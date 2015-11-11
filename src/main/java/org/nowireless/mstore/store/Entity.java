package org.nowireless.mstore.store;

import org.nowireless.mstore.MStore;
import org.nowireless.mstore.store.accessor.Accessor;

import com.google.gson.Gson;

/**
 * Usage of this class is highly optional. You may persist anything. If you are 
 * creating the class to be persisted yourself, it might be handy to extend this
 * Entity class. It just contains a set of shortcut methods.  
 */

// Self referencing generic.
// http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ206
public abstract class Entity<E extends Entity<E>>
{
	// -------------------------------------------- //
	// COLL & ID
	// -------------------------------------------- //
	
	protected transient Coll<E> coll;
	protected void setColl(Coll<E> val) { this.coll = val; }
	public Coll<E> getColl() { return this.coll; }
	
	protected transient String id;
	protected void setId(String id) { this.id = id; }
	public String getId() { return this.id; }
	
	public String getUniverse()
	{
		Coll<E> coll = this.getColl();
		if (coll == null) return null;
		
		return coll.getUniverse();
	}
	
	// -------------------------------------------- //
	// ATTACH AND DETACH
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public String attach(Coll<E> coll)
	{
		return coll.attach((E) this);
	}
	
	@SuppressWarnings("unchecked")
	public E detach()
	{
		Coll<E> coll = this.getColl();
		if (coll == null) return (E)this;
		
		return coll.detachEntity(this);
	}
	
	public boolean attached()
	{
		return this.getColl() != null && this.getId() != null;
	}
	
	public boolean detached()
	{
		return ! this.attached();
	}
	
	
	public void preAttach(String id)
	{
		
	}
	
	public void postAttach(String id)
	{
		
	}
	
	public void preDetach(String id)
	{
		
	}
	
	public void postDetach(String id)
	{
		
	}
	
	// -------------------------------------------- //
	// SYNC AND IO ACTIONS
	// -------------------------------------------- //
	
	public boolean isLive()
	{
		String id = this.getId();
		if (id == null) return false;
		
		Coll<E> coll = this.getColl();
		if (coll == null) return false;
		
		if ( ! coll.inited()) return false;
		
		return true;
	}
	
	public void changed()
	{
		if ( ! this.isLive()) return;
		
		// UNKNOWN is very unimportant really.
		// LOCAL_ATTACH is for example much more important and should not be replaced.
		if ( ! coll.identifiedModifications.containsKey(id))
		{
			coll.identifiedModifications.put(id, Modification.UNKNOWN);
		}
	}
	
	public Modification sync()
	{
		String id = this.getId();
		if (id == null) return Modification.UNKNOWN;
		return this.getColl().syncId(id);
	}
	
	public void saveToRemote()
	{
		String id = this.getId();
		if (id == null) return;
		
		this.getColl().saveToRemote(id);
	}
	
	public void loadFromRemote()
	{
		String id = this.getId();
		if (id == null) return;
		
		this.getColl().loadFromRemote(id, null);
	}
	
	// -------------------------------------------- //
	// DERPINGTON
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public E load(E that)
	{
		Accessor.get(this.getClass()).copy(that, this);
		return (E) this;
	}
	
	public boolean isDefault()
	{
		return false;
	}
	
	// -------------------------------------------- //
	// STANDARDS
	// -------------------------------------------- //

	@Override
	public String toString()
	{
		Gson gson = MStore.gson;
		Coll<E> coll = this.getColl();
		if (coll != null) gson = coll.getGson();
		
		return this.getClass().getSimpleName()+gson.toJson(this, this.getClass());
	}
	
}
