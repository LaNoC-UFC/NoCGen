package nocgen.files;
import java.util.HashMap;
import java.util.List;

import nocgen.rbr.*;
import nocgen.util.*;

public class TableFile extends FileTagCopy implements RouterChannels
{
	public TableFile(String filePath, String templatePath) 
	{
		super(FilesNames.tableFileName, filePath, templatePath);
	}
	
	/**
	 * TODO new comment
	 * 
	 *  This method selects which tag generator method will be used
	 *according to the select region number and algorithm by the user.
	 * 
	 *  @return a string with the tag.
	 */
	public String generateTag()
	{
		if(rbrOption)
			return generateTagRBR_XY();
		else
			return generateTagXY();
	}
	
	/** 
	 *  This method generates the tag for region based routing using XY algorithm.
	 * @return a string containing all the vhdl code.
	 */
	private String generateTagRBR_XY()
	{
		Rbr rbr = new Rbr(maxX,maxY);
		HashMap<Router, List<String>> tableSet = rbr.computation();
		String str = "";
		str =
			"---------------------------------------------------------\n" +
			"-- RAM Memory\n" +
			"---------------------------------------------------------\n" +
			"library IEEE;\n" +
			"use ieee.std_logic_1164.all;\n" +
			"use ieee.numeric_std.all;\n" +
			"use work.HermesPackage.all;\n\n" +
			"entity Table is\n" +
			"	port( ce : in std_logic; -- chip enable\n" +
			"			dest : in reg8;\n" +
			"			buffCtrl: in buffControl;\n"+
			"			ctrl :	in std_logic;\n"+
			"			ceT : in std_logic;\n"+
			"			operacao: in regflit;\n"+
			"			data : out std_logic_vector(4 downto 0)\n" +
			"		);\n" +
			"end Table;\n\n";
		
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				//int size = usedSize(x, y);
				int size = regionMax;
				
				str = str +
				"architecture N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table of Table is\n" +
				"	signal rowDst, colDst : integer;\n" + 
				signalsRBR_XY(size) +
				"	-------------New Hardware---------------\n" + 
				"	signal VertInf, VertSup : STD_LOGIC_VECTOR(7 downto 0);\n" +
				"	signal func : STD_LOGIC_VECTOR(7 downto 0);\n" +
				"	signal OP : STD_LOGIC_VECTOR(4 downto 0);\n" +
				"	signal i : integer;\n" +
				"\n" +
				"	signal RAM: memory := \n" +
				"	-- VertInf(8) VertSup(8)  OP(5)\n" +
				"	--      XY      XY      L/S/N/W/E\n" +
				"	("+ createRBRTable(x, y, tableSet)  + ");\n" + 
				"begin\n" +
				"	rowDst <= TO_INTEGER(unsigned(dest(7 downto 4))) when ctrl = '0' else 0;\n" +
				"	colDst <= TO_INTEGER(unsigned(dest(3 downto 0))) when ctrl = '0' else 0;\n\n"; 
				
				for(int i = 0; i < size; i++)
				{
					str = str +
						  "	rowInf"+i+" <= TO_INTEGER(unsigned(RAM("+i+")(20 downto 17))) when ctrl = '0' else 0;\n" +
						  "	colInf"+i+" <= TO_INTEGER(unsigned(RAM("+i+")(16 downto 13))) when ctrl = '0' else 0;\n" +
						  "	rowSup"+i+" <= TO_INTEGER(unsigned(RAM("+i+")(12 downto 9))) when ctrl = '0' else 0;\n" +
						  "	colSup"+i+" <= TO_INTEGER(unsigned(RAM("+i+")(8 downto 5))) when ctrl = '0' else 0;\n\n" +
						  "	H"+i+" <= '1' when rowDst >= rowInf"+i+" and rowDst <= rowSup"+i+" and\n" +
						  "		       	   colDst >= colInf"+i+" and colDst <= colSup"+i+" and ctrl = '0' else \n" +
						  "	      '0';\n";
				}
				str = str + "\n	data <= ";
				for(int i = 0; i < size; i++)
				{
					str = str + "RAM("+i+")(4 downto 0) when H"+i+"='1' and ce = '1' and ctrl = '0' else\n";
				}
				str = str + "		(others=>'Z');\n";
				str = str + 
				"	func <= operacao(7 downto 0);\n" +
				"	VertInf <= buffCtrl(0)(7 downto 0);\n" +
				"	VertSup <= buffCtrl(1)(7 downto 0);\n" +
				"	OP <= buffCtrl(2)(4 downto 0);\n\n" +
				"	process(ceT,ctrl)\n" +
				"	begin\n" +
				"		if ctrl = '0' then\n" +
				"			i <= 0;\n" +
				"		elsif ctrl = '1' and ceT = '1' and func = x\"01\" then\n" +
				"			RAM(i)(20 downto 13) <= VertInf(7 downto 0);\n" +
				"			RAM(i)(12 downto 5) <= VertSup(7 downto 0);\n" +
				"			RAM(i)(4 downto 0) <= OP(4 downto 0);\n" +
				"			i <= i + 1;\n" +
				"		end if;\n" +
				"	end process;\n";
				str = str + "end N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table;\n" +
							"--------------------------------------------------------------------------\n\n";
			}
		}		
		return str;
	}
	
	/**
	 * This method is used for region based routing. Locates the router position
	 * and find out the existing number of regions ( = number of spaces allocated
	 * in the memory).
	 * 
	 * @param x Current router 'x' position
	 * @param y Current router 'y' position
	 * @return A integer containing the used memory size
	 */
	private int usedSize(int x, int y)
	{
		if((x==0 && y==0) || (x==maxX-1 && y==maxY-1) || 
		   (x == 0 && y==maxY-1) || (x == maxX-1 && y==0))
			return 2;
		else if(x==0 || y==0 || x==maxX-1 || y==maxY-1)
			return 3;
		else
			return 4;
	}
	/**
	 *  TODO comment this piece of code
	 * @param size
	 * @return
	 */
	private String signalsRBR_XY(int size)
	{
		String str = "";
		
		for(int i = 0; i < size; i++)
		{
			str = str + "	signal rowInf"+i+", colInf"+i+", rowSup"+i+", colSup"+i+" : integer;\n";
		}
		
		str = str + "	signal ";
		for(int i = 0; i < size; i++)
		{
			str = str + "H"+i;
			if(i != size-1)
				str = str + ", ";
		}
		str = str + ": std_logic := '0';\n";
		return str;
	}
	
	/**
	 *  This method selects from a HashMap<Router, List<String>> the right data
	 *  to insert in which router table defined by x and y parameters.
	 * 
	 * @param x Column position.
	 * @param y Line position.
	 * @param tableSet Set of all tables.
	 * @return A String containing the table for the Router(x,y).
	 */
	private String createRBRTable(int x, int y, HashMap<Router, List<String>> tableSet)
	{
		Router r = new Router(x,y);
		String str = "";
		String unusedSpace = "000000000000000000000";
		int region = regionMax;
		
		for(Router rt : tableSet.keySet())
			if(r.equals(rt))
			{
				for(int i = 0; i < tableSet.get(rt).size(); i++)
				{
					str = str + "(\"" + tableSet.get(rt).get(i) + "\")";
					if(i != regionMax-1)
						str = str + ",";
					str = str + "\n\t ";
					region--;
				}
				int unusedSpacesNumber = regionMax - tableSet.get(rt).size();
				for(int i = 0; i < unusedSpacesNumber; i++)
				{
					str = str + "(\"" + unusedSpace + "\")";
					if(i != region - 1)
						str = str + ",\n\t ";
					else
						str = str + "\n\t";
				}
			}
		return str;
	}
	
	/**
	 *  This method generates the tag for non region based routing using XY algorithm.
	 * 
	 *  @return a string containing all the vhdl code.
	 */
	private String generateTagXY()
	{
		String str = "";
		str = "--	##### Tabela de Roteamento #####\n" +
		"--	Cada arquitetura possui uma tabela de roteamento. Estas serão\n" +
		"--	instanciadas corretamente pelo switch control de cada roteador.\n" +
		"--	As tabelas estão dispostas da seguinte maneira:\n" +
		"--	+ As colunas representam o parâmetro \"ty\". A coluna mais a esquerda\n" +
		"--	  equivale a variável \"ty = 0\", a partir desta devemos incrementar\n" +
		"--	  em um cada coluna que andarmos a direita.\n" +
		"--	+ As linhas representam o parâmetro \"tx\". A linha mais ao topo equivale\n" +
		"--	  a variável \"tx = 0\", a partir desta devemos incrementar em um\n" +
		"--	  cada linha que andarmos para baixo\n" +
		"--	É importante observar que as portas estão dipostas da seguinte maneira:\n" +
		"--			| Local | South | North | West | East |\n" +
		"--	As variáveis \"tx\" e \"ty\" representam o endereço do roteador destino. A\n" +
		"--	variável \"dir\" retorna a direção que o pacote deverá seguir(representada\n" +
		"--	pelo bit 1) para atingir o destino.\n\n" +
		"library IEEE;\n" +
		"use IEEE.STD_LOGIC_1164.all;\n" +
		"use IEEE.STD_LOGIC_unsigned.all;\n" +
		"use work.HermesPackage.all;\n\n" +
		"entity Table is\n" +
		"  port(\n" +
		"	tx, ty: in  regquartoflit;\n" +
		"	dir: out std_logic_vector(NPORT-1 downto 0)\n" +
		"  );\n" +
		"end Table;\n\n";
		
		for(int y = 0; y < maxY; y++)
		{
			for(int x = 0; x < maxX; x++)
			{
				str = str + 
						"architecture N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table of Table is\n" + 
						"  signal N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table: routingTable := \n" +
						"	( " + createRoutingTable(x,y)  + ");\n" + 
						"begin\n" + 
						"  process(tx, ty)\n" + 
						"  variable x,y: integer;\n" + 
						"  begin\n" + 
						"		x := conv_integer(tx);\n" + 
						"		y := conv_integer(ty);\n" +
						"		if((x <= MAX_X) and ( y <= MAX_Y)) then		-- Esta verificação serve para não dar erro\n" +
						"								-- durante a transmissão do dado: se o valor de x/y\n" +
						"								-- fosse maior que o tamanho do array bidimensional\n" +
						"			dir <= N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table(x,y);\n" +
						"		end if;\n" +
						"  end process;\n" +
						"end N" + NumberFormatConversion.convHex(x) + NumberFormatConversion.convHex(y) + "Table;\n\n";
			}
		}
		return str;
	}
	/**  
	 *  This method creates a string with the XY Table (non region based routing) for each router according to the 
	 *parameters "posX" and "posY". The integers "x" and "y" will be the destiny router address.	
	 *Compares the current router position with the destiny router. If they are
	 *in the same column(x) the data might go to North or South depending on which line the
	 *destiny router is. Otherwise it can send to East (x > i) or West (x < i)
	 * 
	 *  Returns: a string containing the vhdl code for each table.
	 */
	private String createRoutingTable(int posX, int posY)
	{
		String routingTable = "";

		for(int x = 0; x < maxX; x ++)
		{
			routingTable = routingTable + "(";
			for(int y = 0; y < maxY; y ++)
			{
				if(x == posX)
				{
					if(y == posY)
						routingTable = routingTable + setLocal;
					else if(y > posY)
						routingTable = routingTable + setNorth;
					else 
						routingTable = routingTable + setSouth;
				}
				else if(x > posX)
					routingTable = routingTable + setEast;
				else
					routingTable = routingTable + setWest;
				// if clause to add comma
				if(y != maxY-1)
					routingTable = routingTable + ", ";
			}
			routingTable = routingTable + ")";
			// if clause to add comma + ENTER
			if(x != maxX-1)
				routingTable = routingTable + ", \n\t  ";
		}
		return routingTable;
	}
	
}
