package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.FilesPaths;
import nocgen.util.NumberFormatConversion;

public class SwitchControlFile extends FileTagCopy
{
	public SwitchControlFile(String filePath, String templatePath) 
	{
		super(FilesNames.swithControlFileName,filePath, templatePath);
	}
	
	public void writeOperation()
	{
		verifyTemplatePath(FilesPaths.templatePath);
		writeFile(createStringFiles());
	}
	
	/**
	 *  Verifies if the Region Based Routing option was selected. When this
	 *  condition is true, the template file is changed. 
	 * @param filePath 
	 */
	public void verifyTemplatePath(String templatePath)
	{
		String fileName;
		if(rbrOption)
			fileName = FilesNames.swithControlFileNameRBR_XY;
		else
			fileName = FilesNames.swithControlFileNameXY;
		
		setSourceFile(templatePath, fileName);
	}
	
	/**
	 *  This method selects which tag generator method will be used.
	 *  At this very moment, it doesn't have any utility.
	 * 
	 *  @return a string with the tag.
	 */
	public String generateTag()
	{
		return generateTagXY();
	}
	/**
	 * This method generates the vhdl for non Region based table using XY algorithm
	 * 
	 * @return String containing the vhdl for XY non Region based algorithm.
	 */
	public String generateTagXY()
	{
		String str = "";
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				str = str +
				"	N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "_Table: 	if (address = ADDRESSN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ") generate\n" + 
				"			N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "_Table: entity work.Table(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table)\n" + 
				"				port map(\n" + 
				"					tx  => tx,\n" + 
				"					ty  => ty,\n" + 
				"					dir => dir\n" + 
				"				);\n" + 
				"	end generate N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "_Table;\n\n";
			}
		}
		return str;
	}
}
