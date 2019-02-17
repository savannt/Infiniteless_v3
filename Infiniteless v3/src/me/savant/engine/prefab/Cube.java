package me.savant.engine.prefab;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Cube extends Prefab
{
	private int indiceLength;
	
	public Cube()
	{
		float[] vertices =
			{			
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,-0.5f,0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				-0.5f,-0.5f,0.5f,
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		byte[] indices =
			{
				0,1,3,
				3,1,2,
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		this.indiceLength = indices.length;
		
		FloatBuffer vB = BufferUtils.createFloatBuffer(vertices.length);
		vB.put(vertices);
		vB.flip();
		
		ByteBuffer iB = BufferUtils.createByteBuffer(this.indiceLength);
		iB.put(indices);
		iB.flip();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		verticesVbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vB, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		indicesVbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iB, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(verticesVbo);
		GL15.glDeleteBuffers(indicesVbo);
		
		GL20.glDisableVertexAttribArray(0);
	}
	
	public int getVAO()
	{
		return vao;
	}
	
	public int getIndicesCount()
	{
		return indiceLength;
	}
	
	public int getVerticesVBO()
	{
		return verticesVbo;
	}
	
	public int getIndicesVBO()
	{
		return indicesVbo;
	}
	
	/**
	private float[] textureCoords =
		{
			
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0
	};
	**/
}