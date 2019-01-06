package game;

import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class Coin extends Entity {
	
	public static final int COIN_WIDTH = 64, COIN_HEIGHT = 64;

	private boolean alive;
	private Sound coinSound;
	
	public Coin(int posX, int posY, int velocityX, int velocityY, int width, 
			int height, boolean harmful, Game game, String[] img) {
		super(posX, posY, velocityX, velocityY, width, height, harmful, game, img);
		alive = true;
	}

	/**
	 * Render a coin until it gets eaten by Mario. Once the coin gets eaten, 
	 * it no longer render its image.
	 */
	@Override
	public void render(Graphics g) {
		if(alive) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(this.getImage(), posX, posY, width, height, null);
		}
	}
	
	/**
	 * Update a coin until it gets eaten by Mario. When the coin gets eaten, 
	 * it will make a sound, and add the score by 1.
	 */
	@Override
	public void update() {
		if(!alive)
			return;
		posX += velocityX;
		if(isCollidedWithMario()) {
			alive = false;
			coinSound = new Sound(Main.COIN_SOUND);
			coinSound.start();
			game.setScore(game.getScore()+1);
		}
	}
	
	/**
	 * Check if a coin is collided with Mario.
	 * 
	 * @return true, if a coin collides with Mario, false otherwise.
	 */
	public boolean isCollidedWithMario() {
		Entity e = game.getEntities().get(0);
		if(e instanceof Mario) {
			if(getBoundsNorth().intersects(e.getBounds()) || 
			   getBoundsSouth().intersects(e.getBounds()) || 
			   getBoundsWest().intersects(e.getBounds())  || 
			   getBoundsEast().intersects(e.getBounds()))
				return true;
		}
		return false;
	}

} 
