package P2;
public class RobotDriver {

	public static void main(String[] args) {
		Grid g = new Grid(6, 5);
		System.out.println("******************** Test 1 ********************");
		g.printGrid();
		System.out.println("******************** Test 2 ********************");
		Robot rob = new Robot('R', g, 0, 1, 10);
		Robot walle = new Robot('W', g, 3, 4, 10);
		g.printGrid();
		System.out.println("******************** Test 3 ********************");
		rob.setTask("Drop off book", 15, 3);
		rob.executeTask(true);
		System.out.println("******************** Test 4 ********************");
		rob.setTask("Drop off book", 4, 4);
		rob.executeTask(true);
		System.out.println("******************** Test 5 ********************");
		walle.setTask("Pick up flower", 3, 0);
		walle.executeTask(true);
		System.out.println("******************** Test 6 ********************");
		walle.setTask("Drop off flower", 0, 4);
		walle.executeTask(true);
		System.out.println("******************** Test 7 ********************");
		walle.recharge(5);
		walle.executeTask(true);
		System.out.println("******************** Test 8 ********************");
		rob.setTask("Pick up book", 0, 4);
		rob.recharge(10);
		rob.executeTask(true);
		System.out.println("******************** Test 9 ********************");
		walle.setTask("Move out of the way", 0, 3);
		walle.executeTask(true);
		System.out.println("******************** Test 10 ********************");
		rob.setTask("Pick up book", 0, 4);
		rob.executeTask(true);
		System.out.println("******************** Test 11 ********************");
		Robot smarty = new SmartRobot('S', g, 4, 4, 5);
		g.printGrid();
		smarty.setTask("Buy candy", 2, 0);
		smarty.executeTask(true);
		System.out.println("******************** Test 12 ********************");
		smarty.recharge(5);
		smarty.setTask("Deliver candy", 0, 4);
		smarty.executeTask(true);
	}

}
