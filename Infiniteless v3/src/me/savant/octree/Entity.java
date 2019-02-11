package me.savant.octree;

import me.savant.octree.color.ColorGen;
import me.savant.octree.geometry.GeoGen;

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