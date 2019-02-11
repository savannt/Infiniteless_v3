package me.savant.game;

import org.lwjgl.glfw.GLFW;

import me.savant.io.Input;
import me.savant.io.Log;
import me.savant.main.DisplayWindow;

public class MasterUniverse
{
	private DisplayWindow w;
	private Performance p;
	
	public MasterUniverse()
	{
		init();
		loop();
		destroy();
	}
	
	private void init()
	{
		w = new DisplayWindow(1080, 720, "Engine");
		w.create();
		
		p = new Performance();
	}
	
	
	private boolean showFPS = false;
	private void loop()
	{
		while(!w.isClosed())
		{
			firstCall();
			
			if(Input.isKeyReleased(GLFW.GLFW_KEY_F3))
				showFPS = !showFPS;
			if(showFPS)
				System.out.println("[FPS] " + p.getFPS() + " fps");
			
			lastCall();
		}
	}
	
	private void firstCall()
	{
		w.update(); //Updates Window
		p.update(); //Updates FPS
	}
	
	private void lastCall()
	{
		w.updateInputs();
		w.swapBuffers(); //Swap's Buffers
	}
	
	private void destroy()
	{
		w.destroy();
		
		Log.log("Destroyed successfully");
		
		Log.save();
		System.exit(-1);
	}
	
}