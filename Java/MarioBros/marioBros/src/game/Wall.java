package game;

import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class Wall extends Entity {

	public Wall(int posX, int posY, int velocityX, int velocityY, 
			int width, int height, boolean alive, Game game, String[] img) {
		super(posX, posY, velocityX, velocityY, width, height, alive, game, img);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getImage(), posX, posY, width, height, null);
	}

	@Override
	public void update() { 
		posX += velocityX;
	}

} 
