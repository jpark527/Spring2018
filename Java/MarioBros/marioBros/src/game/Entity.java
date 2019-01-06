package game;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public abstract class Entity extends ImageIcon {
	
	protected int posX, posY, velocityX, velocityY, width, height;
	protected double gravity;
	private boolean harmful, jump, fall, onlyOnce, stopped;
	protected Game game;
	protected String[] img;
	private int velXSaved, velYSaved;
	
	public Entity(int posX, int posY, int velocityX, int velocityY, int width, 
			int height, boolean harmful, Game game, String[] img) {
		super(img[0]);
		stopped = false;
		onlyOnce = true;
		this.img = img;
		velXSaved = velYSaved = -99;
		gravity = 0.;
		this.setX(posX);
		this.setY(posY);
		this.setVelocityX(velocityX);
		this.setVelocityY(velocityY);
		this.setWidth(width);
		this.setHeight(height);
		this.harmful = harmful;
		this.game = game;
		setJump(false);
		setFall(true);     
	}

	public abstract void render(Graphics g);
	public abstract void update();
	
	public int getX() {
		return posX;
	}

	public void setX(int posX) {
		this.posX = posX;
	}

	public int getY() {
		return posY;
	}

	public void setY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}
	
	public boolean isHarmful() {
		return harmful;
	}
	
	protected Rectangle getBounds() {
		return new Rectangle(posX, posY, width, height);
	}
	
	protected Rectangle getBoundsNorth() {
		return new Rectangle(posX+10, posY, width-20, 5);
	}
	
	protected Rectangle getBoundsSouth() {
		return new Rectangle(posX+10, posY+height-5, width-20, 5);
	}
	
	protected Rectangle getBoundsEast() {
		return new Rectangle(posX, posY+10, 5, height-20);
	}
	
	protected Rectangle getBoundsWest() {
		return new Rectangle(posX+width-5, posY+10, 5, height-20);
	}

	public boolean isJumped() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public boolean isFalling() {
		return fall;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	
	public void setFall(boolean fall) {
		this.fall = fall;
	}
	
	public void setImage(String[] img) {
		this.img = img;
	}
	
	public void stop() {  
		stopped = true;
		if(velXSaved==-99 && velYSaved==-99)
			onlyOnce = true;
		if(onlyOnce) {
			velXSaved = velocityX;
			velYSaved = velocityY;
			velocityX = velocityY = 0;  
			onlyOnce = false;
		}
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public void resume() {
		stopped = false;
		if(velXSaved!=-99 || velYSaved!=-99) {
			velocityX = velXSaved;
			velocityY = velYSaved;
			velXSaved = velYSaved = -99;      
		}	
	}			
	
} 
