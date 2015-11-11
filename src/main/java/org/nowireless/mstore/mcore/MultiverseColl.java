package org.nowireless.mstore.mcore;

import org.nowireless.mstore.MStore;
import org.nowireless.mstore.store.Coll;

public class MultiverseColl extends Coll<Multiverse>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MultiverseColl i = new MultiverseColl();
	public static MultiverseColl get() { return i; }
	private MultiverseColl()
	{
		super("massivecore_multiverse", Multiverse.class, MStore.getDb("default"), MStore.get());
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
	// EXTRAS
	// -------------------------------------------- //
	
	@Override
	public void init()
	{
		super.init();
		
		// Ensure the default multiverse exits
		this.get(MassiveCore.DEFAULT, true);
	}
	
}
