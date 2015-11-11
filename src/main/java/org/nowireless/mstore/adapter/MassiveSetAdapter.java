package org.nowireless.mstore.adapter;

import java.lang.reflect.Type;
import java.util.Collection;

import org.nowireless.mstore.collections.MassiveSet;
import org.nowireless.mstore.collections.MassiveSetDef;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class MassiveSetAdapter extends MassiveXAdapter<MassiveSet<?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveSetAdapter i = new MassiveSetAdapter();
	public static MassiveSetAdapter get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveSet<?> create(Object parent, boolean def, JsonElement json, Type typeOfT, JsonDeserializationContext context)
	{
		if (def)
		{
			return new MassiveSetDef((Collection)parent);
		}
		else
		{
			return new MassiveSet((Collection)parent);
		}
	}
	
}
