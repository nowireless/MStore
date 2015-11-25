package org.nowireless.mstore;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nowireless.mstore.adapter.BackstringEnumSetAdapter;
import org.nowireless.mstore.adapter.EntryAdapter;
import org.nowireless.mstore.adapter.JsonElementAdapter;
import org.nowireless.mstore.adapter.MassiveListAdapter;
import org.nowireless.mstore.adapter.MassiveMapAdapter;
import org.nowireless.mstore.adapter.MassiveSetAdapter;
import org.nowireless.mstore.adapter.MassiveTreeMapAdapter;
import org.nowireless.mstore.adapter.MassiveTreeSetAdapter;
import org.nowireless.mstore.adapter.ModdedEnumTypeAdapter;
import org.nowireless.mstore.adapter.UUIDAdapter;
import org.nowireless.mstore.collections.BackstringEnumSet;
import org.nowireless.mstore.collections.MassiveList;
import org.nowireless.mstore.collections.MassiveListDef;
import org.nowireless.mstore.collections.MassiveMap;
import org.nowireless.mstore.collections.MassiveMapDef;
import org.nowireless.mstore.collections.MassiveSet;
import org.nowireless.mstore.collections.MassiveSetDef;
import org.nowireless.mstore.collections.MassiveTreeMap;
import org.nowireless.mstore.collections.MassiveTreeMapDef;
import org.nowireless.mstore.collections.MassiveTreeSet;
import org.nowireless.mstore.collections.MassiveTreeSetDef;
import org.nowireless.mstore.mcore.ConfServer;
import org.nowireless.mstore.mcore.MultiverseColl;
import org.nowireless.mstore.store.Coll;
import org.nowireless.mstore.store.Db;
import org.nowireless.mstore.store.Driver;
import org.nowireless.mstore.store.DriverFlatfile;
import org.nowireless.mstore.store.DriverMongo;
import org.nowireless.mstore.store.ExamineThread;
import org.nowireless.mstore.store.GsonEqualsChecker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MStore implements MStoreUser
{
	
	public final static String INSTANCE = "instance";
	public final static String DEFAULT = "default";
	
	public static Random random = new Random();

	
	// -------------------------------------------- //
	// 
	// -------------------------------------------- //
	private static MStore i;
	public static MStore get() { return i; }
	private MStore() {
		i = this;
		gson = this.getGsonBuilder().create();
	}
	
	public GsonBuilder getGsonBuilder() {
		return new GsonBuilder().setPrettyPrinting()
				.disableHtmlEscaping()
				.excludeFieldsWithModifiers(Modifier.TRANSIENT)
				.registerTypeAdapter(JsonNull.class, JsonElementAdapter.get())
				.registerTypeAdapter(JsonPrimitive.class, JsonElementAdapter.get())
				.registerTypeAdapter(JsonArray.class, JsonElementAdapter.get())
				.registerTypeAdapter(JsonObject.class, JsonElementAdapter.get())
				.registerTypeAdapter(UUID.class, UUIDAdapter.get())

				.registerTypeAdapter(MassiveList.class, MassiveListAdapter.get())
				.registerTypeAdapter(MassiveListDef.class,MassiveListAdapter.get())
				.registerTypeAdapter(MassiveMap.class, MassiveMapAdapter.get())
				.registerTypeAdapter(MassiveMapDef.class, MassiveMapAdapter.get())
				.registerTypeAdapter(MassiveSet.class, MassiveSetAdapter.get())
				.registerTypeAdapter(MassiveSetDef.class, MassiveSetAdapter.get())
				.registerTypeAdapter(MassiveTreeMap.class, MassiveTreeMapAdapter.get())
				.registerTypeAdapter(MassiveTreeMapDef.class, MassiveTreeMapAdapter.get())
				.registerTypeAdapter(MassiveTreeSet.class, MassiveTreeSetAdapter.get())
				.registerTypeAdapter(MassiveTreeSetDef.class, MassiveTreeSetAdapter.get())

				.registerTypeAdapter(BackstringEnumSet.class, BackstringEnumSetAdapter.get())
				.registerTypeAdapter(Entry.class, EntryAdapter.get())
				
				.registerTypeAdapterFactory(ModdedEnumTypeAdapter.ENUM_FACTORY);
	}
	
	public void init() {
		ExamineThread.get().start();
		
		MultiverseColl.get().init();
		//AspectColl.get().init();
		//MassiveCoreMConfColl.get().init();
	}
	
	// -------------------------------------------- //
	// GSON
	// -------------------------------------------- //
	public static Gson gson;
	
	// -------------------------------------------- //
	// MStoreUser
	// -------------------------------------------- //
	public static File DATA_FOLDER = new File("mstore_data");
	
	@Override public Gson getGson() { return gson; }
	@Override
	public File getDataFolder() { return DATA_FOLDER; }
	
	// -------------------------------------------- //
	// Logging
	// -------------------------------------------- //
	public static final Logger LOG = LogManager.getLogger("MSore");
	
	// -------------------------------------------- //
	// DRIVER REGISTRY
	// -------------------------------------------- //
	
	private static Map<String, Driver> drivers = new HashMap<String, Driver>();
	public static boolean registerDriver(Driver driver)
	{
		if (drivers.containsKey(driver.getDriverName())) return false;
		drivers.put(driver.getDriverName(), driver);
		return true;
	}

	public static Driver getDriver(String id)
	{
		return drivers.get(id);
	}
	
	static
	{
		registerDriver(DriverMongo.get());
		registerDriver(DriverFlatfile.get());
		new MStore();
	}
	
	// -------------------------------------------- //
	// ID CREATION
	// -------------------------------------------- //
	
	public static String createId()
	{
		return UUID.randomUUID().toString();
	}
	
	// -------------------------------------------- //
	// JSON ELEMENT EQUAL
	// -------------------------------------------- //
	
	public static boolean equal(JsonElement one, JsonElement two)
	{
		if (one == null) return two == null;
		if (two == null) return one == null;
		
		return GsonEqualsChecker.equals(one, two);
	}
	
	// -------------------------------------------- //
	// FROODLSCHTEIN
	// -------------------------------------------- //
	
	// We cache databases here
	private static Map<String, Db> uri2db = new HashMap<String, Db>();
	
	public static String resolveAlias(String alias)
	{
		String uri = ConfServer.alias2uri.get(alias);
		if (uri == null) return alias;
		return resolveAlias(uri);
	}
	
	public static Db getDb(String alias)
	{
		String uri = resolveAlias(alias);
		Db db = uri2db.get(uri);
		if (db != null) return db;
		
		try
		{
			db = getDb(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return null;
		}
		
		uri2db.put(uri, db);
		
		return db;
	}
	
	public static Db getDb()
	{
		return getDb(ConfServer.dburi);
	}
	
	public static Db getDb(URI uri)
	{
		String scheme = uri.getScheme();
		Driver driver = getDriver(scheme);
		if (driver == null) return null;
		return driver.getDb(uri.toString());
	}
	
	/**
	 * Most likely NOT thread safe, but needs to be called to colls get saved/updated.
	 */
	public static void collTick() {
		for (Coll<?> coll : Coll.getInstances())
		{
			coll.onTick();
		}
	}
}