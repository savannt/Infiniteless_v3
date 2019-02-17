package me.savant.engine.main;

import me.savant.engine.game.MasterUniverse;
import me.savant.engine.io.Log;

public class Infiniteless_v3
{
	public static void main(String[] args)
	{
		Log.log("Starting...");
		
		try
		{
			new MasterUniverse();
		}
		catch (Exception e)
		{
			Log.error("Failed somewhere... " + e.getMessage());
			e.printStackTrace();
		}
	}
}