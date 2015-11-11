package org.nowireless.mstore;

import java.io.File;

import com.google.gson.Gson;

public interface MStoreUser {
	
	public Gson getGson();
	public File getDataFolder();
}
