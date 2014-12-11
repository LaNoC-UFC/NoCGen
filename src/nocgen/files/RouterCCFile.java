package nocgen.files;

import nocgen.util.FilesNames;

public class RouterCCFile extends FileTagCopy
{
	public RouterCCFile(String filePath, String templatePath)
	{
		super(FilesNames.routerCCFileName, filePath, templatePath);
		count = 0;
	}
	
	private int count;
	/**
	 * This method generates the package tag. If the region based routing option
	 * was select, will be added to the string the new types.
	 * @return String with the tag.
	 */
	public String generateTag()
	{
		String str = "";
		//System.out.println("valor de count" + count);
		if(rbrOption){
//			if(count1==0)
//			{
//				
//			}
//			else if(count1 == 1 || count1 == 3 || count1 == 5 || count1 == 7)
//			{
//				str = "\tgeneric map(address => address)\n";	
//			}
//			else if(count1 == 10)
//			{
//				str =	"\tc_ctrl=> c_ctrl,\n"+
//				"\tc_buffCtrlOut=> c_BuffCtrl,\n"+
//				"\tc_codigoCtrl=> c_CodControle,\n"+
//				"\tc_chipETable => c_ceTR,\n"+
//				"\tc_buffCtrlFalha => c_BuffTabelaFalhas(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"\tc_ceTF_out => c_ceTF(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"\tc_error_Find => c_erro_ArrayFind(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"\tc_error_dir => c_erro_dir,\n"+
//				"\tc_tabelaFalhas => c_tabela_falhas,\n";
//			}
//			else if(count1 == 2 || count1 == 4 || count1 == 6 || count1 == 8)
//			{
//				str = 	"c_buffCtrlFalha => c_BuffTabelaFalhas(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"c_ceTF_out => c_ceTF(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"c_error_Find => c_erro_ArrayFind(" + Integer.toString(((count1/2)-1)) +"),\n"+
//				"c_error_dir => c_erro_dir,\n"+
//				"c_tabelaFalhas => c_tabela_falhas,\n";
//			}
			switch(count){
				case 0: str = 	"------------New Hardware------------\n" +
								"signal c_ctrl : std_logic;\n" +
								"signal c_CodControle : regflit;\n" +
								"signal c_BuffCtrl : buffControl;\n" +
								"signal c_ceTR : std_logic; --[c_ce][T]abela[R]oteamento\n" +
								"signal c_ceTF : regNport := (others=>'0'); --[c_ce][T]abela[F]alhas\n" +
								"signal c_BuffTabelaFalhas : ArrayRegNport := (others=>(others=>'0'));\n" +
								"signal c_erro_ArrayFind : ArrayRouterControl;\n" +
								"signal c_erro_dir : regNport;\n" +
								"signal c_tabela_falhas: regNport;\n"+
								"signal c_test_link_out: regNport;\n"+
								"signal c_data_test: regFlit;\n"+
								"signal credit_i_A : regNport;\n"+
								"signal credit_o_A : regNport;\n"+
								"signal data_out_A : arrayNport_regflit;\n"+
								"signal c_stpLinkTst : regNport;\n"+
								"signal c_strLinkTst : regNport;\n"+
								"signal c_faultTableFDM : regNport;\n"+
								"signal c_strLinkTstOthers : regNport := (others=>'0');\n";
						break;
				case 1: 
				case 3: 
				case 5: 
				case 7:
				case 9:	str = "\tgeneric map(address => address)"; break;
				
				case 10:str =	"\t\tc_ctrl=> c_ctrl,\n" +  //local
								"\t\tc_buffCtrlOut=> c_BuffCtrl,\n" +
								"\t\tc_codigoCtrl=> c_CodControle,\n" +
								"\t\tc_chipETable => c_ceTR,\n";
				case 2:
				case 4:
				case 6:
				case 8: str += 	"\t\tc_buffCtrlFalha => c_BuffTabelaFalhas(" + Integer.toString(((count/2)-1)) +"),\n"+ //LOCAL-SOUTH-NORTH-EAST-WEST
								"\t\tc_ceTF_out => c_ceTF(" + Integer.toString(((count/2)-1)) +"),\n"+
								"\t\tc_error_Find => c_erro_ArrayFind(" + Integer.toString(((count/2)-1)) +"),\n"+
								"\t\tc_error_dir => c_erro_dir,\n"+
								"\t\tc_tabelaFalhas => c_tabela_falhas,\n"+
								"\t\tc_strLinkTst => c_strLinkTst(" + Integer.toString(((count/2)-1)) +"),\n"+
								"\t\tc_stpLinkTst => c_stpLinkTst(" + Integer.toString(((count/2)-1)) +"),\n"+
								"\t\tc_strLinkTstOthers => c_strLinkTstOthers(" + Integer.toString(((count/2)-1)) +"),";break;
								
				case 11: str = 	"\t\tc_Ctrl => c_ctrl,\n"+ //SWITCH CONTROL
								"\t\tc_buffTabelaFalhas_in=> c_BuffTabelaFalhas,\n"+
								"\t\tc_CodControle => c_CodControle,\n"+
								"\t\tc_BuffCtrl => c_BuffCtrl,\n"+
								"\t\tc_ce => c_ceTR,\n"+
								"\t\tc_ceTF_in => c_ceTF,\n"+
								"\t\tc_error_ArrayFind => c_erro_ArrayFind,\n"+
								"\t\tc_error_dir => c_erro_dir,\n"+
								"\t\tc_tabelaFalhas => c_tabela_falhas,\n"+
								"\t\tc_strLinkTst => c_strLinkTst,\n"+
								"\t\tc_faultTableFDM => c_faultTableFDM,";
				default: break;
				
			}
			count++;
		}
		else{
			str = "?";
		}
		
		return str;
	}
}