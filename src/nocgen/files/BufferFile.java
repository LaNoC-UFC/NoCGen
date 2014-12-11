package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.FilesPaths;

public class BufferFile extends FileTagCopy
{
	public BufferFile(String filePath, String templatePath) 
	{
		super(FilesNames.bufferFileName,filePath, templatePath);
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
			fileName = FilesNames.bufferFileNameRBR;
		else
			fileName = FilesNames.bufferFileName;
		
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
		return "";
	}
}
