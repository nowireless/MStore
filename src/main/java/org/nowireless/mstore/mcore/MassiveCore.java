package org.nowireless.mstore.mcore;

import java.util.Random;
import java.util.Set;

import org.nowireless.mstore.util.MUtil;

public class MassiveCore 
{
	// -------------------------------------------- //
	// COMMON CONSTANTS
	// -------------------------------------------- //
	
	public final static String INSTANCE = "instance";
	public final static String DEFAULT = "default";
	
	public final static Set<String> NOTHING = MUtil.treeset("", "none", "null", "nothing");
	public final static Set<String> REMOVE = MUtil.treeset("clear", "c", "delete", "del", "d", "erase", "e", "remove", "rem", "r", "reset", "res");
	public final static Set<String> NOTHING_REMOVE = MUtil.treeset("", "none", "null", "nothing", "clear", "c", "delete", "del", "d", "erase", "e", "remove", "rem", "r", "reset", "res");
	
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveCore i;
	public static MassiveCore get() { return i; }
	public MassiveCore() { i = this; }
	
	// -------------------------------------------- //
	// STATIC
	// -------------------------------------------- //
	
	public static Random random = new Random();
/*	public static Gson gson = getMassiveCoreGsonBuilder().create();
	
	public static GsonBuilder getMassiveCoreGsonBuilder()
	{
		return new GsonBuilder()
		.setPrettyPrinting()
		.disableHtmlEscaping()
		.excludeFieldsWithModifiers(Modifier.TRANSIENT)
		.registerTypeAdapter(JsonNull.class, JsonElementAdapter.get())
		.registerTypeAdapter(JsonPrimitive.class, JsonElementAdapter.get())
		.registerTypeAdapter(JsonArray.class, JsonElementAdapter.get())
		.registerTypeAdapter(JsonObject.class, JsonElementAdapter.get())
		.registerTypeAdapter(UUID.class, UUIDAdapter.get())
		.registerTypeAdapter(ItemStack.class, ItemStackAdapter.get())
		.registerTypeAdapter(Inventory.class, InventoryAdapter.get())
		.registerTypeAdapter(PlayerInventory.class, PlayerInventoryAdapter.get())
		.registerTypeAdapter(PS.class, PSAdapter.get())
		
		.registerTypeAdapter(MassiveList.class, MassiveListAdapter.get())
		.registerTypeAdapter(MassiveListDef.class, MassiveListAdapter.get())
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
	} */
	
	//public static String getServerId() { return ConfServer.serverid; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// Commands
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	
	/*
	@Override
	public void onEnable()
	{
		// This is safe since all plugins using Persist should bukkit-depend this plugin.
		// Note this one must be before preEnable. dooh.
		// TODO: Create something like "deinit all" (perhaps a forloop) to readd this.
		// TODO: Test and ensure reload compat.
		// Coll.instances.clear();
		
		// Start the examine thread
		ExamineThread.get().start();
		
		if ( ! preEnable()) return;
		
		// Load Server Config
		ConfServer.get().load();
		
		// Setup IdUtil
		IdUtil.setup();
		
		// Engine
		EngineCollTick.get().activate();
		MassiveCoreEngineMain.get().activate();
		MassiveCoreEngineVariable.get().activate();
		EngineScheduledTeleport.get().activate();
		EngineTeleportMixinCause.get().activate();
		MassiveCoreEngineWorldNameSet.get().activate();
		MassiveCoreEngineCommandRegistration.get().activate();
		MassiveCoreEngineDestination.get().activate();
		PlayerUtil.get().activate();
		EngineChestGui.get().activate();
		EngineGank.get().activate();
		
		// Collections
		MultiverseColl.get().init();
		AspectColl.get().init();
		MassiveCoreMConfColl.get().init();
		
		// Register commands
		this.outerCmdMassiveCore = new CmdMassiveCore() { public List<String> getAliases() { return MassiveCoreMConf.get().aliasesOuterMassiveCore; } };
		this.outerCmdMassiveCore.register(this);
		
		this.outerCmdMassiveCoreUsys = new CmdMassiveCoreUsys() { public List<String> getAliases() { return MassiveCoreMConf.get().aliasesOuterMassiveCoreUsys; } };
		this.outerCmdMassiveCoreUsys.register(this);
		
		this.outerCmdMassiveCoreStore = new CmdMassiveCoreStore() { public List<String> getAliases() { return MassiveCoreMConf.get().aliasesOuterMassiveCoreStore; } };
		this.outerCmdMassiveCoreStore.register(this);
		
		this.outerCmdMassiveCoreBuffer = new CmdMassiveCoreBuffer() { public List<String> getAliases() { return MassiveCoreMConf.get().aliasesOuterMassiveCoreBuffer; } };
		this.outerCmdMassiveCoreBuffer.register(this);
		
		this.outerCmdMassiveCoreCmdurl = new CmdMassiveCoreCmdurl() { public List<String> getAliases() { return MassiveCoreMConf.get().aliasesOuterMassiveCoreCmdurl; } };
		this.outerCmdMassiveCoreCmdurl.register(this);
		
		// Integration
		this.integrate(
			IntegrationVault.get()
		);
		
		// Delete Files (at once and additionally after all plugins loaded)
		MassiveCoreTaskDeleteFiles.get().run();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, MassiveCoreTaskDeleteFiles.get());
		
		this.postEnable();
	} */
	
}
