package org.nowireless.mstore.adapter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import org.nowireless.mstore.collections.BackstringEnumSet;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class BackstringEnumSetAdapter implements JsonDeserializer<BackstringEnumSet<?>>, JsonSerializer<BackstringEnumSet<?>>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static Type stringSetType = new TypeToken<Set<String>>(){}.getType(); 
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static BackstringEnumSetAdapter i = new BackstringEnumSetAdapter();
	public static BackstringEnumSetAdapter get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(BackstringEnumSet<?> src, Type type, JsonSerializationContext context)
	{
		return context.serialize(src.getStringSet(), stringSetType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BackstringEnumSet<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		Set<String> stringSet = context.deserialize(json, stringSetType);
		
		ParameterizedType ptype = (ParameterizedType) type;
		Type[] args = ptype.getActualTypeArguments();
		Class<?> clazz = (Class<?>) args[0];
		
		return new BackstringEnumSet(clazz, stringSet);
	}

}
