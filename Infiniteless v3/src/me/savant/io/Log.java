package me.savant.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log
{
	private static ArrayList<String> a = new ArrayList<String>();
	
	public static void error(String l)
	{
		String s = "[ERROR] [" + getCaller() + ":" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "] " + l;
		System.err.println(s);
		a.add(s);
	}
	
	public static void fatal(String l)
	{
		String s = "[~~FATAL~~] [" + getCaller() + ":" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "] " + l;
		a.add(s);
		save();
		throw new RuntimeException(s);
	}
	
	public static void warning(String l)
	{
		String s = "[Warning] [" + getCaller() + ":" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "] " + l;
		System.err.println(s);
		a.add(s);
	}
	
	public static void log(String l)
	{
		String s = "[" + getCaller() + "] " + l;
		System.out.println(s);
		a.add(s);
	}
	
	public static String getCaller()
	{
		StackTraceElement[] e = Thread.currentThread().getStackTrace();
		for(int i = 1; i < e.length; i++)
		{
			StackTraceElement t = e[i];
			if(!t.getClassName().equalsIgnoreCase(Log.class.getName()) && t.getClassName().indexOf("java.lang.Thread") != 0)
			{
				String[] s = t.getClassName().split("\\.", -1);
				return s[s.length - 1];
			}
		}
		return null;
	}
	
	public static void save()
	{
		try
		{
			File p = new File("log");
			if(!p.exists() || !p.isDirectory())
				p.mkdir();
			FileWriter f = new FileWriter("log\\" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".log");
			BufferedWriter w = new BufferedWriter(f);
			for(String s : a)
				w.write(s + "\n");
			w.close();
			f.close();
		}
		catch (Exception e)
		{
			System.err.println("[Log] Failed to save logging file");
			e.printStackTrace();
		}
	}
}