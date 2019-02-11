package me.savant.game;

import java.util.Timer;
import java.util.TimerTask;

public class Performance
{
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
				lf = f;
				f = 0;
			}
		};
		
		t = new Timer();
		t.scheduleAtFixedRate(u, 1000, 1000);
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