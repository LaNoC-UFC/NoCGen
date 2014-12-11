package nocgen.rbr;
import java.util.List;
import java.util.ArrayList;

public class RoutingPath 
{
	private List<String> ip, op;
	private Router destiny;
	
	/**
	 * 
	 * @param ip -> input port String array
	 * @param op -> output port String array
	 * @param r  -> destiny router
	 */
	public RoutingPath(List<String> ip, List<String> op, Router r)
	{
		this.ip = new ArrayList<String>(ip);
		this.op = new ArrayList<String>(op);
		this.destiny = new Router(r);
	}
	
	/**
	 * 
	 * @param ip -> input port string
	 * @param op -> input port string
	 * @param r  -> destiny router
	 */
	public RoutingPath(String ip, String op, Router r)
	{
		this.ip = new ArrayList<String>();
		this.op = new ArrayList<String>();
		this.destiny = new Router(r);
		
		this.ip.add(ip);
		this.op.add(op);
	}
	
	/**
	 * 
	 * @param ip : input port
	 * @return true, if sent ip is on the IpList
	 */
	public boolean ipContains(String ip)
	{
		return this.ip.contains(ip);
	}
	/**
	 * 
	 * @param op : output port
	 * @return true, if sent op is on the OpList
	 */
	public boolean opContains(String op)
	{
		return this.op.contains(op);
	}
	
	// Gets
	public List<String> getInputPorts()
	{
		return ip;
	}
	
	public List<String> getOutputPorts()
	{
		return op;
	}
	
	public Router getDstRouter()
	{
		return destiny;
	}
	
	public String debug()
	{
		return ("IP: " + ip + " OP: " + op + " Dst: " + destiny.debug());	
	}
}
