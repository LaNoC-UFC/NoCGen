package nocgen.files;

public abstract class FileTagCopy extends FileCopy
{
	public FileTagCopy(String fileName, String filePath, String templatePath)
	{
		super(fileName, filePath, templatePath);
	}
	public abstract String generateTag();
	
	public String addTag(String str)
	{	
		String temp = generateTag();
		if(temp=="?") return str;
		else return str + "\n" + temp;
	}
}



