package prg.com;

public class Pipe
{
	public static final int STRAIGHT = 0;
	public static final int CURVED = 1;
	public static final int EAST = 0;
	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	public static Pipe newRand()
	{
		return new Pipe(Board.RNG.nextInt(2)%2, Board.RNG.nextInt(4)%4);
	}
	public static Pipe newStraight()
	{
		return new Pipe(0, Board.RNG.nextInt(4)%4);
	}
	public static Pipe newBend()
	{
		return new Pipe(1, Board.RNG.nextInt(4)%4);
	}
	public static Pipe newStraight(int orientation)
	{
		return new Pipe(0, orientation);
	}
	public static Pipe newBend(int orientation)
	{
		return new Pipe(1, orientation);
	}
	
	private int type;	//0 = straight, 1 = bend
	private int orientation;	//0 = east, 1 = north, 2 = west, 3 = south
	public Pipe(int type, int orientation)
	{
		this.type = type;
		this.orientation = orientation;
	}
	public int rotate()
	{
		orientation = (orientation + 1) % 4;
		return orientation;
	}
	public int getType()
	{
		return type;
	}
	public int getOrientation()
	{
		return orientation;
	}
	public boolean canRight()
	{
		if(orientation == EAST) return true;
		if(type == STRAIGHT && orientation == WEST) return true;
		if(type == CURVED && orientation == SOUTH) return true;
		return false;
	}
	public boolean canUp()
	{
		if(orientation == NORTH) return true;
		if(type == STRAIGHT && orientation == SOUTH) return true;
		if(type == CURVED && orientation == EAST) return true;
		return false;
	}
	public boolean canLeft()
	{
		if(orientation == WEST) return true;
		if(type == STRAIGHT && orientation == EAST) return true;
		if(type == CURVED && orientation == NORTH) return true;
		return false;
	}
	public boolean canDown()
	{
		if(orientation == SOUTH) return true;
		if(type == STRAIGHT && orientation == NORTH) return true;
		if(type == CURVED && orientation == WEST) return true;
		return false;
	}
}
