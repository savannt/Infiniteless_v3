package me.savant.octree.geometry;

import me.savant.octree.OctParam;

public class OctGeoIndex
{
	private GeoGen gen;
	private OctParam[] param;
	
	public OctGeoIndex(GeoGen gen, OctParam[] param)
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
	
	public GeoGen getGeoGen()
	{
		return gen;
	}
	
	public OctParam[] getParams()
	{
		return param;
	}
}