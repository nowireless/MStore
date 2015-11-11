package org.nowireless.mstore.mcore;

import org.nowireless.mstore.MStore;
import org.nowireless.mstore.store.Coll;

public class MStoreMConfColl extends Coll<MStoreMConf>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MStoreMConfColl i = new MStoreMConfColl();
	public static MStoreMConfColl get() { return i; }
	private MStoreMConfColl()
	{
		super("massivecore_mconf", MStoreMConf.class, MStore.getDb(ConfServer.dburi), MStore.get());
	}

	// -------------------------------------------- //
	// STACK TRACEABILITY
	// -------------------------------------------- //
	
	@Override
	public void onTick()
	{
		super.onTick();
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void init()
	{
		super.init();
		MStoreMConf.i = this.get(MStore.INSTANCE, true);
	}
	
}
