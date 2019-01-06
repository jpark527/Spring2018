package P2;
/**
 * Jae Hyun Park
 */

public class SmartRobot extends Robot {
	
	/**
	 * Construct a smart robot and place it in the grid.
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
	public SmartRobot(char name, Grid grid, int row, int col, int energyUnits) {
		super(name,grid,row,col,energyUnits);
	}
	
	/**
	 * Move to destination and update the grid as well as the current rows and 
	 * columns. It first moves diagonally as close to the destination as 
	 * possible and then moves either vertically or horizontally to reach the 
	 * destination. In case another robot is on the way, it also stores the 
	 * possible blocked row and column ahead. 
	 * 
	 * @param printEachStep
	 * 		      if true the state of the grid is printed after each step
	 * @return true if no other robot is on the way and successfully moved, 
	 * 		   false otherwise
	 */
	@Override
	protected boolean moveRobot(boolean printEachStep) {
		boolean move=true;
		int incOrDecR = destRow > curRow ? 1 : -1,   
			incOrDecC = destCol > curCol ? 1 : -1, 
			count = diagonalCount();
			// Move diagonally as close to the destination it can get to
			for(int i=0; i<count; ++i) {
				if(move=grid.moveFromTo(this, curRow, curCol, 
						blockedRow=curRow+incOrDecR, 
						blockedCol=curCol+incOrDecC)) { 
					curRow+=incOrDecR;      
					curCol+=incOrDecC;
					--energyUnits;
					if(printEachStep)
						grid.printGrid();
				} else {
					return move; 
				}
			}
		move=super.moveRobot(printEachStep);
		return move;
	}

	/**
	 * Calculate the total energy units needed to go from the current point 
	 * to the destination point.
	 * 
	 * @return energy units needed to get to the destination point
	 */
	@Override
	protected int energyUnitsNeeded() {
		int incOrDecR = destRow > curRow ? 1 : -1, 
			incOrDecC = destCol > curCol ? 1 : -1, 
			tempRow=curRow, tempCol=curCol;
		tempRow+=(incOrDecR*diagonalCount());
		tempCol+=(incOrDecC*diagonalCount());
		return diagonalCount() + Math.abs(tempRow-destRow)+
				Math.abs(tempCol-destCol);
	}
	
	/**
	 * Calculate the number of count in which a smart robot can move diagonally
	 * as close to the destination as possible.
	 *
	 * @return the largest number of count that a smart robot can move diagonally
	 */
	private int diagonalCount() {
		return Math.abs(destRow-curRow) > Math.abs(destCol-curCol) ?
			   Math.abs(destCol-curCol) : Math.abs(destRow-curRow);
	}
}
