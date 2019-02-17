package me.savant.engine.prefab;

public abstract class Prefab
{	
	protected int vao;
	protected int verticesVbo;
	protected int indicesVbo;
	
	public abstract int getVAO();
	public abstract int getIndicesCount();
	public abstract int getVerticesVBO();
	public abstract int getIndicesVBO();
	
	public abstract void cleanup();
}