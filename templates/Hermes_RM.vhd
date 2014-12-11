---------------------------------------------------------
-- Routing Mechanism
---------------------------------------------------------
library IEEE;
use ieee.std_logic_1164.all;
use work.HermesPackage.all;

entity routingMechanism is
	generic(address : regmetadeflit := (others=>'0'));
	port(
			clock :   in  std_logic;
			reset :   in  std_logic;
			buffCtrl: in buffControl;
			ctrl :	in std_logic;
			operacao: in regflit;
			ceT: in std_logic;
			oe :   in  std_logic;
			dest : in reg8;
			outputPort : out regNPort;
			find : out RouterControl
		);
end routingMechanism;

architecture behavior of routingMechanism is

	-- sinais da máquina de estado
	type state is (S0,S1,S2,S3,S4);
	signal ES, PES : state;
	
	-- sinais da Tabela
	signal ce: std_logic := '0';
	signal data : std_logic_vector(4 downto 0) := (others=>'0');

begin
------------------------------------------------------------------------
-- Table Generate
------------------------------------------------------------------------

? Aqui será incluso a instação das tabelas através do método generate
?
!
	
	process(reset,clock)
	begin
		if reset='1' then
			ES<=S0;
		elsif clock'event and clock='0' then
			ES<=PES;
		end if; 
	end process;
	
	------------------------------------------------------------------------------------------------------
	-- PARTE COMBINACIONAL PARA DEFINIR O PRÓXIMO ESTADO DA MÁQUINA.
	--
	-- S0 -> Este estado espera oe = '1' (operation enabled), indicando que há um pacote que que deve
	--       ser roteado.
	-- S1 -> Este estado ocorre a leitura na memória - tabela, a fim de obter as 
	--       definições de uma região.
	-- S2 -> Este estado verifica se o roteador destino (destRouter) pertence aquela
	--       região. Caso ele pertença o sinal de RM é ativado e a máquina de estados
	--       avança para o próximo estado, caso contrário retorna para o estado S1 e
	--       busca por uma nova região.
	-- S3 -> Neste estado o switch control é avisado (find="01") que foi descoberto por 
	--       qual porta este pacote deve sair. Este estado também zera count, valor que 
	--			aponta qual o próximo endereço deve ser lido na memória.
	-- S4 -> Aguarda oe = '0' e retorna para o estado S0.
	
	process(ES, oe)
	begin
		case ES is
			when S0 => if oe = '1' then PES <= S1; else PES <= S0; end if;
			when S1 => PES <= S2;
			when S2 => PES <= S3;
			when S3 => if oe = '0' then PES <= S0; else PES <= S3; end if;
			when others => PES <= S0;
		end case;
	end process;
	
	------------------------------------------------------------------------------------------------------
	-- executa as ações correspondente ao estado atual da máquina de estados
	------------------------------------------------------------------------------------------------------
	process(clock)
	begin
		if(clock'event and clock = '1') then
			case ES is
				-- Aguarda oe='1'
				when S0 =>
					find <= invalidRegion;
					
				-- Leitura da tabela
				when S1 =>
					ce <= '1';
					
				-- Informa que achou a porta de saída para o pacote
				when S2 =>
					find <= validRegion;
				-- Aguarda oe='0'
				when S3 =>
					ce <= '0';
					find <= invalidRegion;
				when others =>
					find <= portError;
			end case;
		end if;
	end process;
	outputPort <= data;
end behavior;	
