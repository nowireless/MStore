package org.nowireless.mstore.store;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.nowireless.mstore.util.DiscUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class DriverFlatfile extends DriverAbstract
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	private static final String DOTJSON = ".json";
	public static final String NAME = "flatfile";
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DriverFlatfile i = new DriverFlatfile();
	public static DriverFlatfile get() { return i; }
	private DriverFlatfile() { super(NAME); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
		
	@Override
	public Db getDb(String uri)
	{
		// "flatfile://" is 8+3=11 chars
		File directory = new File(uri.substring(NAME.length() + 3));
		directory.mkdirs();
		return new DbFlatfile(this, directory);
	}
	
	@Override
	public boolean dropDb(Db db)
	{
		if ( ! (db instanceof DbFlatfile)) throw new IllegalArgumentException("db");
		DbFlatfile dbFlatfile = (DbFlatfile)db;
		
		try
		{
			return DiscUtil.deleteRecursive(dbFlatfile.directory);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@Override
	public Set<String> getCollnames(Db db)
	{
		Set<String> ret = new LinkedHashSet<String>();
		
		for (File file : ((DbFlatfile)db).directory.listFiles())
		{
			if ( ! file.isDirectory()) continue;
			ret.add(file.getName());
		}
	
		return ret;
	}
	
	@Override
	public boolean renameColl(Db db, String from, String to)
	{
		File dir = ((DbFlatfile)db).directory;
		File fileFrom = new File(dir, from);
		File fileTo = new File(dir, to);
		return fileFrom.renameTo(fileTo);
	}

	@Override
	public boolean containsId(Coll<?> coll, String id)
	{
		return fileFromId(coll, id).isFile();
	}
	
	@Override
	public long getMtime(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		if ( ! file.isFile()) return 0;
		return file.lastModified();
	}
	
	@Override
	public Collection<String> getIds(Coll<?> coll)
	{
		List<String> ret = new ArrayList<String>();
		
		// Scan the collection folder for .json files
		File collDir = getDirectory(coll);
		if ( ! collDir.isDirectory()) return ret;
		for (File file : collDir.listFiles(JsonFileFilter.get()))
		{
			ret.add(idFromFile(file));
		}
		
		return ret;
	}
	
	@Override
	public Map<String, Long> getId2mtime(Coll<?> coll)
	{
		// Create Ret
		Map<String, Long> ret = new HashMap<String, Long>();
		
		// Get Directory
		File directory = getDirectory(coll);
		if ( ! directory.isDirectory()) return ret;
		
		// For each .json file
		for (File file : directory.listFiles(JsonFileFilter.get()))
		{
			String id = idFromFile(file);
			long mtime = file.lastModified();
			// TODO: Check is 0 here?
			ret.put(id, mtime);
		}
		
		// Return Ret
		return ret;
	}
	
	@Override
	public Entry<JsonElement, Long> load(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		return loadFile(file);
	}
	
	public Entry<JsonElement, Long> loadFile(File file)
	{
		long mtime = file.lastModified();
		JsonElement raw = loadFileJson(file);
		
		return new SimpleEntry<JsonElement, Long>(raw, mtime);
	}
	
	public JsonElement loadFileJson(File file)
	{
		String content = DiscUtil.readCatch(file);
		if (content == null) return null;
		
		content = content.trim();
		if (content.length() == 0) return null;
		
		return new JsonParser().parse(content);
	}
	
	@Override
	public Map<String, Entry<JsonElement, Long>> loadAll(Coll<?> coll)
	{
		// Create Ret
		Map<String, Entry<JsonElement, Long>> ret = null;
		
		// Get Directory
		File directory = getDirectory(coll);
		if ( ! directory.isDirectory()) return ret;
		
		// Find All
		File[] files = directory.listFiles(JsonFileFilter.get());
		
		// Create Ret
		ret = new LinkedHashMap<String, Entry<JsonElement, Long>>(files.length);
		
		// For Each Found
		for (File file : files)
		{
			// Get ID
			String id = idFromFile(file);
			
			// Get Entry
			Entry<JsonElement, Long> entry = loadFile(file);
			// NOTE: The entry can be a failed one with null and 0.
			// NOTE: We add it anyways since it's an informative failure.
			// NOTE: This is supported by our defined specification.
			
			// Add
			ret.put(id, entry);
		}
		
		// Return Ret
		return ret;
	}

	@Override
	public long save(Coll<?> coll, String id, JsonElement data)
	{
		File file = fileFromId(coll, id);
		String content = coll.getGson().toJson(data);
		if (DiscUtil.writeCatch(file, content) == false) return 0;
		return file.lastModified();
	}

	@Override
	public void delete(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		file.delete();
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static File getDirectory(Coll<?> coll)
	{
		return (File) coll.getCollDriverObject();
	}
	
	public static String idFromFile(File file)
	{
		if (file == null) return null;
		String name = file.getName();
		return name.substring(0, name.length() - 5);
	}
	
	public static File fileFromId(Coll<?> coll, String id)
	{
		File collDir = getDirectory(coll);
		File idFile = new File(collDir, id + DOTJSON);
		return idFile;
	}
	
}
