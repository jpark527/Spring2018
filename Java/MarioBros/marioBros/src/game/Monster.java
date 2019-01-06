package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Monster extends Entity {
	
	private int index;

	public Monster(int posX, int posY, int velocityX, int velocityY, int width, 
			int height, boolean harmful, Game game, String[] img) {
		super(posX, posY, velocityX, velocityY, width, height, harmful, game, img);
		index = 0;
	}

	@Override
	public void render(Graphics g) {
		index = ++index%img.length;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.getImage(), posX, posY, width, height, null);
		setImage(Toolkit.getDefaultToolkit().getImage(img[index]));
	}

	@Override
	public void update() {
		posX += velocityX;
	}
} 
