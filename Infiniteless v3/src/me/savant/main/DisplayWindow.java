package me.savant.main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import me.savant.io.Input;
import me.savant.io.Log;

public class DisplayWindow
{
	private int width;
	private int height;
	private String title;
	private long w;
	
	public DisplayWindow(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void create()
	{
		if(!GLFW.glfwInit())
			Log.fatal("Couldn't initialize");
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		w = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		if(w == 0)
			Log.fatal("Couldn't create window");
		
		GLFWVidMode v = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(w, (v.width() - width) / 2, (v.height() - height) / 2);
		GLFW.glfwShowWindow(w);
		
		Input.update(w); //Avoids null point exception due to last call in loop;
	}
	
	public boolean isClosed()
	{
		return GLFW.glfwWindowShouldClose(w);
	}
	
	public void update()
	{
		GLFW.glfwPollEvents(); //TODO: Updates Inputs
	}
	
	public void updateInputs()
	{		
		Input.update(w);
	}
	
	public void swapBuffers()
	{
		GLFW.glfwSwapBuffers(w);
	}
	
	public void destroy()
	{
		GLFW.glfwHideWindow(w);
		GLFW.glfwDestroyWindow(w);
		GLFW.glfwTerminate();
	}
}