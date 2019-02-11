package me.savant.octree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import me.savant.io.Log;
import me.savant.octree.color.ColorGen;
import me.savant.octree.color.OctColorIndex;
import me.savant.octree.color.SolidColor;
import me.savant.octree.geometry.GeoGen;
import me.savant.octree.geometry.OctGeoIndex;
import me.savant.octree.geometry.SolidGeo;

public class OctreeCompiler
{
	public List<OctGeoIndex> geoDict = new ArrayList<OctGeoIndex>();
	public List<OctColorIndex> colorDict = new ArrayList<OctColorIndex>();
	
	public EntityDictionary entities = new EntityDictionary();
	
	public OctreeCompiler()
	{
		geoDict.add(new OctGeoIndex(new SolidGeo(), new OctParam[] { new OctParam(0, OctParamType.BOOLEAN )}));
		colorDict.add(new OctColorIndex(new SolidColor(), new OctParam[] { new OctParam(0, OctParamType.INTEGER )}));
	}
	
	public Entity compile(File a)
	{
		try
		{
			long startMilliseconds = System.currentTimeMillis();
			
			BufferedReader r = new BufferedReader(new FileReader(a));
			
			String l;
			
			GeoGen geo = null;
			ColorGen color = null;
			
			reader: while((l = r.readLine()) != null)
			{
				boolean inParameters = false;
				String insideClass = "global";
				OctGeoIndex geoIndex = null;
				OctColorIndex colorIndex = null;
				boolean expectingSemicolon = false;
				boolean isFinished = false;
				ArrayList<String> param = new ArrayList<String>();
				
				String f = "";
				
				for(int i = 0; i < l.length(); i++)
				{
					char s = l.charAt(i);
					
					/** Unclosed Parameter **/
					if(s == '(' && i == l.length() - 1)
					{
						Log.error("SyntaxError: Unclosed Parameter [" + l + "]");
						break reader;
					}
					
					/** Semicolon **/
					if((expectingSemicolon && s != ';') || (s == ')' && i == l.length() - 1))
					{
						Log.error("SyntaxError: Expecting Semicolon at [" + l + "]");
						break reader;
					}
					else if(expectingSemicolon && s == ';')
					{
						isFinished = true;
						expectingSemicolon = false;
					}
					
					/** Unexpected End **/
					if(!isFinished && i == l.length() - 1)
					{
						Log.error("SyntaxError: Unexpected End of Syntax [" + l + "]");							
						break reader;
					}
					
					/** Parameters **/
					if(s == ',')
					{
						if(inParameters)
						{
							if(insideClass.equalsIgnoreCase("geo"))
							{
								if(!geoIndex.hasParams())
								{
									Log.error("SyntaxError: Unexpected Parameter [" + l + "]");
									break reader;
								}
								if(geoIndex.getParam(0).getValueType().canParse(f))
								{
									param.add(f);									
								}
								else
								{
									Log.error("SyntaxError: [" + f + "] is not of parameter type [" + geoIndex.getParam(0).getValueType().name() + "]");
									break reader;
								}
							}
							else if(insideClass.equalsIgnoreCase("color"))
							{
								if(!colorIndex.hasParams())
								{
									Log.error("SyntaxError: Unexpected Parameter [" + l + "]");
									break reader;
								}
								if(colorIndex.getParam(0).getValueType().canParse(f))
								{
									param.add(f);									
								}
								else
								{
									Log.error("SyntaxError: [" + f + "] is not of parameter type [" + colorIndex.getParam(0).getValueType().name() + "]");
									break reader;
								}
							}
							
							
							f = "";
							continue;
						}
						else
						{
							Log.error("SyntaxError: Unexpected Common [" + l + "]");
							break reader;
						}
					}
					
					/** Skip **/
					if(s == ' ')
						continue;
					
					/** Function Check **/
					if(s == '(')
					{
						inParameters = true;
						//Check if function is real
						if(insideClass.equalsIgnoreCase("geo"))
						{
							geoIndex = getGeoIndex(f);
							if(geoIndex == null)
							{
								Log.error("SyntaxError: [" + f + "] is not a valid function of [" + insideClass + "]");
								break reader;
							}
							geo = geoIndex.getGeoGen();
							f = "";
						}
						else if(insideClass.equalsIgnoreCase("color"))
						{
							colorIndex = getColorIndex(f);
							if(colorIndex == null)
							{
								Log.error("SyntaxError: [" + f + "] is not a valid function of [" + insideClass + "]");
								break reader;
							}
							color = colorIndex.getColorGen();
							f = "";
						}
						continue;
					}
					 /** Close Parameters **/
					if(s == ')')
					{
						if(f != "")
						{
							if(insideClass.equalsIgnoreCase("geo"))
							{
								if(!geoIndex.hasParams())
								{
									Log.error("SyntaxError: Unexpected Parameter [" + l + "]");
									break reader;
								}
								int g = 0;
								if(!param.isEmpty())
									g = param.size() - 1;
								if(geoIndex.getParam(g).getValueType().canParse(f))
								{
									param.add(f);									
								}
								else
								{
									Log.error("SyntaxError: [" + f + "] is not of parameter type [" + geoIndex.getParam(g).getValueType().name() + "]");
									break reader;
								}
							}
							else if(insideClass.equalsIgnoreCase("color"))
							{
								if(!colorIndex.hasParams())
								{
									Log.error("SyntaxError: Unexpected Parameter [" + l + "]");
									break reader;
								}
								int g = 0;
								if(!param.isEmpty())
									g = param.size() - 1;
								if(colorIndex.getParam(g).getValueType().canParse(f))
								{
									param.add(f);									
								}
								else
								{
									Log.error("SyntaxError: [" + f + "] is not of parameter type [" + colorIndex.getParam(g).getValueType().name() + "]");
									break reader;
								}
							}
							f = "";
						}
						
						if(insideClass.equalsIgnoreCase("geo"))
						{
							if(geoIndex.hasParams())
							{
								if(param.isEmpty())
								{
									Log.error("SyntaxError: Parameter required for [" + geoIndex.getGeoGen().funcName + "]");
									break reader;									
								}
								else
								{
									if(geoIndex.getParams().length != param.size())
									{
										Log.error("SyntaxError: [" + geoIndex.getParams().length + "] Parameters required for [" + geoIndex.getGeoGen().funcName + "]");
										break reader;
									}
								}
							}
						}
						else if(insideClass.equalsIgnoreCase("color"))
						{
							if(colorIndex.hasParams())
							{
								if(param.isEmpty())
								{
									Log.error("SyntaxError: Parameter required for [" + colorIndex.getColorGen().funcName + "]");
									break reader;									
								}
								else
								{
									if(colorIndex.getParams().length != param.size())
									{
										Log.error("SyntaxError: [" + colorIndex.getParams().length + "] Parameters required for [" + colorIndex.getColorGen().funcName + "]");
										break reader;
									}
								}
							}
						}
						
						expectingSemicolon = true;
						inParameters = false;
						continue;
					}

					/** Classes **/
					if(s == '.')
					{
						if(!f.equalsIgnoreCase("geo") && !f.equalsIgnoreCase("color"))
						{
							Log.error("SyntaxError: [" + f + "] not expected");
							break reader;
						}
						insideClass = f;
						f = "";
						continue;
					}
					
					f = f + s;
				}
			}
			
			if(geo == null)
			{
				Log.error("SyntaxError: Geometry not specified for Entity [" + a.getName() + "]");
			}
			if(color == null)
			{
				Log.error("SyntaxError: Color not specified for Entity [" + a.getName() + "]");
			}
			long compileTime = System.currentTimeMillis() - startMilliseconds;
			if(geo != null && color != null)
			{
				entities.addEntity(new Entity(geo, color), a);
				Log.log("Entity [" + a.getName() + "] successfully compiled in [" + compileTime + "] milliseconds");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.fatal("Unexpected exception " + e.getMessage());
		}
		return null;
	}
	
	private OctGeoIndex getGeoIndex(String n)
	{
		for(OctGeoIndex i : geoDict)
		{
			if(i.getGeoGen().funcName.equalsIgnoreCase(n))
			{
				return i;
			}
		}
		return null;
	}
	
	private OctColorIndex getColorIndex(String n)
	{
		for(OctColorIndex i : colorDict)
		{
			if(i.getColorGen().funcName.equalsIgnoreCase(n))
			{
				return i;
			}
		}
		return null;
	}
}