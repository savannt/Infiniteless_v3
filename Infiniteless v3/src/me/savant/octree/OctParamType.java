package me.savant.octree;

public enum OctParamType
{
	BOOLEAN, INTEGER, DOUBLE;
	
	public boolean canParse(String s)
	{
		if(this == BOOLEAN)
			if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
				return true;
		
		if(this == INTEGER || this == DOUBLE)
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
			return true;
		}
		
		return false;
	}
}