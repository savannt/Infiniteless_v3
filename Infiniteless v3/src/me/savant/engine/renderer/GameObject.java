package me.savant.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.savant.engine.octree.Entity;
import me.savant.engine.prefab.Prefab;

public class GameObject
{
	private Vector3f pos = new Vector3f(0, 0, 0);
	private float scale = 1f;
	private Prefab p;
	private Entity e;
	
	public GameObject(Entity e)
	{
		this.e = e;
	}
	
	public GameObject(Prefab p)
	{
		this.p = p;
	}
	
	public boolean isPrefab()
	{
		return this.p != null;
	}
	
	public Prefab getPrefab()
	{
		return this.p;
	}
	
	public void setPosition(Vector3f v)
	{
		this.pos = v;
	}
	
	public void setScale(float f)
	{
		this.scale = f;
	}
	
	public Vector3f getPosition()
	{
		return this.pos;
	}
	
	public float getScale()
	{
		return this.scale;
	}
	
	public Entity getEntity()
	{
		return this.e;
	}
	
	public Matrix4f getTransformationMatrix()
	{
		Matrix4f m = new Matrix4f();
		m.identity();
		m.translate(pos);
		//m.translate(pos);
		//m.scale(scale);
		return m;
	}
	
	public void cleanup()
	{
		if(isPrefab())
		{
			p.cleanup();
		}
	}
}