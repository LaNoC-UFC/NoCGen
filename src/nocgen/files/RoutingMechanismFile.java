package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.RouterChannels;
import nocgen.util.NumberFormatConversion;

public class RoutingMechanismFile extends FileTagCopy implements RouterChannels
{

	public RoutingMechanismFile(String filePath, String templatePath)
	{
		super(FilesNames.rmFileName, filePath, templatePath);
	}
	
	public String generateTag()
	{
		String str = "";
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				str = str +
					  "	Table_N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ": 	if (address = ADDRESSN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ") generate\n" +
				      "			Table_N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ": entity work.Table(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table)\n" +
				      "				port map(\n" +
				      "					ce => ce,\n" +
				      "					dest => dest,\n" +
				      "					buffCtrl => buffCtrl,\n" +
				      "					ctrl => ctrl,\n" +
				      "					ceT => ceT,\n" +
				      "					operacao => operacao,\n" +
				      "					data => data\n" +
				      "				);\n" +
				      "	end generate Table_N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ";\n\n";
			}
		}
		
		return str;
	}
}
