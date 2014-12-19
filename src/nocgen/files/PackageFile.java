package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.NumberFormatConversion;

public class PackageFile extends FileTagCopy
{
	public PackageFile(String filePath, String templatePath)
	{
		super(FilesNames.packageFileName, filePath, templatePath);
	}
	
	/**
	 * This method generates the package tag. If the region based routing option
	 * was select, will be added to the string the new types.
	 * @return String with the tag.
	 */
	public String generateTag()
	{
		int count = 0;
		String str = "";
		str = 	"---------------------------------------------------------\n" +
				"-- CONSTANTS DEPENDENTES DO NUMERO DE ROTEADORES\n" +
				"---------------------------------------------------------\n" +
				"\tconstant NROT: integer := " + (maxX*maxY) + ";\n\n" +
				"\tconstant MIN_X : integer := 0;\n" +
				"\tconstant MIN_Y : integer := 0;\n" +
				"\tconstant MAX_X : integer := " + (maxX-1) + ";\n" +
				"\tconstant MAX_Y : integer := " + (maxY-1) + ";\n\n" +
				"---------------------------------------------------------\n" +
				"-- CONSTANT TB\n" +
				"---------------------------------------------------------\n" +
				"\tconstant TAM_LINHA : integer := 500;\n\n";
				
				for(int y = 0; y < maxY; y++)
				{
					for(int x = 0; x < maxX; x++)
					{
						str = str +
						"\tconstant N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ": integer :=" + count + ";\n" +
						"\tconstant ADDRESSN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ": std_logic_vector(7 downto 0) :=\"" + NumberFormatConversion.convBin(x,y) + "\";\n";
						count++;
						
					}
				}
				
			if(rbrOption)
				return str + generateTagRBR_XY();
			else
				return str;
	}
	
	public String generateTagRBR_XY()
	{
		String str = 
			"---------------------------------------------------------\n" +
			"-- VARIAVEIS DO NOVO HARDWARE\n" +
			"---------------------------------------------------------\n" +
			"	subtype reg21 is std_logic_vector(20 downto 0);\n" +
			"	subtype reg26 is std_logic_vector(25 downto 0);\n" +
			"	--constant MEMORY_SIZE : integer := " + FileCopy.regionMax + ";\n" +
			"	--type memory is array (0 to MEMORY_SIZE-1) of reg21;\n" +
			"	type buffControl is array(0 to 4) of std_logic_vector((TAM_FLIT-1) downto 0);\n" +
			"	type RouterControl is (invalidRegion, validRegion, faultPort, portError);\n" +
			"	type ArrayRouterControl is array(NPORT downto 0) of RouterControl;\n\n"+
			"	constant c_WR_ROUT_TAB : integer := 1;\n"+
			"	constant c_WR_FAULT_TAB : integer := 2;\n"+
			"	constant c_RD_FAULT_TAB_STEP1 : integer := 3;\n"+
			"	constant c_RD_FAULT_TAB_STEP2 : integer := 4;\n"+
			"	constant c_TEST_LINKS : integer := 5;\n";
		return str;
	}
}