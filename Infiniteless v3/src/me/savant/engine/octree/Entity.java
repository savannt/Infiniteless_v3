package me.savant.engine.octree;

import me.savant.engine.octree.color.ColorGen;
import me.savant.engine.octree.geometry.GeoGen;

public class Entity
{
	private GeoGen geo;
	private ColorGen color;
	
	public Entity(GeoGen geo, ColorGen color)
	{
		this.geo = geo;
		this.color = color;
	}
	
	public GeoGen getGeoGen()
	{
		return geo;
	}
	
	public ColorGen getColorGen()
	{
		return color;
	}
}