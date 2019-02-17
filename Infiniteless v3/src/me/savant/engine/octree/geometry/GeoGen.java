package me.savant.engine.octree.geometry;

public abstract class GeoGen
{
	public String funcName;
	public GeoGen(String funcName)
	{
		this.funcName = funcName;
	}
	public abstract boolean filled(int layer, int position);
}