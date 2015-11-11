package org.nowireless.mstore.adapter;

import java.lang.reflect.Type;
import java.util.Map;

import org.nowireless.mstore.collections.MassiveTreeMap;
import org.nowireless.mstore.collections.MassiveTreeMapDef;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class MassiveTreeMapAdapter extends MassiveXAdapter<MassiveTreeMap<?, ?, ?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveTreeMapAdapter i = new MassiveTreeMapAdapter();
	public static MassiveTreeMapAdapter get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveTreeMap<?, ?, ?> create(Object parent, boolean def, JsonElement json, Type typeOfT, JsonDeserializationContext context)
	{
		Object comparator = getComparator(typeOfT);
		if (def)
		{
			return new MassiveTreeMapDef(comparator, (Map)parent);
		}
		else
		{
			return new MassiveTreeMap(comparator, (Map)parent);
		}
	}
	
	// -------------------------------------------- //
	// GET COMPARATOR
	// -------------------------------------------- //
	
	public static Object getComparator(Type typeOfT)
	{
		return getNewArgumentInstance(typeOfT, 2);
	}
	
}
