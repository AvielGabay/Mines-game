package mines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Mines {
	private int height, width;
	private boolean ShowAll = false;//show all the board as open cells
	private Place grid[][];

	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		grid = new Place[height][width];//create new grid
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				grid[i][j] = new Place(i, j);
		int cnt = 0;
		Random r = new Random();
		while (cnt < numMines && cnt < height * width) {//while there are still mines to add
			if (addMine(r.nextInt(height), r.nextInt(width)))//add random mine
				cnt++;
		}
	}

	
	//check for specific location(added for FX)
	public boolean isMine(int i, int j) {
		return grid[i][j].mine;
	}

	//check for specific location(added for FX)
	public boolean isFlag(int i, int j) {
		return grid[i][j].flag;
	}

	//check for specific location(added for FX)
	public boolean isOpen(int i, int j) {
		return grid[i][j].open;
	}
	
	//add mine to specific location
	public boolean addMine(int i, int j) {
		if (grid[i][j].mine)
			return false;
		grid[i][j].mine = true;
		return true;
	}

	//open recursively the cells 
	public boolean open(int i, int j) {
		if (grid[i][j].mine)
			return false;
		if (grid[i][j].open)
			return true;
		grid[i][j].open = true;
		grid[i][j].flag = false;
		if (grid[i][j].neigborsMines() == 0) {
			Collection<Place> neighbors = grid[i][j].neigbors();
			for (Place p : neighbors)
				p.open();
		}
		return true;
	}

//add or remove flag in specific location
	public void toggleFlag(int x, int y) {
		if (grid[x][y].open)
			return;
		grid[x][y].flag = !grid[x][y].flag;
	}

	//check the board for non open non mine cells
	public boolean isDone() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				if (!grid[i][j].mine && !grid[i][j].open)
					return false;
		}
		return true;
	}

	//set value to each cell
	public String get(int i, int j) {
		if (!grid[i][j].open && !ShowAll) {//if opened
			if (grid[i][j].flag)
				return "F";//if closed and flag
			else
				return ".";//if closed and not flag
		}
		if (grid[i][j].mine)//if opened and mine
			return "X";
		int neigborsMines = grid[i][j].neigborsMines();
		if (neigborsMines == 0)
			return " ";
		return "" + neigborsMines;
	}

	//set ShowAll boolean
	public void setShowAll(boolean showAll) {
		this.ShowAll = showAll;
	}

	//prints the board
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				str.append(get(i, j));
			str.append("\n");
		}
		return str.toString();
	}

	//check for specific location(added for FX) - middle click
	public boolean isCellDone(int i, int j) {
		return grid[i][j].neigborsMines() == grid[i][j].neigborsFlags();
	}

	
	//open all non flag neighbors(added for FX) - middle click
	public boolean openAllNeighbors(int i, int j) {
		Collection<Place> neighbors = grid[i][j].neigbors();
		for (Place p : neighbors)
			if (!p.flag && p.open() == false)
				return false;
		return true;
	}

	//class for specific location
	private class Place {
		private int x, y;
		public boolean mine = false, open = false, flag = false;
		private List<Place> n = null;//list of neighbors

		public Place(int x, int y) {
			this.x = x;
			this.y = y;
		}

		//open this location
		public boolean open() {
			return Mines.this.open(x, y);
		}

		//count the number of neighbors containing mines
		public int neigborsMines() {
			Collection<Place> neighbors = neigbors();
			int cnt = 0;
			for (Place p : neighbors)
				if (p.mine)
					cnt++;
			return cnt;
		}

		//return the neighbors of a location
		//calculated one time, after it will return the same list
		public Collection<Place> neigbors() {
			if (n != null)//if canceled return the previous 
				return n;
			n = new ArrayList<>();
			for (int i = -1; i < 2; i++)
				for (int j = -1; j < 2; j++) {
					if (i == 0 & j == 0)
						continue;
					if (i + x < 0 || i + x >= height)//if x not valid
						continue;
					if (j + y < 0 || j + y >= width)//if y not valid
						continue;
					n.add(grid[i + x][j + y]);
				}
			return n;
		}

		//count the number of neighbors containing flags(added for FX) - middle click
		public int neigborsFlags() {
			Collection<Place> neighbors = neigbors();
			int cnt = 0;
			for (Place p : neighbors)
				if (p.flag)
					cnt++;
			return cnt;
		}

	}

}