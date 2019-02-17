package me.savant.engine.renderer;

import org.lwjgl.opengl.GL11;

import me.savant.engine.game.ViewCamera;
import me.savant.engine.main.DisplayWindow;
import me.savant.engine.shader.Shader;

public class Renderer
{
	private Shader s;
	private ViewCamera c;
	private DisplayWindow w;
	
	public Renderer(DisplayWindow w, ViewCamera c)
	{
		this.w = w;
		this.c = c;
		
		s = new Shader();
	}
	
	public void start()
	{
		s.startTime();
	}
	
	public void render(GameObject o)
	{
		//s.setTransformationMatrix(o.getTransformationMatrix());
		//s.setProjectionMatrix(w.getProjectionMatrix());
		//s.setViewMatrix(c.getViewMatrix());
		
		/**
		if(o.isPrefab())
		{
			Prefab p = o.getPrefab();
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
			
			GL30.glBindVertexArray(p.getVAO());
			GL20.glEnableVertexAttribArray(0);
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, p.getIndicesVBO());
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, p.getIndicesCount(), GL11.GL_UNSIGNED_BYTE, 0);
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
		s.stop();
		**/

		s.start();
		s.setResolution(w.getResolution());
		s.setEyePosition(c.getPosition());
		s.setEyeRotation(c.getYaw(), c.getPitch(), c.getRoll());
		s.setUpdatedTime();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		
		
		
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(-1f, -1f, 0f);
		GL11.glVertex3f(1f, -1f, 0f);
		GL11.glVertex3f(1f, 1f, 0f);
		GL11.glVertex3f(-1f, 1f, 0f);
		GL11.glEnd();
		
		
		s.stop();
	}
}