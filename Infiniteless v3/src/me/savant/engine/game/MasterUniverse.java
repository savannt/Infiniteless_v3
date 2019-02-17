package me.savant.engine.game;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import me.savant.engine.io.Input;
import me.savant.engine.io.Log;
import me.savant.engine.main.DisplayWindow;
import me.savant.engine.prefab.Cube;
import me.savant.engine.renderer.GameObject;
import me.savant.engine.renderer.Renderer;

public class MasterUniverse
{
	private DisplayWindow w;
	private Performance p;
	private Renderer r;
	private ViewCamera c;
	
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
		
		c = new ViewCamera();
		
		p = new Performance();
		r = new Renderer(w, c);
	}
	
	
	private boolean showFPS = false;
	private void loop()
	{
		GameObject o = new GameObject(new Cube());
		o.setPosition(new Vector3f(0, 0, 5));
		
		r.start();
		
		while(!w.isClosed())
		{
			
			firstCall();
						
			if(Input.isKeyReleased(GLFW.GLFW_KEY_F3))
				showFPS = !showFPS;
			if(showFPS)
				System.out.println("[FPS] " + p.getFPS() + " fps");
			
			r.render(o);
			
			lastCall();
		}
		
		o.cleanup();
	}
	
	private void firstCall()
	{
		c.update();
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