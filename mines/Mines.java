package mines;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mines {
	private class Place {
		// inner class which becomes a part of the board.
		private int i, j;
		// Neighbors = the number of mines which are neighbors of that place.
		private boolean hasMine, hasFlag = false, isOpen = false;

		public Place(boolean hasMine, int i, int j) {
			// constructor for the places in the board.
			this.i = i;
			this.j = j;
			this.hasMine = hasMine;
		}

		private List<Place> neighbours() {
			// arranges the neighbors of the Place into a list.
			List<Place> l = new LinkedList<>();
			if (i < 0 || i >= height || j < 0 || j >= width) {
				return null;
			}
			// case up:
			if (i > 0) {
				l.add(board[i - 1][j]);
			}
			// case down:
			if (i < height - 1) {
				l.add(board[i + 1][j]);
			}
			// case left:
			if (j > 0) {
				l.add(board[i][j - 1]);
			}
			// case right;
			if (j < width - 1) {
				l.add(board[i][j + 1]);
			}
			// case up-right:
			if (j != width - 1 && i != 0) {
				l.add(board[i - 1][j + 1]);
			}
			// case up-left:
			if (i != 0 && j != 0) {
				l.add(board[i - 1][j - 1]);
			}
			// case down-left:
			if (j != 0 && i != height - 1) {
				l.add(board[i + 1][j - 1]);
			}
			// case down-right:
			if (j != width - 1 && i != height - 1) {
				l.add(board[i + 1][j + 1]);
			}
			return l;
		}

		private int calcNeighbours() {
			// calculates the number of neighbours of the place.
			int counter = 0;
			for (Place p : neighbours()) {
				if (p.hasMine)
					counter++;
			}
			return counter;
		}

		@Override
		public String toString() {
			return String.format("place=[%d][%d],neighbors=%d,hasFlag=%b,hasMine=%b", i, j, calcNeighbours(), hasFlag,
					hasMine);
		}
	}

	private int height, width, numMines;
	private Place[][] board;
	private boolean showAll = false;

	public Mines(int height, int width, int numMines) {
		// constructor for the board for the game.
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new Place[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = new Place(false, i, j);
			}
		}
		if(numMines > height*width) {
			numMines = height*width;
		}
		// putting mines in random places on the board.
		Random r = new Random();
		for (int k = 0; k < numMines; k++) {
			int row = r.nextInt(height);
			int col = r.nextInt(width);
			while(board[row][col].hasMine) {
				row = r.nextInt(height);
				col = r.nextInt(width);
			}
			board[row][col] = new Place(true, row, col);
		}
	}

	public boolean addMine(int i, int j) {
		// adds a mine to the board.
		// this method is called only after the constructor.
		if (!board[i][j].hasMine) {
			board[i][j] = new Place(true, i, j);
			numMines++;
			return true;
		}
		return false;
	}

	public boolean open(int i, int j) {
		// signals that the user has opened that place;
		if (i < 0 || j < 0 || i >= height || j >= width) {
			// check bounds of board.
			return false;
		}
		if (board[i][j].hasMine) {
			return false;
		}
		if (board[i][j].isOpen) {
			return true;
		}
		board[i][j].isOpen = true;
		if (board[i][j].calcNeighbours() == 0) {
			// mark that the place is open.
			// continue recursively for all other 8 locations.
			for (Place p : board[i][j].neighbours()) {
				open(p.i, p.j);
			}
		}
		return true;
	}

	public void toggleFlag(int x, int y) {
		// puts a flag in the given place.
		if (board[x][y].hasFlag == true) {
			// removes the flag if there is already one there.
			board[x][y].hasFlag = false;
		} else
			board[x][y].hasFlag = true;
	}

	public boolean isDone() {
		// returns true if all the places which are not mines are open.
		int counter = 0;
		int safe = height * width - numMines; // all the safe places.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j].isOpen && !board[i][j].hasMine) {
					counter++;
				}
			}
		}
		return counter == safe;
	}

	public String get(int i, int j) {
		// returns a string representation of the place on the board.
		Place curr = board[i][j];
		int n = curr.calcNeighbours();
		if (!showAll) {
			if (!curr.isOpen) {
				if (curr.hasFlag)
					return "F";
				return ".";
			} else if (curr.hasMine)
				return "X";
			else if (n == 0)
				return " ";
			return String.format("%d", n);
		} else {
			if (curr.hasMine)
				return "X";
			else if (n == 0)
				return " ";
			return String.format("%d", n);
		}
	}

	public void setShowAll(boolean showAll) {
		// decided the return value of the field showAll.
		this.showAll = showAll;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s.append(get(i, j));
			}
			s.append("\n");
		}
		return s.toString();
	}
}