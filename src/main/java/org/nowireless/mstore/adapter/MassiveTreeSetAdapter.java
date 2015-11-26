package org.nowireless.mstore.adapter;

import java.lang.reflect.Type;
import java.util.Collection;

import org.nowireless.common.massive.collections.MassiveTreeSet;
import org.nowireless.common.massive.collections.MassiveTreeSetDef;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class MassiveTreeSetAdapter extends MassiveXAdapter<MassiveTreeSet<?, ?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveTreeSetAdapter i = new MassiveTreeSetAdapter();
	public static MassiveTreeSetAdapter get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveTreeSet<?, ?> create(Object parent, boolean def, JsonElement json, Type typeOfT, JsonDeserializationContext context)
	{
		Object comparator = getComparator(typeOfT);
		if (def)
		{
			return new MassiveTreeSetDef(comparator, (Collection)parent);
		}
		else
		{
			return new MassiveTreeSet(comparator, (Collection)parent);
		}
	}
	
	// -------------------------------------------- //
	// GET COMPARATOR
	// -------------------------------------------- //
	
	public static Object getComparator(Type typeOfT)
	{
		return getNewArgumentInstance(typeOfT, 1);
	}
	
}
