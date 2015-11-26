package org.nowireless.mstore.adapter;

import java.lang.reflect.Type;
import java.util.Collection;

import org.nowireless.common.massive.collections.MassiveList;
import org.nowireless.common.massive.collections.MassiveListDef;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

public class MassiveListAdapter extends MassiveXAdapter<MassiveList<?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveListAdapter i = new MassiveListAdapter();
	public static MassiveListAdapter get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveList<?> create(Object parent, boolean def, JsonElement json, Type typeOfT, JsonDeserializationContext context)
	{
		if (def)
		{
			return new MassiveListDef((Collection)parent);
		}
		else
		{
			return new MassiveList((Collection)parent);
		}
	}
	
}
