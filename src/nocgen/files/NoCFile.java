package nocgen.files;

import nocgen.util.FilesNames;
import nocgen.util.NumberFormatConversion;

public class NoCFile extends FileTagCopy implements FilesNames
{
	public NoCFile(String filePath, String templatePath)
	{
		super(FilesNames.nocFileName, filePath, templatePath);
	}
	public String generateTag()
	{
		String str = "";
		// declare signals
		for(int y = 0; y < maxY; y++)
		{
			str = str +
					generateNocSignals(y, "clock_rx", "regNport") +
					generateNocSignals(y, "rx", "regNport") + 
					generateNocSignals(y, "data_in", "arrayNport_regphit") +
					generateNocSignals(y, "credit_o", "regNport") +
					generateNocSignals(y, "clock_tx", "regNport") +
					generateNocSignals(y, "tx", "regNport") +
					generateNocSignals(y, "data_out", "arrayNport_regphit") +
					generateNocSignals(y, "credit_i", "regNport") +
					generateNocSignals(y, "testLink_i", "regNport") +
					generateNocSignals(y, "testLink_o", "regNport") +
					generateNocSignals(y, "retransmission_i", "regNport") +
					generateNocSignals(y, "retransmission_o", "regNport");
			if(y != maxY - 1)
					str = str + "\n";
		}
		// instantiate the routers
		str = str + "begin\n\n" + 
		"		fillLocalFlits: for i in 0 to NROT-1 generate\n" +
		"		begin\n" +
		"			data_inLocal(i) <= data_inLocal_flit(i) & CONV_STD_LOGIC_VECTOR(0,TAM_HAMM);\n" +
		"			data_outLocal_flit(i) <= data_outLocal(i)(TAM_PHIT-1 downto TAM_HAMM);\n" +
		"		end generate;\n\n" +
		generateNocRouters();
		// router attribution

		str = str + generateNocAttributions();
		return str;
	}
	private String generateNocAttributions()
	{
		String routerAttribution = "";
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				String sourceRouterAddress = NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y);		// current router address
				// this arrays contains the destiny 
				// position		|	meaning
				//	  0			|   clock_txNxxxx(?)
				//	  1			|   txNxxxx(?)
				//	  2			|   data_outNxxxx(?)
				//	  3			|   credit_oNxxxx(?)
				//-------------------------------
				String[] eastRouterAddress  = new String[6];
				String[] westRouterAddress  = new String[6];
				String[] northRouterAddress = new String[6];
				String[] southRouterAddress = new String[6];
				
				//   this piece of code sets the connection between routers.
				// if the router is located at the extremes of the NoC its
				// variable will be set to zero.
				
				//EAST
				if(x == maxX-1)
				{
					eastRouterAddress[0] = "'0'";			//clock_tx
					eastRouterAddress[1] = "'0'";			//tx
					eastRouterAddress[2] = "(others=>'0')"; //data_out
					eastRouterAddress[3] = "'0'";			//credit_o
					eastRouterAddress[4] = "'0'";			//testLink_o
					eastRouterAddress[5] = "'0'";			//retransmission_o
				}
				else 
				{
					eastRouterAddress[0] = "clock_txN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
					eastRouterAddress[1] = "txN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
					eastRouterAddress[2] = "data_outN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
					eastRouterAddress[3] = "credit_oN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
					eastRouterAddress[4] = "testLink_oN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
					eastRouterAddress[5] = "retransmission_oN" + NumberFormatConversion.convHex(x+1) + NumberFormatConversion.convHex(y) + "(1)";
				}
				//WEST
				if(x == 0)
				{
					westRouterAddress[0] = "'0'";			//clock_tx
					westRouterAddress[1] = "'0'";			//tx
					westRouterAddress[2] = "(others=>'0')"; //data_out
					westRouterAddress[3] = "'0'";			//credit_o
					westRouterAddress[4] = "'0'";			//testLink_o
					westRouterAddress[5] = "'0'";			//retransmission_o
				}
				else 
				{
					westRouterAddress[0] = "clock_txN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
					westRouterAddress[1] = "txN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
					westRouterAddress[2] = "data_outN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
					westRouterAddress[3] = "credit_oN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
					westRouterAddress[4] = "testLink_oN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
					westRouterAddress[5] = "retransmission_oN" + NumberFormatConversion.convHex(x-1) + NumberFormatConversion.convHex(y) + "(0)";
				}
				//NORTH	
				if(y == maxY-1)
				{
					northRouterAddress[0] = "'0'";			 //clock_tx
					northRouterAddress[1] = "'0'";			 //tx
					northRouterAddress[2] = "(others=>'0')"; //data_out
					northRouterAddress[3] = "'0'";			 //credit_o
					northRouterAddress[4] = "'0'";			 //testLink_o
					northRouterAddress[5] = "'0'";			//retransmission_o
				}
				else 
				{
					northRouterAddress[0] = "clock_txN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
					northRouterAddress[1] = "txN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
					northRouterAddress[2] = "data_outN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
					northRouterAddress[3] = "credit_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
					northRouterAddress[4] = "testLink_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
					northRouterAddress[5] = "retransmission_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y+1) + "(3)";
				}
				//SOUTH				
				if(y == 0)
				{
					southRouterAddress[0] = "'0'";			 //clock_tx
					southRouterAddress[1] = "'0'";			 //tx
					southRouterAddress[2] = "(others=>'0')"; //data_out
					southRouterAddress[3] = "'0'";			 //credit_o
					southRouterAddress[4] = "'0'";			 //testLink_o
					southRouterAddress[5] = "'0'";			//retransmission_o
					
				}
				else 
				{
					southRouterAddress[0] = "clock_txN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
					southRouterAddress[1] = "txN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
					southRouterAddress[2] = "data_outN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
					southRouterAddress[3] = "credit_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
					southRouterAddress[4] = "testLink_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
					southRouterAddress[5] = "retransmission_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y-1) + "(2)";
				}
				// tag attribution : begin
				routerAttribution = 	routerAttribution +  
						"	-- ROUTER " + sourceRouterAddress + "\n" +
						// EAST
						"	-- EAST port\n" +
						"	clock_rxN"   + sourceRouterAddress + "(0)<="+ eastRouterAddress[0] +";\n" +
						"	rxN"         + sourceRouterAddress + "(0)<="+ eastRouterAddress[1] +";\n" +
						"	data_inN"    + sourceRouterAddress + "(0)<="+ eastRouterAddress[2] +";\n" +
						"	credit_iN"   + sourceRouterAddress + "(0)<="+ eastRouterAddress[3] +";\n" +
						"   testLink_iN" + sourceRouterAddress + "(0)<="+ eastRouterAddress[4] +";\n" +
						"   retransmission_iN" + sourceRouterAddress + "(0)<="+ eastRouterAddress[5] +";\n" +
						//WEST
						"	-- WEST port\n" +
						"	clock_rxN" + sourceRouterAddress + "(1)<="+ westRouterAddress[0] +";\n" +
						"	rxN" + sourceRouterAddress + "(1)<="+ westRouterAddress[1] +";\n" +
						"	data_inN" + sourceRouterAddress + "(1)<="+ westRouterAddress[2] +";\n" +
						"	credit_iN" + sourceRouterAddress + "(1)<="+ westRouterAddress[3] +";\n" +
						"	testLink_iN" + sourceRouterAddress + "(1)<="+ westRouterAddress[4] +";\n" +
						"	retransmission_iN" + sourceRouterAddress + "(1)<="+ westRouterAddress[5] +";\n" +
						//NORTH
						"	-- NORTH port\n" +
						"	clock_rxN" + sourceRouterAddress + "(2)<="+ northRouterAddress[0] +";\n" +
						"	rxN" + sourceRouterAddress + "(2)<="+ northRouterAddress[1] +";\n" +
						"	data_inN" + sourceRouterAddress + "(2)<="+ northRouterAddress[2] +";\n" +
						"	credit_iN" + sourceRouterAddress + "(2)<="+ northRouterAddress[3] +";\n" +
						"	testLink_iN" + sourceRouterAddress + "(2)<="+ northRouterAddress[4] +";\n" +
						"	retransmission_iN" + sourceRouterAddress + "(2)<="+ northRouterAddress[5] +";\n" +
						//SOUTH
						"	-- SOUTH port\n" +
						"	clock_rxN" + sourceRouterAddress + "(3)<="+ southRouterAddress[0] +";\n" +
						"	rxN" + sourceRouterAddress + "(3)<="+ southRouterAddress[1] +";\n" +
						"	data_inN" + sourceRouterAddress + "(3)<="+ southRouterAddress[2] +";\n" +
						"	credit_iN" + sourceRouterAddress + "(3)<="+ southRouterAddress[3] +";\n" +
						"	testLink_iN" + sourceRouterAddress + "(3)<="+ southRouterAddress[4] +";\n" +
						"	retransmission_iN" + sourceRouterAddress + "(3)<="+ southRouterAddress[5] +";\n" +
						//LOCAL	
						"	-- LOCAL port\n" +
						"	clock_rxN" + sourceRouterAddress + "(4)<=clock_rxLocal(N" + sourceRouterAddress + ");\n" +
						"	rxN" + sourceRouterAddress + "(4)<=rxLocal(N" + sourceRouterAddress + ");\n" +
						"	data_inN" + sourceRouterAddress + "(4)<=data_inLocal(N" + sourceRouterAddress + ");\n" +
						"	credit_iN" + sourceRouterAddress + "(4)<=credit_iLocal(N" + sourceRouterAddress + ");\n" +
						"	testLink_iN" + sourceRouterAddress + "(4)<='0';\n" +
						"	clock_txLocal(N" + sourceRouterAddress + ")<=clock_txN" + sourceRouterAddress + "(4);\n" +
						"	txLocal(N" + sourceRouterAddress + ")<=txN" + sourceRouterAddress + "(4);\n" +
						"	data_outLocal(N" + sourceRouterAddress + ")<=data_outN" + sourceRouterAddress + "(4);\n" +
						"	credit_oLocal(N" + sourceRouterAddress + ")<=credit_oN" + sourceRouterAddress + "(4);\n" +	
						"	retransmission_iN" + sourceRouterAddress + "(4)<='0';\n\n" ;
						
			}
		}
		
		return routerAttribution;
	}
	private String generateNocRouters()
	{
		String routerString = "";
		for(int y = 0; y < maxY ; y++)
		{
			for(int x = 0; x < maxX ; x++)
			{
				routerString = 	routerString + 
								"	Router" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + " : Entity work.RouterCC\n" +
								"	generic map( address => ADDRESSN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + " )\n" +
								"	port map(\n" +
								"		clock    => clock(N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "),\n" +
								"		reset    => reset,\n" +
								"		clock_rx => clock_rxN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		rx       => rxN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		data_in  => data_inN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		credit_o => credit_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		clock_tx => clock_txN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		tx       => txN"       + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		data_out => data_outN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		credit_i => credit_iN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		testLink_i => testLink_iN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		testLink_o => testLink_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		retransmission_i => retransmission_iN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ",\n" +
								"		retransmission_o => retransmission_oN" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + ");\n\n" ;
			}
		}
		return routerString;
	}
	private String generateNocSignals(int y, String name, String type)
	{
		String signalString = "	signal ";
		for(int x = 0; x < maxX; x++)
		{
			signalString = 	signalString + name + "N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y);
			if(x != maxX-1)
				signalString = signalString	+ ", ";
		}
		signalString = signalString + " : " + type + ";\n"; 
		return signalString;
	}
}
