package prg.com;

public class BoardGenerator
{
	private static final boolean SHOWPATH = false;
	private static final boolean PRINTMAZE = false;
	private static int[][] grid;
	private static Pipe[][] pipe;
	private static int gridHeight;
	private static int gridWidth;
	public static Pipe[][] generateNewBoard(int width, int height)
	{
		gridHeight = height;
		gridWidth = width;
		grid = new int[width][height];
		pipe = new Pipe[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				grid[i][j] = -1;
			}
		}
		path(0, 0, 0);
		if(PRINTMAZE) printBoard(grid);
		backPath();
		return pipe;
	}
	private static void backPath()
	{	//0 = right, 1 = up, 2 = left, 3 = down
		for(int i = 0, x = 0, y = 0, prevDir = 1, nextDir = -1; 
				i <= grid[gridWidth-1][gridHeight-1]; 
				i++, prevDir = ((nextDir+2)%4), nextDir = -1) {
			if(i < grid[gridWidth-1][gridHeight-1]) {
				if(x < gridWidth-1 && nextDir == -1) {
					if(grid[x+1][y] == i+1) {
						nextDir = 0;
					}
				}
				if(y > 0) {
					if(grid[x][y-1] == i+1 && nextDir == -1) {
						nextDir = 1;
					}
				}
				if(x > 0) {
					if(grid[x-1][y] == i+1 && nextDir == -1) {
						nextDir = 2;
					}
				}
				if(y < gridHeight-1) {
					if(grid[x][y+1] == i+1 && nextDir == -1) {
						nextDir = 3;
					}
				}
			} else {
				nextDir = 3;
			}
			if(SHOWPATH) {
				if((prevDir + nextDir) % 2 == 0) {	//Straight
					pipe[x][y] = Pipe.newStraight(prevDir);
				} else {
					if(nextDir == ((prevDir + 1)%4)) {
						pipe[x][y] = Pipe.newBend(prevDir);
					} else {
						pipe[x][y] = Pipe.newBend(nextDir);
					}
				}
			} else {
				if((prevDir + nextDir) % 2 == 0) {	//Straight
					pipe[x][y] = Pipe.newStraight();
				} else {
					pipe[x][y] = Pipe.newBend();
				}
			}
			if(nextDir == 0) {
				x++;
			} else if(nextDir == 1) {
				y--;
			} else if(nextDir == 2) {
				x--;
			} else if(nextDir == 3) {
				y++;
			}
		}
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				if(pipe[x][y] == null) {
					pipe[x][y] = Pipe.newRand();
				}
			}
		}
	}
	private static void path(int x, int y, int num)
	{
		grid[x][y] = num;
		if(x == gridWidth -1 && y == gridHeight -1) {
			return;
		}
		boolean[] canDirection = new boolean[4];
		int numDirection = 0;
		if(x < gridWidth -1) {
			if(grid[x+1][y] == -1) {
				canDirection[0] = true;
				numDirection++;
			}
		}
		if(y > 0) {
			if(grid[x][y-1] == -1) {
				canDirection[1] = true;
				numDirection++;
			}
		}
		if(x > 0) {
			if(grid[x-1][y] == -1) {
				canDirection[2] = true;
				numDirection++;
			}
		}
		if(y < gridHeight -1) {
			if(grid[x][y+1] == -1) {
				canDirection[3] = true;
				numDirection++;
			}
		}
		int[] directionList = new int[numDirection];
		for(int iter = 0, iter2 = 0; iter < 4 && iter2 < numDirection; iter++) {
			if(canDirection[iter]) {
				directionList[iter2] = iter;
				iter2++;
			}
		}
		if(numDirection == 0) {
			grid[x][y] = -2;
			return;
		} else {
			switch(directionList[Board.RNG.nextInt(numDirection)%numDirection]) {
			case 0:
				path(x+1, y, num+1);
				if(grid[x+1][y] == -2) {
					path(x, y, num);
				}
				break;
			case 1:
				path(x, y-1, num+1);
				if(grid[x][y-1] == -2) {
					path(x, y, num);
				}
				break;
			case 2:
				path(x-1, y, num+1);
				if(grid[x-1][y] == -2) {
					path(x, y, num);
				}
				break;
			case 3:
				path(x, y+1, num+1);
				if(grid[x][y+1] == -2) {
					path(x, y, num);
				}
				break;
			}
		}
	}
	private static void printBoard(int[][] grid)
	{
		System.out.println("############");
		for(int i = 0; i < grid.length; i++) {
			String str = "#";
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[j][i] > -1) {
					char ch = (char) (grid[j][i]+65);
					str += ch;
				}
				else str += " ";
			}
			System.out.println(str + "#");
		}
		System.out.println("############");
	}
}
