package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;

@SuppressWarnings("serial")
public class Mario extends Entity {
	
	private Sound marioTheme;
	private Sound gameoverSound;
	private Sound victory;
	
	private int index;
	private boolean onGround, slide, dead, soundPlayOnlyOnce;

	public Mario(int posX, int posY, int velocityX, int velocityY, 
			int width, int height, boolean valid, Game game, String[] img) {
		super(posX, posY, velocityX, velocityY, width, height, valid, game, img);
		index=-1;
		soundPlayOnlyOnce = onGround = true;
		slide = dead = false; 
		marioTheme = new Sound(Main.IN_GAME_SOUND);
		gameoverSound = new Sound(Main.GAMEOVER_SOUND);
		victory = new Sound(Main.VICTORY_SOUND);
		marioTheme.start();
	}

	/*
	 * Render Mario on the game screen. Make our Mario look like he is running,
	 * jumping, or sliding.
	 */
	@Override
	public void render(Graphics g) {
		int newWidth = width, newHeight = height;
		Graphics2D g2d = (Graphics2D) g;
		if(!onGround)
			setImage(Toolkit.getDefaultToolkit().getImage(img[img.length-2]));
		else {
			if(isSliding())
				setImage(Toolkit.getDefaultToolkit().getImage
						(img[img.length-1]));
			else 
				setImage(Toolkit.getDefaultToolkit().getImage
						(img[++index%(img.length-2)]));	
		}
		g2d.drawImage(this.getImage(), posX, posY, newWidth, newHeight, null);
	}

	/*
	 * Update Mario on the game screen. Gravity is set for Mario also.
	 */
	@Override
	public void update() {  
		checkStatus();	
		if(isStopped() && !dead)
			return ;
		if(posY<=0)
			posY = 0;	
		if(isJumped()) {
			onGround = false;
			gravity -= .4;
			setVelocityY((int)(-4 * gravity));
		}
		if(gravity<=0.) {
			setJump(false);
			setFall(true);
		}
		if(isFalling()) {
			gravity += 1.2;
			setVelocityY((int)gravity);
		}
		posY += velocityY;
	}
	
	/*
	 * Do collision check of Mario with other entities that are harmful.
	 * If Mario runs into any of these harmful entities from any side, 
	 * Mario dies.
	 */
	private void collisionWithHarmful() {
		List<Entity> e = game.getEntities();
		for(int i=1; i<e.size(); ++i) {
			if(e.get(i).isHarmful()) {
				if(getBoundsNorth().intersects(e.get(i).getBounds()) || 
				   getBoundsSouth().intersects(e.get(i).getBounds()) || 
				   getBoundsWest().intersects(e.get(i).getBounds())  || 
				   getBoundsEast().intersects(e.get(i).getBounds())) 
					dead = true;	
			}
		}
	}
	
	/*
	 * Do collision check of Mario with other entities that are NOT harmful.
	 * If the entity happens to be a coin, skip the check and run over. And 
	 * if the entity happens to be the finishing point, our Mario gets located
	 * to the other side of the finishing point.
	 */
	private void collisionWithNotHarmful() { 
		List<Entity> e = game.getEntities();
		for(int i=1; i<e.size(); ++i) {
			int w = e.get(i) instanceof FinishingPoint ? 
					-1 * e.get(i).getWidth() : e.get(i).getWidth();				
			if(!e.get(i).isHarmful()) {
				if(e.get(i) instanceof Coin) 
					continue;
				if(getBoundsSouth().intersects(e.get(i).getBounds())) {		
					onGround = true;
					setVelocityY(0);
					posY = e.get(i).getY() - height;
					if(isFalling())
						setFall(false);
				} else {
					if(!isFalling() && !isJumped()) { 
						gravity = 0.;
						setFall(true);
					}		
				} 
				if(getBoundsWest().intersects(e.get(i).getBounds()))
					posX = e.get(i).getX() - w;
				if(getBoundsEast().intersects(e.get(i).getBounds()))		
					posX = e.get(i).getX() + w;
			}
		}
	}

	private void checkCollision() {
		collisionWithNotHarmful();
		collisionWithHarmful();
	}
	
	/*
	 * Check Mario's status, and proceed different actions for different 
	 * situations accordingly.
	 */
	private void checkStatus() {               
		if(dead) {					
			game.stopAll();
			if(soundPlayOnlyOnce) {   
				marioTheme.stop();
				gameoverSound.start();
				soundPlayOnlyOnce = false;
			} 
		} else {
			if(game.won()) {   
				marioTheme.stop();
				dead = true;
				if(soundPlayOnlyOnce) {   
					victory.start();
					soundPlayOnlyOnce = false;
				}	
			} else
				checkCollision();
		}
	}

	public boolean isOnGround() {
		return onGround;
	}
	
	public boolean isSliding() {
		return slide;
	}

	public void setSlide(boolean slide) {
		this.slide = slide;
	}
	
	/*
	 * Check if Mario is dead. If Mario falls below 1500 of y coordinate, 
	 * Mario is claimed dead. 
	 */
	public boolean isDead() {
		return posY > 1500;
	}
	
	@Override
	public void stop() {
		super.stop();
		if(!dead)
			marioTheme.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
		if(!dead)
			marioTheme.resume();
	}
} 
