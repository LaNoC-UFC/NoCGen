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
	
	type state is (S0,S1,S2,S3);
	signal ES, PES: state;
	
begin

----------------------------------------------------
-- 	Atribuição do próximo estado de acordo com o 
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
-- 	Funcionamento/ Controle da máquina de estados

	process(ES, PES, tx)
		variable counter : integer := 0;
	begin
		case ES is
			-- reset
			when S0 =>
				counter := 0;
				PES <= S1;
			-- verifica o header
			when S1 =>
				if(data_out = x"FF" & address and tx='1') then
					PES <= S2; 
				else 
					PES <= S1;
				end if;
				
				if(counter = NROT-1) then 
					package_received <= '1';
				else
					package_received <= '0';
				end if;
			-- verifica o size
			when S2 =>
				if(data_out = x"0001" and tx='1') then
					PES <= S3; 
				else 
					PES <= S1;
				end if;	
			-- verifica o payload
			when S3 =>
				if (data_out = x"0ABC" and tx='1') then
					counter := counter+1;
				else 
					counter := counter;
				end if;
				PES <= S1;
		end case;
	end process;
	
end SendAuthenticate;

-----------------------------------------
-- TOP NOC
-----------------------------------------
library IEEE;
use IEEE.std_logic_1164.all;
use ieee.std_logic_arith.all;
use work.HermesPackage.all;

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

-------------------------------------------------------------------
--   Cada roteador envia um pacote para cada outro roteador, logo todos
-- recebem NROT-1 pacotes. O envio ocorre de forma sequêncial, ou seja,
-- primeiro o roteador 00 enviar todos seus pacotes,depois o roteador 10 
-- envia todos seus pacotes e assim por diante.
	
	process(clock_rx(N0000), reset)
		  variable count:integer;
		  variable counter:integer;
		  variable packet_part:integer;
		  variable source:integer;
		  variable target:integer;

		  variable var_tx, var_ty: regflit;
		begin
			if(reset = '1')then
				count:=0;
				counter:=0; -- primeiro payload, outros ++
				source:=0;
				target:=0;
				packet_part:=0;
				rx <= (others=>'0');
				data_in <= (others => (others=>'0'));
				do_nothing <= '0';
			elsif(clock_rx(N0000)'event and clock_rx(N0000)='0')then
				if(do_nothing = '0') then
					if(source/=target)then -- codição para não enviar para si mesmo
						if(count=0)then -- estou inserindo um header
						  rx <= (source=>'1', others=>'0');
						  var_tx := CONV_STD_LOGIC_VECTOR((target) rem (MAX_X+1),TAM_FLIT);
						  var_ty := CONV_STD_LOGIC_VECTOR((target) / (MAX_X+1),TAM_FLIT);
						  data_in(source)<= x"FF" & var_tx(3 downto 0) & var_ty(3 downto 0);

						  count := count+1;
						elsif(count=1 and credit_o(source)='1')then -- estou inserindo um size
						  data_in(source)<= CONV_STD_LOGIC_VECTOR(1,TAM_FLIT);
						  count := count+1;
						elsif(count=2 and credit_o(source)='1')then -- estou inserindo um payload (1 flit)
						  data_in(source)<= x"0ABC"; --CONV_STD_LOGIC_VECTOR(counter,TAM_FLIT);
						  counter := counter + 1;
						  count := count+1;
						elsif(count=3) then
						  if(credit_o(source)='1') then
						    count := 0;
						    rx <= (others=>'0');

						    if(counter=NROT*(NROT-1)) then -- counter = max_pacotes_à_enviar
						      do_nothing <= '1';
						    end if;
						  
						    if(target=NROT-1)then
						      target:=0;
						      source:=source + 1;
					    	    else
					       	      target := target +1;
						    end if;
						  end if;
						end if;
					else
				     target:=target+1;
					end if;
				else
				  data_in <= (others=>(others=>'0'));
				end if;
			end if;
		end process;

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
