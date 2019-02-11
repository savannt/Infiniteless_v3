package me.savant.main;

import java.io.File;

import me.savant.io.Log;
import me.savant.octree.OctreeCompiler;

public class Infiniteless_v3
{
	public static void main(String[] args)
	{
		Log.log("Starting...");
		
		try
		{
			//new MasterUniverse();
			OctreeCompiler c = new OctreeCompiler();
			c.compile(new File("test.oct"));
		}
		catch (Exception e)
		{
			Log.error("Failed somewhere... " + e.getMessage());
			e.printStackTrace();
		}
	}
}