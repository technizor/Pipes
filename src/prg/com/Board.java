package prg.com;

import java.util.Random;

public class Board
{
	public static final Random RNG = new Random();
	private int height;
	private int width;
	private Pipe[][] board;
	private boolean[][] connected;
	private boolean solved;
	private static final boolean DEBUG = false;
	
	public Board(int width, int height)
	{
		this.setHeight(height);
		this.setWidth(width);
		setBoard(BoardGenerator.generateNewBoard(width, height));
		connected = new boolean[width][height];
		solved = false;
		update();
	}
	public boolean getConnected(int x, int y)
	{
		return connected[x][y];
	}
	public boolean[][] getConnected()
	{
		return connected;
	}
	public int getHeight()
	{
		return height;
	}
	private void setHeight(int height)
	{
		this.height = height;
	}
	public int getWidth()
	{
		return width;
	}
	private void setWidth(int width)
	{
		this.width = width;
	}
	public Pipe[][] getBoard()
	{
		return board;
	}
	private void setBoard(Pipe[][] board)
	{
		this.board = board;
	}
	public void rotate(int xPos, int yPos)
	{
		if(xPos >= 0 && xPos < width && yPos >= 0 && yPos < height) {
			board[xPos][yPos].rotate();
		}
		update();
	}
	private void update()
	{
		for(int i = 0; i < width; i++) {
			connected[i] = new boolean[height];
		}
		check(0, 0, 1);
		if(DEBUG)
			printConnections();
	}
	private void printConnections()
	{
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				System.out.print((connected[i][j] ? "1" : "0"));
			}
			System.out.print("\t");
			for(int i = 0; i < width; i++) {
				System.out.print((board[i][j].getOrientation() + "" + board[i][j].getType()));
				System.out.print("" + (board[i][j].canRight() ? "1" : "0") + "" +
						(board[i][j].canUp() ? "1" : "0") + "" +
						(board[i][j].canLeft() ? "1" : "0") + "" +
						(board[i][j].canDown() ? "1" : "0") + "-");
			}
			System.out.print("\n");
		}
		System.out.println("-------------------------------------------");
		if(solved) System.out.println("Solved!");
	}
	private void check(int x, int y, int lastDir)
	{
		if(connected[x][y]) return;
		if(board[x][y].canRight() && lastDir == Pipe.EAST) {
			connected[x][y] = true;
		} else if(board[x][y].canUp() && lastDir == Pipe.NORTH) {
			connected[x][y] = true;
		} else if(board[x][y].canLeft() && lastDir == Pipe.WEST) {
			connected[x][y] = true;
		} else if(board[x][y].canDown() && lastDir == Pipe.SOUTH) {
			connected[x][y] = true;
		}
		if(connected[x][y]) {
			if(x == width-1 && y == height-1 && board[x][y].canDown()) {
				solved = true;
				return;
			}
			if(board[x][y].getType() == Pipe.STRAIGHT) {
				int newDir = (2+lastDir)%4;
				if(newDir == Pipe.EAST && x < width-1) { 
					check(x+1, y, lastDir);
				} else if(newDir == Pipe.NORTH && y > 0) {
					check(x, y-1, lastDir);
				} else if(newDir == Pipe.WEST && x > 0) {
					check(x-1, y, lastDir);
				} else if(y < height-1) {
					check(x, y+1, lastDir);
				}
			} else {
				int orientation = board[x][y].getOrientation();
				int other = (orientation +1)%4;
				if(orientation == lastDir) {
					if(other == Pipe.EAST && x < width-1) { 
						check(x+1, y, (other+2)%4);
					} else if(other == Pipe.NORTH && y > 0) {
						check(x, y-1, (other+2)%4);
					} else if(other == Pipe.WEST && x > 0) {
						check(x-1, y, (other+2)%4);
					} else if(y < height-1) {
						check(x, y+1, (other+2)%4);
					}
				} else if(other == lastDir) {
					if(orientation == Pipe.EAST && x < width-1) { 
						check(x+1, y, (orientation+2)%4);
					} else if(orientation == Pipe.NORTH && y > 0) {
						check(x, y-1, (orientation+2)%4);
					} else if(orientation == Pipe.WEST && x > 0) {
						check(x-1, y, (orientation+2)%4);
					} else if(y < height-1) {
						check(x, y+1, (orientation+2)%4);
					}
				}
			}
		}
	}
}
