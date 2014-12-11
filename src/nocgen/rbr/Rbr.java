package nocgen.rbr;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import nocgen.util.NumberFormatConversion;

public class Rbr 
{
	private int maxX, maxY;
	
	public Rbr(int maxX, int maxY)
	{
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/**
	 * Compute all possible output ports, that a package have, for each input 
	 * port it can come in.
	 * 
	 * @param src -> source router
	 * @param dst -> destiny router
	 * @return possible output port for each input port
	 */
	public List<RoutingPath> XYPaths(Router src, Router dst)
	{
		List<RoutingPath> paths = new ArrayList<RoutingPath>();
		String op;
		List<String> ipPorts = new ArrayList<String>();
		
		// check for possible input ports
		if(src.x() > 0)
			ipPorts.add("W");
		if(src.x() < this.maxX)
			ipPorts.add("E");
		if(src.y() > 0)
			ipPorts.add("S");
		if(src.y() < this.maxY)
			ipPorts.add("N");
		
		ipPorts.add("I");
		
		for (String ip : ipPorts) // loop in which ip receives every element of ipPorts (foreach)
		{
			if(src.x() == dst.x())
			{
				if(src.y() < dst.y())
					op = "N";
				else
					op = "S";
			}
			else if(src.x() < dst.x())
				op = "E";
			else
				op = "W";
			
			if(ip != op)
				paths.add(new RoutingPath(ip, op, dst));
		}
		
		return paths;
	}
	
	/**
	 * 
	 * @param paths
	 * @return a List<RoutingPath>
	 */
	public List<RoutingPath> pack(List<RoutingPath> paths)
	{
		List<RoutingPath> partPacked = new ArrayList<RoutingPath>();
		List<RoutingPath> packed = new ArrayList<RoutingPath>();
		
		List<String> ipPorts = new ArrayList<String>();
		ipPorts.add("W");
		ipPorts.add("E");
		ipPorts.add("S");
		ipPorts.add("N");
		ipPorts.add("I");
		
		if (paths.isEmpty())
			return packed;
		
		Router dst = paths.get(0).getDstRouter();
		
	    // Pack all the output port if they have the same input port
		for(String ip : ipPorts)
		{
			List<String> opPack = new ArrayList<String>();
			
			for(RoutingPath path : paths)
			{
				if(path.ipContains(ip))
					opPack.addAll(path.getOutputPorts());
			}
					
			if(!opPack.isEmpty())
			{
				List<String> inputPort = new ArrayList<String>();
				inputPort.add(ip);
				RoutingPath rp = new RoutingPath(inputPort,opPack,dst);
				
				partPacked.add(rp);
				
			}
		}
		
		HashMap<Integer, List<Integer>> ipHash= new HashMap<Integer, List<Integer>>();
		
		for(int i =0; i < partPacked.size(); i++)
		{
		
			boolean continuar = false;
			//   Run through the HashMap(key) searching in every List<Integer> 
			// if the element already exists 
			for(int x=0; x < ipHash.size(); x++)
			{
				if(ipHash.get(x).contains(i))
					continuar = true;
			}
			
			if(continuar)
					continue;
			
			ipHash.put(i, new ArrayList<Integer>());
			
			ipHash.get(i).add(i);
			
			for(int j = i+1; j < partPacked.size(); j++)
			{
				continuar = false;
				for(int x=0; x < ipHash.size(); x++)
				{
					if(ipHash.get(x).contains(j))
						continuar = true;
				}
				if(continuar)
					continue;
				
				List<String> oPi = partPacked.get(i).getOutputPorts(); // output port from partPacked object with key = i
				List<String> oPj = partPacked.get(j).getOutputPorts(); // output port from partPacked object with key = j
				if(oPi.equals(oPj))
				{		
					ipHash.get(i).add(j);
				}
			}
		}

		for(int pos : ipHash.keySet())
		{
			List<String> ipPack = new ArrayList<String>();
				
			
			for(int value : ipHash.get(pos))
			{
				List<String> auxIp = partPacked.get(value).getInputPorts();
				ipPack.addAll(auxIp);
			}
			
			List<String> op = partPacked.get(pos).getOutputPorts(); // output port from partPacked object with key = pos
			Router dstRouter = partPacked.get(pos).getDstRouter(); // destiny router from partPacked object with key = pos
			RoutingPath rp = new RoutingPath(ipPack, op, dstRouter); // create auxialary RoutingPath object
			
			packed.add(rp); // add a Routing Path to the list
		}
		
		return packed;	
	}
	

	public HashMap<Router, List<String>> computation()
	{
		List<Router> orderedKeySet = new ArrayList<Router>();
		HashMap<Router, List<RoutingPath>> swPaths = new HashMap<Router, List<RoutingPath>>();
		HashMap<Router, List<String>> str = new HashMap<Router, List<String>>();
		
		for(int i=0; i < maxX; i++)
		{
			for(int j=0; j < maxY; j++)
			{
				List<RoutingPath> packed = new ArrayList<RoutingPath>();
				Router source = new Router(i,j);	
				
				for(int k=0; k < maxX; k++)
				{
					for(int l=0; l < maxY; l++)
					{
						Router dest = new Router(k,l);
						
						if(!source.equals(dest))
						{
							List<RoutingPath> paths = new ArrayList<RoutingPath>();
							paths = XYPaths(source,dest);
							List<RoutingPath> pathsPacked = pack(paths);							
							packed.addAll(pathsPacked);
						}
					}
				}
				swPaths.put(source, packed);
				orderedKeySet.add(source);
			}
		}
		
		for(Router src : orderedKeySet)
		{
			HashMap<Integer, List<Integer>> grouped = new HashMap<Integer, List<Integer>>();
			List<RoutingPath> allPaths = new ArrayList<RoutingPath>();
			allPaths = swPaths.get(src);

			for(int i=0; i < allPaths.size(); i++)
			{
				boolean continuar = false;
				for(int x : grouped.keySet())
				{
					if(grouped.get(x).contains(i))
						continuar = true;
				}
				if(continuar)
					continue;
				
				grouped.put(i,new ArrayList<Integer>());
				grouped.get(i).add(i);
				
				RoutingPath onePath = allPaths.get(i);
				
				for(int j=i+1; j < allPaths.size(); j++)
				{
					continuar = false;
					for(int x : grouped.keySet())
					{
						if(grouped.get(x).contains(j))
							continuar = true;
					}
					
					if(continuar)
						continue;

					RoutingPath otherPath = allPaths.get(j);
					
					// onePath auxiliary variables
					List<String> onePathIp = onePath.getInputPorts();
					List<String> onePathOp = onePath.getOutputPorts();
					
					// otherPath auxiliary variables
					List<String> otherPathIp = otherPath.getInputPorts();
					List<String> otherPathOp = otherPath.getOutputPorts();
					
					if(onePathIp.equals(otherPathIp) && onePathOp.equals(otherPathOp))
						grouped.get(i).add(j);
				}
			}

			str.put(src, new ArrayList<String>());
			
			for(int pos : grouped.keySet())
			{
				int last = grouped.get(pos).size()-1;
				int value = grouped.get(pos).get(0);
				
				String element = "";
				String op, x, y = "";
				int xHigh, xLow, yHigh, yLow;
				
				if(grouped.get(pos).size() == 1) // unique router region
				{
					// convert the destiny router address to binary
					x = allPaths.get(value).getDstRouter().debug().toString();
					xHigh = x.charAt(0) - 48;
					xLow = x.charAt(1) - 48;
					x = NumberFormatConversion.convBin(xHigh, xLow);
					element = x + x;
				}
				else
				{
					// convert the destiny router address to binary
					x = allPaths.get(value).getDstRouter().debug().toString();
					xHigh = x.charAt(0) - 48;
					xLow = x.charAt(1) - 48;
					x = NumberFormatConversion.convBin(xHigh, xLow);
					
					value = grouped.get(pos).get(last);
					
					y = allPaths.get(value).getDstRouter().debug().toString();
					yHigh = y.charAt(0) - 48;
					yLow = y.charAt(1) - 48;
					y = NumberFormatConversion.convBin(yHigh, yLow);
					element = x + y; 
				}
				// convert the direction to binary
				op = allPaths.get(value).getOutputPorts().toString();
				element = element + NumberFormatConversion.convDirection(op);
				str.get(src).add(element);
			}
		}
		
		return str;
	}
}