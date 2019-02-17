package me.savant.engine.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Shader extends ShaderProg
{
	private int timeID;
	
	private int transformationMatrixID;
	private int projectionMatrixID;
	private int viewMatrixID;
	private int eyePositionID;
	private int eyeRotationID;
	
	private int resolutionID;
	
	@Override
	protected void getUniformLocations()
	{
		
		//transformationMatrixID = super.getUniformLocation("transformationMatrix");
		//projectionMatrixID = super.getUniformLocation("projectionMatrix");

		resolutionID = super.getUniformLocation("resolution");
		viewMatrixID = super.getUniformLocation("viewMatrix");
		eyePositionID = super.getUniformLocation("eyePosition");
		eyeRotationID = super.getUniformLocation("eyeRotation");
		
		timeID = super.getUniformLocation("time");
	}

	@Override
	protected void bindAttributes()
	{
		//super.bindAttribute(0, "position");
		//super.bindAttribute(1, "visible");
		//super.bindFragData(0, "color");
	}
	
	private long startTime = 0L;
	public void startTime()
	{
		startTime = System.currentTimeMillis();
	}
	
	public void setUpdatedTime()
	{
		super.setFloat(timeID, ((float)(System.currentTimeMillis() - startTime)) / 1000f);
	}
	
	public void setResolution(Vector2f v)
	{
		super.setVector2f(resolutionID, v);
	}
	
	public void setEyePosition(Vector3f v)
	{
		super.setVector3f(eyePositionID, v);
	}
	
	public void setEyeRotation(float yaw, float pitch, float roll)
	{
		super.setVector3f(eyeRotationID, new Vector3f(yaw, pitch, roll));
	}
	
	public void setTransformationMatrix(Matrix4f m)
	{
		super.setMatrix(transformationMatrixID, m);
	}
	
	public void setViewMatrix(Matrix4f m)
	{
		super.setMatrix(viewMatrixID, m);
	}
	
	public void setProjectionMatrix(Matrix4f m)
	{
		super.setMatrix(projectionMatrixID, m);
	}
}