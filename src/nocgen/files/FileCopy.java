package nocgen.files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileCopy
{
	private String targetFile;       // Contains target file name and path
	private String sourceFile;       // Contains source file name and path
	protected static int maxX, maxY; // NoC size
	protected static int regionMax;	// Number o regions
	protected static boolean rbrOption; 
	private static boolean initialParameterSetted = false;
	
	public FileCopy(String fileName, String filePath, String templatePath)
	{
		if(!initialParameterSetted)
			throw new RuntimeException("Invalid use! Firtly use setColumnsLinesRegions method!");
		this.targetFile = FileCopy.pathNameCat(filePath, fileName);
		this.sourceFile = FileCopy.pathNameCat(templatePath, fileName);
		writeOperation();
	}
	
	public void writeOperation()
	{
		writeFile(createStringFiles());
	}
	
	public void setSourceFile(String templatePath, String fileName)
	{
		this.sourceFile = FileCopy.pathNameCat(templatePath, fileName);
	}
	
	/* temporary methods */
	
	public String targetFile()
	{
		return targetFile;
	}
	
	public String sourceFile()
	{
		return sourceFile;
	}
	
	/**/
	
	public static void setColumnsLinesRegions(int columns, int lines, int regions, String rbrOption)
	{
		FileCopy.maxX = columns;
		FileCopy.maxY = lines;
		FileCopy.regionMax = regions;
		if(rbrOption.equals("true"))
			FileCopy.rbrOption =  true;
		else
			FileCopy.rbrOption =  false;
		FileCopy.initialParameterSetted = true;
		//System.out.println("FileCopy.maxX = " + maxX + " FileCopy.maxY = " + maxY);
	}

	public static String pathNameCat(String path, String name)
	{
		return path + java.io.File.separatorChar + name;
	}
	/**
	 * 	This method reads the file in the given path (templatePath) and stores it.
	 * There are some lines that cannot be stored because they are commentaries.
	 * When the line starts with:
	 * 			? - commentary line;
	 * 			! - end of commentary, insert tag;
	 * @return a String with all VHDL code.
	 */
	public String createStringFiles()
	{
		String line = "";    		// String that holds current file line
		String textString = "";		// String that holds all file
		char aux = ' ';
		try
		{
			FileReader input = new FileReader(sourceFile);
			BufferedReader bufRead = new BufferedReader(input);	
			
			line = bufRead.readLine();		// get the first line
			textString = line;				// replaces the blank space by the first line
			line = bufRead.readLine();		// get the second line
	        // Read through file one line at time.
	        while(line != null)
	        {  	
	        	if(line.equals("") == false)
	        		aux = line.charAt(0);
	        	else
	        		aux = 'a';
	        	if(aux == '!')
	        		textString = addTag(textString);
	        	else if(aux != '?')
	        		textString = textString + "\n" + line;
	        	line = bufRead.readLine();
	        }
	        bufRead.close();             
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return textString;
	}
	protected String addTag(String str) // changed private to protected
	{
		return str;
	}
	
	/**
	 * This method creates a new .vhd file in the given path and writes 
	 * on it the given string
	 * @param textString : vhdl code
	 */
	public void writeFile(String textString)
	{
		try
		{	
			File f = new File(targetFile);
			f.getParentFile().mkdirs();

				FileWriter outFile = new FileWriter(f);
				PrintWriter out = new PrintWriter(outFile);
				out.print(textString);
				out.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Error : Invalid source path!"); if directory is root
		}
	}
}