package me.savant.engine.octree.color;

public abstract class ColorGen
{
	public ColorGen(String funcName)
	{
		this.funcName = funcName;
	}
	
	public String funcName;
	public abstract float color(int layer, int position);
}