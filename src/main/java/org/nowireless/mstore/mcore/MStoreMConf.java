package org.nowireless.mstore.mcore;

import org.nowireless.mstore.store.Entity;


public class MStoreMConf extends Entity<MStoreMConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MStoreMConf i;
	public static MStoreMConf get() { return i; }
	
	
}
