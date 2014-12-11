package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.NumberFormatConversion;

public class TopNoCFile extends FileTagCopy
{
	public TopNoCFile(String filePath, String templatePath)
	{
		super(FilesNames.topNoCFileName, filePath, templatePath);
	}
	public String generateTag()
	{
		String str = "";
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				str = str + 
				"		N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "_Authentication:entity work.SendAuthenticate\n" +
				"			generic map(address => ADDRESSN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ")\n" +
				"			port map(\n" +
				"				clock_tx     =>   clock_tx(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "),\n" +
				"				reset        =>   reset,\n" +
				"				package_received => package_received(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "),\n" +
				"				data_out     => data_out(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "),\n" +
				"				tx           => tx(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ")\n" +
				"			);\n\n";
			}
		}
		return str;
	}
}
