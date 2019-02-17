package me.savant.engine.octree.geometry;

public class SolidGeo extends GeoGen
{
	boolean fill;
	
	public SolidGeo()
	{
		super("solid");
	}
	
	public void setFill(boolean fill)
	{
		this.fill = fill;
	}
	
	@Override
	public boolean filled(int layer, int position)
	{
		return fill;
	}
}