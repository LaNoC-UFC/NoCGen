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

?    Este trecho será substituído por toda arquitetura do NOC.vhd. Esta contém:
?  1°) Declaração de sinais (signal) auxiliares,
?  2°) Instanciação dos roteadores com seus respectivos sinais
?  3°) Conexão dos roteadores. Cada roteador é interligado com seus próximos e 
?     sua entrada local recebe os valores injetados pelo topNoC. 
!
end NOC;
