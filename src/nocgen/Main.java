package nocgen;
import nocgen.ui.WindowPrincipal;

public class Main 
{
	@SuppressWarnings({ "static-access" })
	public static void main(String args [])
	{
		if(args.length == 2) //MaxX MaxY
		{
			System.out.println("Running in command line ...");
			NocGen.main(args);
			System.out.println("Check ./output for VHDL files");
		}
		else
		{
			WindowPrincipal window = new WindowPrincipal();
			window.main(null);
		}
	}

}
