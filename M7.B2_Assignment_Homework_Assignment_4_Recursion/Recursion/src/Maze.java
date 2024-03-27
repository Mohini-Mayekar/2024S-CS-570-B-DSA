import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * 
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

	/** The maze */
	private TwoDimGrid maze;

	public Maze(TwoDimGrid m) {
		maze = m;
	}

	int[][] dirs = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

	/** Wrapper method. */
	public boolean findMazePath() {
		return findMazePath(0, 0); // (0, 0) is the start point.
	}

	/**
	 * Attempts to find a path through point (x, y).
	 * 
	 * @pre Possible path cells are in BACKGROUND color; barrier cells are in
	 *      ABNORMAL color.
	 * @post If a path is found, all cells on it are set to the PATH color; all
	 *       cells that were visited but are not on the path are in the TEMPORARY
	 *       color.
	 * @param x The x-coordinate of current point
	 * @param y The y-coordinate of current point
	 * @return If a path through (x, y) is found, true; otherwise, false
	 */

	public boolean findMazePath(int x, int y) {
		// COMPLETE HERE FOR PROBLEM 1
		int rows = maze.getNCols();
		int cols = maze.getNRows();
//		System.out.println("rows : " + rows);
//		System.out.println("cols : " + cols);
//		System.out.println("x : " + x);
//		System.out.println("y : " + y);
		
		// base
		if (x < 0 || x >= rows || y < 0 || y >= cols)
			return false;
		if (maze.getColor(x, y) == GridColors.BACKGROUND || maze.getColor(x, y) == GridColors.TEMPORARY)
			return false;
		if (x == rows - 1 && y == cols - 1 && maze.getColor(x, y) == GridColors.NON_BACKGROUND) {
			maze.recolor(x, y, GridColors.PATH);
			return true;
		}		

		// logic
		Color curr_color = maze.getColor(x, y);
		maze.recolor(x, y, GridColors.TEMPORARY);

		if (findMazePath(x + 1, y) || // Down
				findMazePath(x - 1, y) || // Up
				findMazePath(x, y + 1) || // Right
				findMazePath(x, y - 1) //Left
		) {
			maze.recolor(x, y, GridColors.PATH);
			return true;
		} else {
			maze.recolor(x, y, curr_color);
			return false;
		}

	}
	
	// ADD METHOD FOR PROBLEM 2 HERE
	public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
		ArrayList<ArrayList<PairInt>> result = new ArrayList<ArrayList<PairInt>>();

		Stack<PairInt> trace = new Stack<>();

		findMazePathStackBased(0, 0, trace, result);
		if (result.size() == 0) {
			result.add(new ArrayList<>());
		}

		return result;
	}

	private void findMazePathStackBased(int x, int y, Stack<PairInt> trace, ArrayList<ArrayList<PairInt>> result) {
		int rows = maze.getNCols();
		int cols = maze.getNRows();
		// base case
		if (x == rows - 1 && y == cols - 1) {
			trace.push(new PairInt(x, y));
			ArrayList<PairInt> temp = new ArrayList<PairInt>();
			temp.addAll(trace);
			result.add(temp);
			trace.pop();
			return;
		}

		// logic

		// 1. Mark curr cell as part of path
		Color curr_color = maze.getColor(x, y);
		trace.push(new PairInt(x, y));
		maze.recolor(x, y, GridColors.TEMPORARY);

		// 2. Explore all neighbors if they are valid
		if (x + 1 >= 0 && x + 1 < rows && y >= 0 && y < cols && !(maze.getColor(x + 1, y).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x + 1, y).equals(GridColors.TEMPORARY))) {
			findMazePathStackBased(x + 1, y, trace, result); // Up

		}

		if (x - 1 >= 0 && x - 1 < rows && y >= 0 && y < cols && !(maze.getColor(x - 1, y).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x - 1, y).equals(GridColors.TEMPORARY))) {
			findMazePathStackBased(x - 1, y, trace, result); // Down

		}

		if (x >= 0 && x < rows && y + 1 >= 0 && y + 1 < cols && !(maze.getColor(x, y + 1).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x, y + 1).equals(GridColors.TEMPORARY))) {
			findMazePathStackBased(x, y + 1, trace, result); // Right

		}

		if (x >= 0 && x < rows && y - 1 >= 0 && y - 1 < cols && !(maze.getColor(x, y - 1).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x, y - 1).equals(GridColors.TEMPORARY))) {
			findMazePathStackBased(x, y - 1, trace, result); // Left
		}

		// 3. Backtracking
		maze.recolor(x, y, curr_color);
		trace.pop();
	}

	// ADD METHOD FOR PROBLEM 3 HERE

	public ArrayList<PairInt> findMazePathMin(int x, int y) {
		ArrayList<ArrayList<PairInt>> result = new ArrayList<ArrayList<PairInt>>();

		Stack<PairInt> trace = new Stack<>();

		helper(0, 0, trace, result);
		if (result.size() == 0) {
			result.add(new ArrayList<>());
		}

		return result.get(0);
	}

	private void helper(int x, int y, Stack<PairInt> trace, ArrayList<ArrayList<PairInt>> result) {
		int rows = maze.getNCols();
		int cols = maze.getNRows();
		// base case
		if (x == rows - 1 && y == cols - 1) {
			trace.push(new PairInt(x, y));
			ArrayList<PairInt> temp = new ArrayList<PairInt>();
			temp.addAll(trace);
			if(result.size() == 0 || temp.size() < result.get(0).size()) {
				result.clear();
				result.add(temp);
			}
			trace.pop();
			return;
		}

		// 1. Mark curr cell as part of path
		Color curr_color = maze.getColor(x, y);
		trace.push(new PairInt(x, y));
		maze.recolor(x, y, GridColors.TEMPORARY);

		// 2. Explore all neighbors if they are valid
		if (x + 1 >= 0 && x + 1 < rows && y >= 0 && y < cols && !(maze.getColor(x + 1, y).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x + 1, y).equals(GridColors.TEMPORARY))) {
			helper(x + 1, y, trace, result); // Up

		}

		if (x - 1 >= 0 && x - 1 < rows && y >= 0 && y < cols && !(maze.getColor(x - 1, y).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x - 1, y).equals(GridColors.TEMPORARY))) {
			helper(x - 1, y, trace, result); // Down

		}

		if (x >= 0 && x < rows && y + 1 >= 0 && y + 1 < cols && !(maze.getColor(x, y + 1).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x, y + 1).equals(GridColors.TEMPORARY))) {
			helper(x, y + 1, trace, result); // Right

		}

		if (x >= 0 && x < rows && y - 1 >= 0 && y - 1 < cols && !(maze.getColor(x, y - 1).equals(GridColors.BACKGROUND))
				&& !(maze.getColor(x, y - 1).equals(GridColors.TEMPORARY))) {
			helper(x, y - 1, trace, result); // Left
		}

		// 3. Backtracking
		maze.recolor(x, y, curr_color);
		trace.pop();
	}


	/* <exercise chapter="5" section="6" type="programming" number="2"> */
	public void resetTemp() {
		maze.recolor(TEMPORARY, BACKGROUND);
	}
	/* </exercise> */

	/* <exercise chapter="5" section="6" type="programming" number="3"> */
	public void restore() {
		resetTemp();
		maze.recolor(PATH, BACKGROUND);
		maze.recolor(NON_BACKGROUND, BACKGROUND);
	}
	/* </exercise> */
}
/* </listing> */
