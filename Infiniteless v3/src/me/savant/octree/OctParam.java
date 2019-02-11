package me.savant.octree;

public class OctParam
{
	private int p;
	private OctParamType v;
	
	public OctParam(int p, OctParamType v)
	{
		this.p = p;
		this.v = v;
	}
	
	public int getPosition()
	{
		return p;
	}
	
	public OctParamType getValueType()
	{
		return v;
	}
}