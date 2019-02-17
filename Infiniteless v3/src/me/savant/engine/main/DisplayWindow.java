package me.savant.engine.main;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import me.savant.engine.io.Input;
import me.savant.engine.io.Log;

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
		//GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_FALSE);
		
		w = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		if(w == 0)
			Log.fatal("Couldn't create window");
		
		GLFWVidMode v = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(w, (v.width() - width) / 2, (v.height() - height) / 2);
		GLFW.glfwShowWindow(w);
		GLFW.glfwMakeContextCurrent(w);
		GLFW.glfwSwapInterval(0);
		GL.createCapabilities(true);
		
		Input.update(w); //Avoids null point exception due to last call in loop;
		GL.createCapabilities();
		GL11.glViewport(0, 0, width, height);
		
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}
	
	public Vector2f getResolution()
	{
		return new Vector2f(width, height);
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
	
	public Matrix4f getProjectionMatrix()
	{
		return new Matrix4f().setPerspective((float)Math.toRadians(90f), (((float)width )/((float) height)), 0.01f, 100f);
	}
}