package org.nowireless.mstore.mcore;

import java.io.File;

import org.nowireless.mstore.MStoreUser;
import org.nowireless.mstore.store.accessor.Accessor;
import org.nowireless.mstore.util.DiscUtil;

import com.google.gson.Gson;


public class SimpleConfig
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected transient MStoreUser plugin;
	protected MStoreUser getPlugin() { return this.plugin; }
	
	protected transient File file;
	protected File getFile() { return this.file; }
	
	public SimpleConfig(MStoreUser plugin, File file)
	{
		this.plugin = plugin;
		this.file = file;
	}
	
	public SimpleConfig(MStoreUser plugin, String confname)
	{
		this(plugin, new File(plugin.getDataFolder(), confname+".json"));
	}
	
	public SimpleConfig(MStoreUser plugin)
	{
		this(plugin, "conf");
	}
	
	// -------------------------------------------- //
	// IO
	// -------------------------------------------- //
	
	private Gson getGson()
	{
		return this.plugin.getGson();
	}
	
	protected static boolean contentRequestsDefaults(String content)
	{
		if (content == null) return false;
		if (content.length() == 0) return false;
		char c = content.charAt(0);
		return c == 'd' || c == 'D';
	}
	
	public void load()
	{
		if (this.getFile().isFile())
		{
			String content = DiscUtil.readCatch(this.getFile());
			content = content.trim();
			Object toShallowLoad = null;
			if (contentRequestsDefaults(content))
			{
				try
				{
					toShallowLoad = this.getClass().newInstance();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return;
				}
			}
			else
			{
				toShallowLoad = this.getGson().fromJson(content, this.getClass());
			}
			Accessor.get(this.getClass()).copy(toShallowLoad, this);
		}
		save();
	}
	
	public void save()
	{
		String content = DiscUtil.readCatch(this.getFile());
		if (contentRequestsDefaults(content)) return;
		content = this.getGson().toJson(this);
		DiscUtil.writeCatch(file, content);
	}
}
