package game;

import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class FinishingPoint extends Entity {
	
	private boolean marioArrived;

	public FinishingPoint(int posX, int posY, int velocityX, int velocityY, 
			int width, int height, boolean harmful, Game game, String[] img) {
		super(posX, posY, velocityX, velocityY, width, height, harmful, game, img);
		setMarioArrived(false);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getImage(), posX, posY, width, height, null);
	}

	@Override
	public void update() { 
		posX += velocityX;
		// If Mario gets to the finishing pole, stop the game.
		Mario m = (Mario) game.getEntities().get(0);
		if(m.getX() > (getX()+200)) {
			game.stopAll();
			marioArrived = true;
		}
	}

	public boolean isMarioArrived() {
		return marioArrived;
	}

	public void setMarioArrived(boolean marioArrived) {
		this.marioArrived = marioArrived;
	}	
} 
