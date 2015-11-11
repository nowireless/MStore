package org.nowireless.mstore.mcore;

import org.nowireless.mstore.store.Entity;
import com.mongodb.WriteConcern;


public class MStoreMConf extends Entity<MStoreMConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MStoreMConf i;
	public static MStoreMConf get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	
	
	
	// Used in the MongoDB mstore driver.
	public boolean catchingMongoDbErrorsOnSave = true;
	
	public boolean catchingMongoDbErrorsOnDelete = true;
	
	public static WriteConcern getMongoDbWriteConcern(boolean catchingErrors)
	{
		return catchingErrors ? WriteConcern.ACKNOWLEDGED : WriteConcern.UNACKNOWLEDGED;
	}
	public WriteConcern getMongoDbWriteConcernSave()
	{
		return getMongoDbWriteConcern(this.catchingMongoDbErrorsOnSave);
	}
	public WriteConcern getMongoDbWriteConcernDelete()
	{
		return getMongoDbWriteConcern(this.catchingMongoDbErrorsOnDelete);
	}	
}
