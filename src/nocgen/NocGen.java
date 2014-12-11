package nocgen;

import nocgen.util.FilesNames;

/**
 * @author Felipe Todeschini Bortolon
 * 
 * 08/15/2011 - added "Hermes_switchcontrol.vhd" and "Hermes_package.vhd". Created all the structure 
 * 				described on the bottom of the page.
 * 
 * 08/16/2011 - added "Hermes_table.vhd" generation. New methods: generateTableTag(int maxX,int maxY),
 * 				generateRoutingTable(int posX, int posY, int maxX, int maxY), generateAllVHDs().
 *
 * 08/17/2011 - added "Hermes_crossbar.vhd","Hermes_switchcontrol.vhd" and "RouterCC.vhd". 
 * 				Started "NOC.vhd" automatic generation. Stopped at: Routers Instantiation. 
 * 
 * 08/18/2011 - completed "NOC.vhd". I've already tested all vhdl's syntax(result = 100%).
 * 				Still need to test a NoC 16x16 in which every router send a package for every
 * 				other router. Thus every router will send fifteen packages. 
 * 
 * 08/24/2011 - started topNoC.vhd. Must create a method that generates the VHDL which sends
 * 				a package to every other router 
 * 08/25/2011 - almost finished topNoC.vhd. Reminder to tomorrow: create Char convASCII(String ascii).
 * 
 * 09/12/2011 - Rearranged the code hierarchy. 
 * 
 * 09/22/2011 - Trying to find a way to change the Substitution Tags by commentaries. 
 * 				It would be much more useful. Tomorrow take a look out for charAt(pos)
 * 				method!
 * 09/23/2011 - Now the reading method of File is ignoring the commented lines (starts
 * 				with '?') and inserting the tag when appears '!'. There is some problems
 * 				with TopFile. Somehow the old method does not work anymore, it only
 * 				generates the topNoC.vhd. Therefore, I made a method less optimized but
 * 				works.
 */

public class NocGen implements FilesNames
{
	public static boolean main(String args[]) 
	{
		int maxX = 2, maxY = 2, maxRegion = 4;
		String algorithmCode;
		String sourceFilePath = "error";
		String rbrOption;
		
		switch(args.length)
		{
			case 6:
				maxX = new Integer(args[0]).intValue();
				maxY = new Integer(args[1]).intValue();
				sourceFilePath = args[2];
				maxRegion = new Integer(args[3]).intValue();
				algorithmCode = args[4];
				rbrOption = args[5];
				break;
				
			default:
				System.out.println("Format> java Main -dx -dy -p -a -mr");
				System.exit(-1);
				return false;
		}
		
		System.out.println("maxX = " + maxX + " maxY = " + maxY);
		TopFile allFiles = new TopFile(maxX, maxY, sourceFilePath, algorithmCode, maxRegion, rbrOption);
		System.out.println(allFiles);
		return true;		
	}
}


/**		## Notes for further documentation ##
 * 
 * NumberFormatConversion :
 * 				Methods to convert the number format ( decimal -> hex or bin ).
 * 
 * FilesPath:
 * 				Contains the template files path and the new files path.
 * 
 * TopFiles:
 * 				This class contains all the files that must be generated( Hermes_package.vhd,
 * 		Hermes_switchcontrol.vhd, Hermes_buffer.vhd, Hermes_crossbar.vhd, Hermes_Table.vhd, 
 * 		RouterCC.vhd, NOC.vhd).
 * 
 * File:
 * 				This class is the father of all Files. Contains five attributes:  
 * 					filePath = new files path;
 * 					templatePath = templates path;
 * 					tag = piece of code that must be inserted in the place of 
 * 							### Substitution Tag ###
 * 					maxX = router max lines
 * 					maxY = router max columns 
 * 				
 * 				and two methods:
 * 		
 * 					createStringFiles:	
 * 							creates a string with the entire VHDL. An object(Files) must 
 * 							be sent by parameter. This object will be manipulated in order 
 * 							to include the tag on the string. The template path is obtained
 * 							through the function getTemplatePath(). This method returns a
 * 							textString.
 * 							
 * 					writeFile:
 * 							this method complements the previous one. It receives by parameter
 * 							an object and a textString. This time it uses that object in 
 * 							order to get the destiny path of the new file created with 
 * 							the textString.
 */