package org.nowireless.mstore.mcore;

import org.nowireless.mstore.MStore;
import org.nowireless.mstore.mcore.MassiveCore;
import org.nowireless.mstore.store.Coll;

public class MassiveCoreMConfColl extends Coll<MassiveCoreMConf>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveCoreMConfColl i = new MassiveCoreMConfColl();
	public static MassiveCoreMConfColl get() { return i; }
	private MassiveCoreMConfColl()
	{
		super("massivecore_mconf", MassiveCoreMConf.class, MStore.getDb(ConfServer.dburi), MStore.get());
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
		MassiveCoreMConf.i = this.get(MassiveCore.INSTANCE, true);
	}
	
}
