package org.nowireless.mstore.store;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonElement;


public abstract class DbAbstract implements Db
{	
	// -------------------------------------------- //
	// DRIVER
	// -------------------------------------------- //
	
	public String getDriverName()
	{
		return this.getDriver().getDriverName();
	}
	
	public Db getDb(String uri)
	{
		return this.getDriver().getDb(uri);
	}
	
	public boolean dropDb()
	{
		return this.getDriver().dropDb(this);
	}
	
	public Set<String> getCollnames()
	{
		return this.getDriver().getCollnames(this);
	}
	
	public boolean renameColl(String from, String to)
	{
		return this.getDriver().renameColl(this, from, to);
	}
	
	public boolean containsId(Coll<?> coll, String id)
	{
		return this.getDriver().containsId(coll, id);
	}
	
	public long getMtime(Coll<?> coll, String id)
	{
		return this.getDriver().getMtime(coll, id);
	}
	
	public Collection<String> getIds(Coll<?> coll)
	{
		return this.getDriver().getIds(coll);
	}
	
	public Map<String, Long> getId2mtime(Coll<?> coll)
	{
		return this.getDriver().getId2mtime(coll);
	}
	
	public Entry<JsonElement, Long> load(Coll<?> coll, String id)
	{
		return this.getDriver().load(coll, id);
	}
	
	public Map<String, Entry<JsonElement, Long>> loadAll(Coll<?> coll)
	{
		return this.getDriver().loadAll(coll);
	}
	
	public long save(Coll<?> coll, String id, JsonElement data)
	{
		return this.getDriver().save(coll, id, data);
	}
	
	public void delete(Coll<?> coll, String id)
	{
		this.getDriver().delete(coll, id);
	}
}
