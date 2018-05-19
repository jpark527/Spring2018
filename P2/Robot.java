package P2;

/**  
 * @author Jae Hyun Park  
 * 
 * I have tested this program and it has no issues.
 * 
 * This program simulates robots operating on a rectangular grid. Once robots
 * are instantiated, they automatically mark themselves on a grid putting the
 * first character of their names, and the grid point they are at. Robots 
 * generates an error message if any issues occur, otherwise execute their 
 * tasks dedicatedly.
 */

public class Robot {

	private char name;
	private String task;
	protected int curRow, curCol, destRow, destCol, 
				  blockedRow, blockedCol, energyUnits;
	protected Grid grid;
	
	/**
	 * Construct a robot and place it in the grid.
	 * 
	 * @param name
	 *            first character of a robot's name that is recorded on a grid
	 * @param grid
	 *            grid to record at
	 * @param row
	 *            current row
	 * @param col
	 *            current column
	 * @param energyUnits
	 *            amount of robot's energy that is consumed when it moves 
	 */
	public Robot(char name, Grid grid, int row, int col, int energyUnits) {
		this.name = name;
		this.grid = grid;
		curRow = row;
		curCol = col;
		this.energyUnits = energyUnits;
		grid.add(this, row, col);
	}

	/**
	 * Set a task for the robot and the destination for the task. An error message
	 * is printed to indicate an invalid destination.
	 * 
	 * @param task
	 * 		      the task given to robot
	 * @param destRow
	 * 		      destination row
	 * @param destCol
	 * 		      destination column
	 * @return true if destination is valid, false otherwise.
	 */
	public boolean setTask(String task, int destRow, int destCol) {
		boolean taskSet;
		if(taskSet=grid.isPositionValid(destRow, destCol)) {
			this.task=task;
			this.destRow=destRow;
			this.destCol=destCol;
		} else {
			System.out.printf("Destination position (%d, %d) is not a valid "
					+ "grid position.\n",destRow,destCol);
			this.task="";
		}	
		return taskSet;
	}

	/**
	 * Move to destination and execute the task. An error message is printed if
	 * there is no task set, not enough energy, or blocked along the way.
	 * 
	 * @param printEachStep
	 *            if true the state of the grid is printed after each step
	 * @return true if task executed successfully, false otherwise
	 */
	public boolean executeTask(boolean printEachStep) {
		boolean executed = false;
		if(task.equals("")) {
			System.out.printf("%c(%d,%d) unable to execute task. No task set.\n"
					,name,curRow,curCol);
		} else if(energyUnits-energyUnitsNeeded() < 0) {
			System.out.printf("%c(%d,%d) unable to execute task. %d energy units"
					+ " are required.\n",name,curRow,curCol,energyUnitsNeeded()
					-energyUnits);
		} else {
			System.out.printf("%c(%d,%d) progressing to destination (%d, %d).\n"
					,name,curRow,curCol,destRow,destCol);	
			executed = moveRobot(printEachStep);   
			Robot blockedBy = grid.getRobotAt(blockedRow, blockedCol);
			// Prints an error message if some other robot is on the way,
			// prints a completion message otherwise
			if(!executed&&blockedBy!=null) {
				System.out.printf("%c(%d,%d) unable to execute task. Cannot "
						+ "progress to destination (%d, %d). Blocked by "
						+ "%c(%d,%d).\n",name,curRow,curCol,destRow,destCol,
						blockedBy.name, blockedBy.curRow, blockedBy.curCol);
			} else {
				System.out.printf("%c(%d,%d): %s completed. Energy level: %d\n"
						, name, destRow, destCol, task, energyUnits);
			}
		}
		return executed;
	}

	/**
	 * Add more energy to the robot
	 * 
	 * @param energyUnits
	 */
	public void recharge(int energyUnits) {
		this.energyUnits += energyUnits;
	}

	/**
	 * Get name
	 * 
	 * @return the character that is recorded on a grid
	 */
	public char getName() {
		return name;
	}

	/**
	 * Move to destination and update the grid as well as the current rows and 
	 * columns. It first moves vertically all the way, then horizontally all 
	 * the way until the destination is reached. In case another robot is on 
	 * the way, it also stores the possible blocked row and column ahead. 
	 * 
	 * @param printEachStep
	 * 		      if true the state of the grid is printed after each step
	 * @return true if no other robot is on the way and successfully moved, 
	 * 		   false otherwise
	 */
	protected boolean moveRobot(boolean printEachStep) {
		int incOrDecR = destRow > curRow ? 1 : -1, 
			incOrDecC = destCol > curCol ? 1 : -1;
		boolean move=true;
		// Move vertically all the way until reaches the destination column
		while(move && curRow!=destRow) {
			if(move=grid.moveFromTo(this, curRow, curCol, 
					blockedRow=curRow+incOrDecR, blockedCol=curCol)) {
				curRow+=incOrDecR;
				--energyUnits;
				if(printEachStep)
					grid.printGrid();
			}
		}
		// Move horizontally all the way until reaches the destination column
		while(move && curCol!=destCol) {
			if(move=grid.moveFromTo(this, curRow, curCol, blockedRow=curRow, 
					blockedCol=curCol+incOrDecC)) {
				curCol+=incOrDecC;
				--energyUnits;
				if(printEachStep)
					grid.printGrid();
			}
		}
		return move;
	}
	
	/**
	 * Calculate the total energy units needed to go from the current point 
	 * to the destination point.
	 * 
	 * @return energy units needed to get to the destination point
	 */
	protected int energyUnitsNeeded() {
		return Math.abs(curRow-destRow)+Math.abs(curCol-destCol);
	}
	
}
