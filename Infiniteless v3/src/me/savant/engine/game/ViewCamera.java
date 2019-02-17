package me.savant.engine.game;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import me.savant.engine.io.Input;

public class ViewCamera
{
	private float moveBoost = 10f;
	private float pitch = 0f;
	private float yaw = 0f;
	private float roll = 0f;
	private float moveSpeed = 5f;
	private Vector3f pos = new Vector3f(0, 0, 0);
	
	public Matrix4f getViewMatrix()
	{
		Matrix4f m = new Matrix4f();
		m.identity();
		m.rotate((float)Math.toRadians(pitch), new Vector3f(1, 0, 0));
		m.rotate((float)Math.toRadians(yaw), new Vector3f(0, 1, 0));
		m.rotate((float)Math.toRadians(roll), new Vector3f(0, 0, 1));
		
		Vector3f p = pos;
		Vector3f n = new Vector3f(-p.x, -p.y, -p.z);
		m.translate(n);
		return m;
	}
	
	public void update()
	{
		float s = moveSpeed * Performance.DELTA_TIME;
		if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			s *= moveBoost;
		
		float y = 0;
		if(Input.isKeyDown(GLFW.GLFW_KEY_W))
			y = yaw + 180;
		if(Input.isKeyDown(GLFW.GLFW_KEY_S))
			y = yaw;
		if(Input.isKeyDown(GLFW.GLFW_KEY_A))
			y = yaw + 90;
		if(Input.isKeyDown(GLFW.GLFW_KEY_D))
			y = yaw - 90;
		if(Input.isKeyDown(GLFW.GLFW_KEY_E))
			pos = pos.add(0, s, 0);
		if(Input.isKeyDown(GLFW.GLFW_KEY_Q))
			pos = pos.add(0, -s, 0);
		
		
		if(y != 0)
		{
			float dx = (float)(s * Math.sin(Math.toRadians(360 - y)));
			float dz = (float)(s * Math.cos(Math.toRadians(360 - y)));
			pos = pos.add(dx, 0, dz);
		}
				
		yaw += Input.changedMouseX() / 5f;
		pitch += Input.changedMouseY() / 5f;
	}
	
	public Vector3f getPosition()
	{
		return pos;
	}
	
	public float getPitch()
	{
		return pitch;
	}
	
	public float getYaw()
	{
		return yaw;
	}
	
	public float getRoll()
	{
		return roll;
	}
}