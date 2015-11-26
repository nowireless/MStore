package org.nowireless.mstore.adapter;

import java.lang.reflect.Type;
import java.util.Map;

import org.nowireless.common.massive.collections.MassiveMap;
import org.nowireless.common.massive.collections.MassiveMapDef;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class MassiveMapAdapter extends MassiveXAdapter<MassiveMap<?, ?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveMapAdapter i = new MassiveMapAdapter();
	public static MassiveMapAdapter get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveMap<?,?> create(Object parent, boolean def, JsonElement json, Type typeOfT, JsonDeserializationContext context)
	{
		if (def)
		{
			return new MassiveMapDef((Map)parent);
		}
		else
		{
			return new MassiveMap((Map)parent);
		}
	}
	
}
