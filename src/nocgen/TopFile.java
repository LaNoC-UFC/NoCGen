package nocgen;

import nocgen.files.BufferFile;
import nocgen.files.FileCopy;
import nocgen.files.NoCFile;
import nocgen.files.PackageFile;
import nocgen.files.RouterCCFile;
import nocgen.files.RoutingMechanismFile;
import nocgen.files.SwitchControlFile;
import nocgen.files.TableFile;
import nocgen.files.TopNoCFile;
import nocgen.util.FilesNames;
import nocgen.util.FilesPaths;

public class TopFile implements FilesPaths {
	/**
	 * @param maxX
	 *            => max Columns
	 * @param maxY
	 *            => max Lines
	 */
	public TopFile(int maxX, int maxY, String sourceFilePath, String algorithm, int maxRegions, String rbrOption) 
	{
		FileCopy.setColumnsLinesRegions(maxX, maxY, maxRegions, rbrOption);
		generateAllVHDs(sourceFilePath, rbrOption);
	}

	/**
	 * Generates all the .vhd files.
	 * 
	 * @param sourceFilePath
	 *        - contains the path where all .vhd files will be stored
	 */
	private void generateAllVHDs(String sourceFilePath, String rbrOption) {
		
		new PackageFile(sourceFilePath, templatePath);
		new SwitchControlFile(sourceFilePath, templatePath);
		new BufferFile(sourceFilePath, templatePath);
		//new FileCopy(FilesNames.routerCCFileName, sourceFilePath, templatePath);
		new RouterCCFile(sourceFilePath,templatePath);
		new NoCFile(sourceFilePath, templatePath);
		new TopNoCFile(sourceFilePath, templatePath);
		new TableFile(sourceFilePath, templatePath);
		new FileCopy(FilesNames.crossbarFileName, sourceFilePath, templatePath);
		new FileCopy(FilesNames.faultDetectionMechanism, sourceFilePath, templatePath);
		
		if(rbrOption.equals("true"))
			new RoutingMechanismFile(sourceFilePath, templatePath);

	}
}