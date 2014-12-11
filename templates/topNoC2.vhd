library IEEE;
use IEEE.Std_Logic_1164.all;
use std.textio.all;
package aux_functions is  

	procedure readFileLine(file in_file: TEXT; outStrLine: out string);
   
end aux_functions;

package body aux_functions is

  procedure readFileLine(file in_file: TEXT; outStrLine: out string) is
		variable localLine: line;
		variable localChar:  character;
		variable isString: 	boolean;
	begin				
		 readline(in_file, localLine);
		 for i in outStrLine'range loop
			 outStrLine(i) := ' ';
		 end loop;   
		 for i in outStrLine'range loop
			read(localLine, localChar, isString);
			outStrLine(i) := localChar;
			if not isString then -- found end of line
				exit;
			end if;   
		 end loop; 
	end readFileLine;
end aux_functions;

-----------------------------------------
-- RECEIVING AUTHENTICATION 
-----------------------------------------
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_arith.all;
use work.HermesPackage.all;

entity SendAuthenticate is
	generic(address : regmetadeflit := "00000000");
port(
	clock_tx : 	in  std_logic;
	reset :	   in  std_logic;
	data_out : 	in regflit;
	tx : 	      in std_logic;
	package_received : out std_logic := '0');
end SendAuthenticate;

architecture SendAuthenticate of SendAuthenticate is
	
	type state is (S0,S1,S2);
	signal ES, PES: state;
	signal counterSignal : integer :=0;
begin

----------------------------------------------------
-- 	AtribuiÃ§Ã£o do prÃ³ximo estado de acordo com o 
-- clock advindo da topNoC
	process(reset,clock_tx)
	begin
		if reset='1' then
			ES<=S0;
		elsif clock_tx'event and clock_tx='1' then
			ES<=PES;
		end if; 
	end process;

----------------------------------------------------
-- 	Funcionamento/ Controle da mÃ¡quina de estados

	process(clock_tx)
		variable counter : integer := 0;
	begin
		if clock_tx'event and clock_tx='1' then
			case ES is
				-- reset
				when S0 =>
					counter := 0;
					PES <= S1;
				-- verifica o header
				when S1 =>
					if(data_out = x"00" & address and tx='1') then
						PES <= S2; 
					end if;
				-- verifica o payload
				when S2 =>
					if (data_out = x"0ABC" and tx='1') then
						counter := counter+1;
						PES <= S1;
						if(counter = NROT-1) then 
							package_received <= '1';
						else
							package_received <= '0';
						end if;
					end if;
			end case;
			counterSignal <= counter;
		end if;
	end process;
	
end SendAuthenticate;

-----------------------------------------
-- TOP NOC
-----------------------------------------
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_arith.all;
use work.HermesPackage.all;


use IEEE.STD_LOGIC_unsigned.all;
use std.textio.all;
use work.aux_functions.all;

entity topNoC is
end;

architecture topNoC of topNoC is

	signal clock : regNrot:=(others=>'0');
	signal reset : std_logic;
	signal clock_rx: regNrot:=(others=>'0');
	signal rx, credit_o: regNrot;
	signal clock_tx, tx, credit_i: regNrot;
	signal data_in, data_out : arrayNrot_regflit;
	signal do_nothing: std_logic; -- representa o final do envio dos pacotes
	signal package_received : regNrot:=(others=>'0'); -- confirma recebimento de packages
	--signal sgn_end_of_simulation: std_logic := '0';
	--type arrayTemp is array(110 downto 0) of regflit;
	--signal dadoTable : arrayTemp;
	--signal index: integer := 0;
	--signal readF : boolean := true;
	--file ARQ : TEXT open READ_MODE is "mode.txt";
	
begin
	reset <= '1', '0' after 10 ns;
	clock <= not clock after 10 ns;
	clock_rx <= not clock_rx after 10 ns;	
	credit_i <= (others=>'1');
	
	NOC: Entity work.NOC
	port map(
		clock         => clock,
		reset         => reset,
		clock_rxLocal => clock_rx,
		rxLocal       => rx,
		data_inLocal  => data_in,
		credit_oLocal => credit_o,
		clock_txLocal => clock_tx,
		txLocal       => tx,
		data_outLocal => data_out,
		credit_iLocal => credit_i	
		);

	
	leitura: for router in 0 to NROT - 1 generate
	begin
	  
process(clock_rx(router), reset)
    file ARQ: TEXT open READ_MODE is string'("router") & integer'image(CONV_INTEGER(router)) & string'(".txt");
    variable line_arq: string(1 to 200);
    variable wordIndex: integer := 1;
    variable count : integer := -1;
    variable tempTAM : regflit;
    begin
        if reset = '1' then
            rx(router) <= '0';
            wordIndex := 0;
            count := -1;
        elsif clock_rx(router)'event and clock_rx(router) = '1' then
            if ((NOT endfile(ARQ)) and (wordIndex = 0)) then
                readFileLine(ARQ, line_arq);
                wordIndex := 1;
                count := -1;
            end if;

            --teste1 : assert router /= 0 report "R:" & integer'image(router) & " - " & integer'image(wordIndex) & " [ " & integer'image(count) & " , " & integer'image(CONV_INTEGER(data_in(router))) & " ]" severity note;
				
            if ((wordIndex > 0) and (credit_o(router)='1'))then
					
                if (line_arq'high <= wordIndex) or (count=0) then
                    wordIndex := 0;                    
                    rx(router) <= '0';
                    data_in(router) <= (others=>'Z');
                else
          		        tempTAM := CONV_VECTOR(line_arq, wordIndex) &
        					                  CONV_VECTOR(line_arq, wordIndex + 1) &
						                   CONV_VECTOR(line_arq, wordIndex + 2) &
						                   CONV_VECTOR(line_arq, wordIndex + 3);
					          data_in(router) <= tempTAM;
								   
                    rx(router) <= '1';
                    wordIndex := wordIndex + 5;
                    if wordIndex = 11 then
                        count := CONV_INTEGER(tempTAM);
                    elsif wordIndex > 11 then
                        count := count - 1;
                    end if;
 		            end if;
		        end if;
        end if;			
		end process;
		
	end generate;

?    Como está arquitetura possui a maioria de seus métodos implementados de forma genérica - envio
? de pacotes e um "hadware" para verificar se o roteador recebeu todos os pacotes, nos resta instanciar
? todos os módulos de autentificação aqui.
!
		process(package_received)
		variable count : integer;
		begin
		count := 0;
		for i in 0 to NROT-1 loop			
				if(package_received(i) = '1') then
					count:= count + 1;
				end if;
		end loop;
		
		if(count = NROT) then
			assert false report "end of simulation" severity failure;
		end if;
		end process;
	
end topNoC;
