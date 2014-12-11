package nocgen.rbr;

public class Router
{

	private int posX, posY;
	/**
	 * Class constructor without parameters
	 */
	public Router()
	{
		posX = 0;
		posY = 0;
	}
	
	/**
	 * Class constructor with parameters
	 * 
	 * @param X -> X position
	 * @param Y -> Y position
	 */
	public Router(int X, int Y)
	{
		setPosX(X);
		setPosY(Y);
	}
	
	public Router(Router r)
	{
		setPosX(r.x());
		setPosY(r.y());
	}
	
	// Set's and Get's
	public int x()
	{
		return posX;
	}
	
	public void setPosX(int X)
	{
		posX = X;
	}
	
	public int y()
	{
		return posY;
	}
	
	public void setPosY(int Y)
	{
		posY = Y;
	}
	/**
	 * 
	 * @param r : router to be compared with.
	 * @return true if routers are iqual else false.
	 */
	public boolean equals(Router r)
	{
		if(posX == r.x() && posY == r.y())
			return true;
		return false;
	}
	
	public String debug()
	{
		return (posX + "" + posY);
	}
}
