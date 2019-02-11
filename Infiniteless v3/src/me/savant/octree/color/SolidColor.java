package me.savant.octree.color;

public class SolidColor extends ColorGen
{
	public SolidColor()
	{
		super("solid");
	}

	@Override
	public float color(int layer, int position) 
	{
		return 0;
	}
}