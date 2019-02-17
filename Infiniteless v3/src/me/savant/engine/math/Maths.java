package me.savant.engine.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths
{
	public static Matrix4f getTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale)
	{
		Matrix4f m = new Matrix4f();
		m.identity();
		m.translate(translation);
		m.rotate((float)Math.toRadians(rx), new Vector3f(1, 0, 0));
		m.rotate((float)Math.toRadians(ry), new Vector3f(0, 1, 0));
		m.rotate((float)Math.toRadians(rz), new Vector3f(0, 0, 1));
		m.scale(scale);
		return m;
	}
}
