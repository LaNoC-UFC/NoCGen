library IEEE;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use work.PhoenixPackage.all;

package TablePackage is

constant NREG : integer := 2;
constant MEMORY_SIZE : integer := NREG;

type memory is array (0 to MEMORY_SIZE-1) of reg26;
type tables is array (0 to NROT-1) of memory;

constant TAB: tables :=(
 -- Router 00
 -- 1 1
 -- x 0
(("10100000100000001000000001"), -- 0, LOCAL, NORTH
("10001000000010001000100100") -- 1, LOCAL, EAST
),
 -- Router 01
(("10001000000000000000001000"),
("11000000100000001000100001")
),
 -- Router 10
(("10000000000000000000000010"),
("10000000000010001000100100")
),
 -- Router 11
(("10010000100000001000001000"),
("11000000000000000000100010")
)
);
end TablePackage;

package body TablePackage is
end TablePackage;
