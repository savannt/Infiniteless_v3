package me.savant.engine.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import me.savant.engine.io.Log;

public abstract class ShaderProg
{
	private int pID;
	private int vID;
	private int fID;
	//private int gID;
	
	private static FloatBuffer mBuff = BufferUtils.createFloatBuffer(16);
	
	protected abstract void getUniformLocations();
	protected abstract void bindAttributes();
	
	protected int getUniformLocation(String n)
	{
		return GL20.glGetUniformLocation(pID, n);
	}
	
	
	public void start()
	{
		GL20.glUseProgram(pID);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	protected void setMatrix(int l, Matrix4f m)
	{
		mBuff = m.get(mBuff);
		GL20.glUniformMatrix4fv(l, false, mBuff);
	}
	
	protected void setVector2f(int l, Vector2f v)
	{
		GL20.glUniform2f(l, v.x, v.y);
	}
	
	protected void setVector3f(int l, Vector3f v)
	{
		GL20.glUniform3f(l, v.x, v.y, v.z);
	}
	
	protected void setFloat(int l, float f)
	{
		GL20.glUniform1f(l, f);
	}
	
	protected void bindAttribute(int a, String n)
	{
		GL20.glBindAttribLocation(pID, a, n);
	}
	
	protected void bindFragData(int a, String n)
	{
		GL32.glBindFragDataLocation(pID, a, n);
	}
	
	
	public ShaderProg()
	{
		vID = compile(ShaderType.VERTEX);
		fID = compile(ShaderType.FRAGMENT);
		//gID = compile(ShaderType.GEOMETRY);
		pID = GL20.glCreateProgram();
		GL20.glAttachShader(pID, vID);
		GL20.glAttachShader(pID, fID);
		//GL20.glAttachShader(pID, gID);
		bindAttributes();
		GL20.glLinkProgram(pID);
		GL20.glValidateProgram(pID);
		getUniformLocations();
	}
	
	private static int compile(ShaderType s)
	{
		StringBuilder shaderSource = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(s.getFile()));
			String line;
			while((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}
		catch(Exception e)
		{
			Log.fatal("Cannot compile shader [" + s.getFile() + "]");
			e.printStackTrace();
		}
		int shaderID = GL20.glCreateShader(s.getGLSL());
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
			Log.fatal("Failed to compile shader [" + s.getFile() + "] due to [" + GL20.glGetShaderInfoLog(shaderID, 500) + "]");
		return shaderID;
	}
}