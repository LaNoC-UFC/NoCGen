library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use work.PhoenixPackage.all;
use work.HammingPack16.all;
use ieee.std_logic_arith.CONV_STD_LOGIC_VECTOR;

entity NOC is
port(
	clock         : in  regNrot;
	reset         : in  std_logic;
	clock_rxLocal : in  regNrot;
	rxLocal       : in  regNrot;
	data_inLocal_flit  : in  arrayNrot_regflit;
	credit_oLocal : out regNrot;
	clock_txLocal : out regNrot;
	txLocal       : out regNrot;
	data_outLocal_flit : out arrayNrot_regflit;
	credit_iLocal : in  regNrot);
end NOC;

architecture NOC of NOC is

	-- novos sinais--
	signal data_inLocal: arrayNrot_regphit;
	signal data_outLocal: arrayNrot_regphit;
	--

	signal clock_rxN0000, clock_rxN0100 : regNport;
	signal rxN0000, rxN0100 : regNport;
	signal data_inN0000, data_inN0100 : arrayNport_regphit;
	signal credit_oN0000, credit_oN0100 : regNport;
	signal clock_txN0000, clock_txN0100 : regNport;
	signal txN0000, txN0100 : regNport;
	signal data_outN0000, data_outN0100 : arrayNport_regphit;
	signal credit_iN0000, credit_iN0100 : regNport;
	signal testLink_iN0000, testLink_iN0100 : regNport;
	signal testLink_oN0000, testLink_oN0100 : regNport;
	signal retransmission_iN0000,retransmission_iN0100 : regNPort;
	signal retransmission_oN0000,retransmission_oN0100 : regNPort;

	signal clock_rxN0001, clock_rxN0101 : regNport;
	signal rxN0001, rxN0101 : regNport;
	signal data_inN0001, data_inN0101 : arrayNport_regphit;
	signal credit_oN0001, credit_oN0101 : regNport;
	signal clock_txN0001, clock_txN0101 : regNport;
	signal txN0001, txN0101 : regNport;
	signal data_outN0001, data_outN0101 : arrayNport_regphit;
	signal credit_iN0001, credit_iN0101 : regNport;
	signal testLink_iN0001, testLink_iN0101 : regNport;
	signal testLink_oN0001, testLink_oN0101 : regNport;
	signal retransmission_iN0001,retransmission_iN0101 : regNPort;
	signal retransmission_oN0001,retransmission_oN0101 : regNPort;

begin
	
	fillLocalFlits: for i in 0 to NROT-1 generate
	begin
		data_inLocal(i) <= data_inLocal_flit(i) & CONV_STD_LOGIC_VECTOR(0,TAM_HAMM);
		data_outLocal_flit(i) <= data_outLocal(i)(TAM_PHIT-1 downto TAM_HAMM);
	end generate;

	Router0000 : Entity work.RouterCC
	generic map( address => ADDRESSN0000 )
	port map(
		clock    => clock(N0000),
		reset    => reset,
		clock_rx => clock_rxN0000,
		rx       => rxN0000,
		data_in  => data_inN0000,
		credit_o => credit_oN0000,
		clock_tx => clock_txN0000,
		tx       => txN0000,
		data_out => data_outN0000,
		credit_i => credit_iN0000,
		testLink_i => testLink_iN0000,
		testLink_o => testLink_oN0000,
		retransmission_i => retransmission_iN0000,
		retransmission_o => retransmission_oN0000);

	Router0100 : Entity work.RouterCC
	generic map( address => ADDRESSN0100 )
	port map(
		clock    => clock(N0100),
		reset    => reset,
		clock_rx => clock_rxN0100,
		rx       => rxN0100,
		data_in  => data_inN0100,
		credit_o => credit_oN0100,
		clock_tx => clock_txN0100,
		tx       => txN0100,
		data_out => data_outN0100,
		credit_i => credit_iN0100,
		testLink_i => testLink_iN0100,
		testLink_o => testLink_oN0100,
		retransmission_i => retransmission_iN0100,
		retransmission_o => retransmission_oN0100);

	Router0001 : Entity work.RouterCC
	generic map( address => ADDRESSN0001 )
	port map(
		clock    => clock(N0001),
		reset    => reset,
		clock_rx => clock_rxN0001,
		rx       => rxN0001,
		data_in  => data_inN0001,
		credit_o => credit_oN0001,
		clock_tx => clock_txN0001,
		tx       => txN0001,
		data_out => data_outN0001,
		credit_i => credit_iN0001,
		testLink_i => testLink_iN0001,
		testLink_o => testLink_oN0001,
		retransmission_i => retransmission_iN0001,
		retransmission_o => retransmission_oN0001);

	Router0101 : Entity work.RouterCC
	generic map( address => ADDRESSN0101 )
	port map(
		clock    => clock(N0101),
		reset    => reset,
		clock_rx => clock_rxN0101,
		rx       => rxN0101,
		data_in  => data_inN0101,
		credit_o => credit_oN0101,
		clock_tx => clock_txN0101,
		tx       => txN0101,
		data_out => data_outN0101,
		credit_i => credit_iN0101,
		testLink_i => testLink_iN0101,
		testLink_o => testLink_oN0101,
		retransmission_i => retransmission_iN0101,
		retransmission_o => retransmission_oN0101);


	-- ROUTER 0000
	-- EAST port
	clock_rxN0000(0)<=clock_txN0100(1);
	rxN0000(0)<=txN0100(1);
	data_inN0000(0)<=data_outN0100(1);
	credit_iN0000(0)<=credit_oN0100(1);
   	testLink_iN0000(0)<=testLink_oN0100(1);
	retransmission_iN0000(0)<=retransmission_oN0100(1);
	-- WEST port
	clock_rxN0000(1)<='0';
	rxN0000(1)<='0';
	data_inN0000(1)<=(others=>'0');
	credit_iN0000(1)<='0';
	testLink_iN0000(1)<='0';
	retransmission_iN0000(1)<='0';
	-- NORTH port
	clock_rxN0000(2)<=clock_txN0001(3);
	rxN0000(2)<=txN0001(3);
	data_inN0000(2)<=data_outN0001(3);
	credit_iN0000(2)<=credit_oN0001(3);
	testLink_iN0000(2)<=testLink_oN0001(3);
	retransmission_iN0000(2)<=retransmission_oN0001(3);
	-- SOUTH port
	clock_rxN0000(3)<='0';
	rxN0000(3)<='0';
	data_inN0000(3)<=(others=>'0');
	credit_iN0000(3)<='0';
	testLink_iN0000(3)<='0';
	retransmission_iN0000(3)<='0';
	-- LOCAL port
	clock_rxN0000(4)<=clock_rxLocal(N0000);
	rxN0000(4)<=rxLocal(N0000);
	data_inN0000(4)<=data_inLocal(N0000);
	credit_iN0000(4)<=credit_iLocal(N0000);
	testLink_iN0000(4)<='0';
	clock_txLocal(N0000)<=clock_txN0000(4);
	txLocal(N0000)<=txN0000(4);
	data_outLocal(N0000)<=data_outN0000(4);
	credit_oLocal(N0000)<=credit_oN0000(4);
	retransmission_iN0000(4)<='0';

	-- ROUTER 0100
	-- EAST port
	clock_rxN0100(0)<='0';
	rxN0100(0)<='0';
	data_inN0100(0)<=(others=>'0');
	credit_iN0100(0)<='0';
   	testLink_iN0100(0)<='0';
	retransmission_iN0100(0)<='0';
	-- WEST port
	clock_rxN0100(1)<=clock_txN0000(0);
	rxN0100(1)<=txN0000(0);
	data_inN0100(1)<=data_outN0000(0);
	credit_iN0100(1)<=credit_oN0000(0);
	testLink_iN0100(1)<=testLink_oN0000(0);
	retransmission_iN0100(1)<=retransmission_oN0000(0);
	-- NORTH port
	clock_rxN0100(2)<=clock_txN0101(3);
	rxN0100(2)<=txN0101(3);
	data_inN0100(2)<=data_outN0101(3);
	credit_iN0100(2)<=credit_oN0101(3);
	testLink_iN0100(2)<=testLink_oN0101(3);
	retransmission_iN0100(2)<=retransmission_oN0101(3);
	-- SOUTH port
	clock_rxN0100(3)<='0';
	rxN0100(3)<='0';
	data_inN0100(3)<=(others=>'0');
	credit_iN0100(3)<='0';
	testLink_iN0100(3)<='0';
	retransmission_iN0100(3)<='0';
	-- LOCAL port
	clock_rxN0100(4)<=clock_rxLocal(N0100);
	rxN0100(4)<=rxLocal(N0100);
	data_inN0100(4)<=data_inLocal(N0100);
	credit_iN0100(4)<=credit_iLocal(N0100);
	testLink_iN0100(4)<='0';
	clock_txLocal(N0100)<=clock_txN0100(4);
	txLocal(N0100)<=txN0100(4);
	data_outLocal(N0100)<=data_outN0100(4);
	credit_oLocal(N0100)<=credit_oN0100(4);
	retransmission_iN0100(4)<='0';

	-- ROUTER 0001
	-- EAST port
	clock_rxN0001(0)<=clock_txN0101(1);
	rxN0001(0)<=txN0101(1);
	data_inN0001(0)<=data_outN0101(1);
	credit_iN0001(0)<=credit_oN0101(1);
   	testLink_iN0001(0)<=testLink_oN0101(1);
	retransmission_iN0001(0)<=retransmission_oN0101(1);
	-- WEST port
	clock_rxN0001(1)<='0';
	rxN0001(1)<='0';
	data_inN0001(1)<=(others=>'0');
	credit_iN0001(1)<='0';
	testLink_iN0001(1)<='0';
	retransmission_iN0001(1)<='0';
	-- NORTH port
	clock_rxN0001(2)<='0';
	rxN0001(2)<='0';
	data_inN0001(2)<=(others=>'0');
	credit_iN0001(2)<='0';
	testLink_iN0001(2)<='0';
	retransmission_iN0001(2)<='0';
	-- SOUTH port
	clock_rxN0001(3)<=clock_txN0000(2);
	rxN0001(3)<=txN0000(2);
	data_inN0001(3)<=data_outN0000(2);
	credit_iN0001(3)<=credit_oN0000(2);
	testLink_iN0001(3)<=testLink_oN0000(2);
	retransmission_iN0001(3)<=retransmission_oN0000(2);
	-- LOCAL port
	clock_rxN0001(4)<=clock_rxLocal(N0001);
	rxN0001(4)<=rxLocal(N0001);
	data_inN0001(4)<=data_inLocal(N0001);
	credit_iN0001(4)<=credit_iLocal(N0001);
	testLink_iN0001(4)<='0';
	clock_txLocal(N0001)<=clock_txN0001(4);
	txLocal(N0001)<=txN0001(4);
	data_outLocal(N0001)<=data_outN0001(4);
	credit_oLocal(N0001)<=credit_oN0001(4);
	retransmission_iN0001(4)<='0';

	-- ROUTER 0101
	-- EAST port
	clock_rxN0101(0)<='0';
	rxN0101(0)<='0';
	data_inN0101(0)<=(others=>'0');
	credit_iN0101(0)<='0';
   	testLink_iN0101(0)<='0';
	retransmission_iN0101(0)<='0';
	-- WEST port
	clock_rxN0101(1)<=clock_txN0001(0);
	rxN0101(1)<=txN0001(0);
	data_inN0101(1)<=data_outN0001(0);
	credit_iN0101(1)<=credit_oN0001(0);
	testLink_iN0101(1)<=testLink_oN0001(0);
	retransmission_iN0101(1)<=retransmission_oN0001(0);
	-- NORTH port
	clock_rxN0101(2)<='0';
	rxN0101(2)<='0';
	data_inN0101(2)<=(others=>'0');
	credit_iN0101(2)<='0';
	testLink_iN0101(2)<='0';
	retransmission_iN0101(2)<='0';
	-- SOUTH port
	clock_rxN0101(3)<=clock_txN0100(2);
	rxN0101(3)<=txN0100(2);
	data_inN0101(3)<=data_outN0100(2);
	credit_iN0101(3)<=credit_oN0100(2);
	testLink_iN0101(3)<=testLink_oN0100(2);
	retransmission_iN0101(3)<=retransmission_oN0100(2);
	-- LOCAL port
	clock_rxN0101(4)<=clock_rxLocal(N0101);
	rxN0101(4)<=rxLocal(N0101);
	data_inN0101(4)<=data_inLocal(N0101);
	credit_iN0101(4)<=credit_iLocal(N0101);
	testLink_iN0101(4)<='0';
	clock_txLocal(N0101)<=clock_txN0101(4);
	txLocal(N0101)<=txN0101(4);
	data_outLocal(N0101)<=data_outN0101(4);
	credit_oLocal(N0101)<=credit_oN0101(4);
	retransmission_iN0101(4)<='0';


end NOC;