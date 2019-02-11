package me.savant.octree;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import me.savant.io.Log;

public class EntityDictionary
{
	private HashMap<String, Entity> d = new HashMap<String, Entity>();
	
	public void addEntity(Entity e, File f)
	{
		d.put(f.getName(), e);
	}
	
	public Entity getEntity(String n)
	{
		for(Entry<String, Entity> s : d.entrySet())
		{
			if(s.getKey().equalsIgnoreCase(n))
			{
				return s.getValue();
			}
		}
		Log.error("Cannot find Entity [" + n + "]");
		return null;
	}
}