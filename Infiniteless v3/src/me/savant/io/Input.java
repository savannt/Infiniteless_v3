package me.savant.io;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input
{
	private static long w;
	private static boolean[] l = new boolean[GLFW.GLFW_KEY_LAST];
	private static boolean[] y = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public static void update(long t)
	{
		w = t;
		for(int i = 0; i < GLFW.GLFW_KEY_LAST; i++) l[i] = isKeyDown(i);
		for(int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) y[i] = isMouseDown(i);
	}
	
	public static boolean isKeyDown(int k)
	{
		return GLFW.glfwGetKey(w, k) == 1;
	}
	
	public static boolean isMouseDown(int m)
	{
		return GLFW.glfwGetMouseButton(w, m) == 1;
	}
	
	public static boolean isKeyPressed(int k)
	{
		return isKeyDown(k) && !l[k];
	}
	
	public static boolean isKeyReleased(int k)
	{
		return !isKeyDown(k) && l[k];
	}
	
	public static boolean isMousePressed(int m)
	{
		return isMouseDown(m) && !y[m];
	}
	
	public static boolean isMouseReleased(int m)
	{
		return !isMouseDown(m) && y[m];
	}
	
	public double getMouseX()
	{
		DoubleBuffer b = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(w, b, null);
		return b.get(0);
	}
	
	public double getMouseY()
	{
		DoubleBuffer b = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(w, null, b);
		return b.get(0);
	}
}