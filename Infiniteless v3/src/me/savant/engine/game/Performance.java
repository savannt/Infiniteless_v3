package me.savant.engine.game;

import java.util.Timer;
import java.util.TimerTask;

public class Performance
{
	public static float DELTA_TIME = 0f;
	private static float RUN_INTERVAL = 4; //How many times a second?
	
	private int lf = 0;
	private int f = 0;
	private TimerTask u;
	private Timer t;
	
	public Performance()
	{
		u = new TimerTask()
		{
			public void run()
			{
				lf = (int)((float) f * RUN_INTERVAL);
				DELTA_TIME = (1f / RUN_INTERVAL) / (float) f;
				f = 0;
			}
		};
		
		t = new Timer();
		t.scheduleAtFixedRate(u, 250, 250);
	}
	
	public void update()
	{
		f++;
	}
	
	public int getFPS()
	{
		return lf;
	}
}