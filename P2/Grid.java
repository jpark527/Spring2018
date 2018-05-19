package P2;

/**
 * Jae Hyun Park
 */
public class Grid {

	private int rows, cols;
	private char[][] occupied;

	/**
	 * Construct new grid
	 * 
	 * @param rows
	 * 		      number of rows	
	 * @param cols
	 * 			  number of columns
	 */
	public Grid(int rows, int cols) {
		occupied = new char[this.rows = rows][this.cols = cols];
	}

	/**
	 * Get rows
	 * 
	 * @return number of rows
	 * 
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Get columns
	 * 
	 * @return number of columns
	 * 
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Check if a given position is available.
	 * 
	 * @param row
	 *            row to check if available
	 * @param col
	 *            column to check if available
	 * @return true if position in grid and not occupied
	 */
	public boolean isPositionAvailable(int row, int col) {
		return isPositionValid(row, col) && occupied[row][col] == '\0';
	}

	/**
	 * Get robot at given position
	 * 
	 * @param row
	 *            current row of robot
	 * @param col
	 *            current column of robot
	 * @return robot at position or null otherwise
	 */
	public Robot getRobotAt(int row, int col) {
		if (occupied[row][col] != '\0')
			return new Robot(occupied[row][col], this, row, col, 0); 
		else
			return null;
	}

	/**
	 * Add a robot to the grid
	 * 
	 * @param r
	 *            the robot to add
	 * @param row
	 *            row to add robot
	 * @param col
	 *            column to add robot
	 * @return True if added, false if position not available
	 */
	public boolean add(Robot r, int row, int col) {
		boolean added = isPositionAvailable(row, col);
		if (added)
			occupied[row][col] = r.getName();
		return added;
	}

	/**
	 * Move a robot from one position to another. Both positions must be valid and
	 * the destination must be not occupied. The current position given must be
	 * occupied by the given robot.
	 * 
	 * @param r
	 *            the robot
	 * @param curRow
	 *            current row
	 * @param curCol
	 *            current column
	 * @param destRow
	 *            destination row
	 * @param destCol
	 *            destination column
	 * @return true if robot moved, false otherwise.
	 */
	public boolean moveFromTo(Robot r, int curRow, int curCol, int destRow, 
			int destCol) {   
		boolean move = (destRow!=curRow || destCol!=curCol) && 
				isPositionValid(curRow, curCol) && 
				r.getName()==occupied[curRow][curCol];
		if(move)
			if(move = add(r, destRow, destCol)) 
				occupied[curRow][curCol]='\0';                      
		return move;
	}

	/**
	 * Print all the grid positions
	 */
	public void printGrid() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for (int row = 0; row < rows; ++row) {
			for (int col = 0; col < cols; ++col) {
				String mark = occupied[row][col] == '\0' ? 
					"      " : occupied[row][col] + "(" + row + "," + col + ")";
				System.out.print("|" + mark);
			}
			System.out.println("|");
		}
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * Check if a position is within the grid.
	 * 
	 * @param row
	 *            row to check
	 * @param col
	 *            column to check
	 * @return true if in grid, false otherwise
	 */
	public boolean isPositionValid(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}
}
