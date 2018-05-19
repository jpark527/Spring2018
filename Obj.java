package homeworkGUI;

public class Obj {
	private int x, y;
	
	public Obj(int x, int y) {
		setX(x);
		setY(y);
	}

	public Obj() {
		this(-100,-100);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void moveBy(int x, int y, int limit) {
		this.x+=x;
		this.y+=y;
		this.x%=limit;
		this.y%=limit;
	}
}
