package nocgen.util;

public class NumberFormatConversion
{
	public static String convHex(int intNumber)
	{
		return "0" + Integer.toHexString(intNumber);
	}
	
	public static String convBin(int high, int low)
	{
		return convBin4Bits(high) + convBin4Bits(low);
	}
	
	public static String convBin4Bits(int intNumber)
	{
		switch(intNumber)
		{
			case 0:		return "0000";
			case 1:		return "0001";
			case 2:		return "0010";
			case 3:		return "0011";
			case 4:		return "0100";
			case 5:		return "0101";
			case 6:		return "0110";
			case 7:		return "0111";
			case 8:		return "1000";
			case 9:		return "1001";
			case 10:	return "1010";
			case 11:	return "1011";
			case 12:	return "1100";
			case 13:	return "1101";
			case 14:	return "1110";
			case 15:	return "1111";
		}
		new NumberFormatException("Binary number greater than 4 bits!");
		return null;
	}
	
	public static String convDirection(String dir)
	{
		if(dir.equals("[N]")) return "00100";
		else if(dir.equals("[S]")) return "01000";
		else if(dir.equals("[W]")) return "00010";
		else if(dir.equals("[E]")) return "00001";
		else return "error";
	}
}
