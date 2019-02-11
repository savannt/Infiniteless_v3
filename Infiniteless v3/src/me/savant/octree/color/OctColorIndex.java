package me.savant.octree.color;

import me.savant.octree.OctParam;

public class OctColorIndex
{
	private ColorGen gen;
	private OctParam[] param;
	
	public OctColorIndex(ColorGen gen, OctParam[] param)
	{
		this.gen = gen;
		this.param = param;
	}
	
	public OctParam getParam(int pos)
	{
		for(OctParam p : param)
		{
			if(p.getPosition() == pos)
			{
				return p;
			}
		}
		return null;
	}
	
	public boolean hasParams()
	{
		return param != null;
	}
	
	public ColorGen getColorGen()
	{
		return gen;
	}
	
	public OctParam[] getParams()
	{
		return param;
	}
}