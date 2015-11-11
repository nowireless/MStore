package org.nowireless.mstore.store;

public enum Modification
{
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	LOCAL_ALTER (true, true),
	LOCAL_ATTACH (true, true),
	LOCAL_DETACH (true, true),
	REMOTE_ALTER (true, false),
	REMOTE_ATTACH (true, false),
	REMOTE_DETACH (true, false),
	NONE (false, false),
	UNKNOWN (false, false),
	;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final boolean modified;
	public boolean isModified() { return this.modified; }
	
	private final boolean local;
	public boolean isLocal() { return this.local; }
	public boolean isRemote() { return this.local == false; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private Modification(boolean modified, boolean local)
	{
		this.modified = modified;
		this.local = local;
	}
	
}
