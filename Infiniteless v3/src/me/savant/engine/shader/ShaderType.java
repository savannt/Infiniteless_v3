package me.savant.engine.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public enum ShaderType
{
	VERTEX, FRAGMENT, GEOMETRY;
	
	public int getGLSL()
	{
		switch(this)
		{
		case VERTEX: return GL20.GL_VERTEX_SHADER;
		case FRAGMENT: return GL20.GL_FRAGMENT_SHADER;
		case GEOMETRY: return GL32.GL_GEOMETRY_SHADER;
		}
		return -1;
	}
	
	public String getFile()
	{
		switch(this)
		{
		case VERTEX: return "shader/oct.vert";
		case FRAGMENT: return "shader/oct.frag";
		case GEOMETRY: return "shader/oct.geo";
		}
		return "";
	}
}
