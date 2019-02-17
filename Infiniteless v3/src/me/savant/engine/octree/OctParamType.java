package me.savant.engine.octree;

public enum OctParamType
{
	BOOLEAN, INTEGER, DOUBLE, COLOR;
	
	public boolean canParse(String s)
	{
		if(this == BOOLEAN)
			if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
				return true;
		
		if(this == INTEGER || this == DOUBLE || this == COLOR)
		{
			int i = 0;
			if(s.charAt(0) == '-')
			{
				if(s.length() == 1)
				{
					return false;
				}
				i = 1;
			}
			for(; i < s.length(); i++)
			{
				char c = s.charAt(i);
				if(c == '.')
					continue;
				if(c < '0' || c > '9')
				{
					return false;
				}
			}
			if(this == COLOR)
			{
				int l = Integer.parseInt(s);
				if(l <= 27 && l >= 1)
				{
					return true;
				}
				return false;
			}
			return true;
		}
		
		return false;
	}
}