package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

// Use SwingWorker instead of Thread for the future reference!!!

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {
	
	static final int WIDTH = 1000, HEIGHT = 800;
	static final String PAUSE_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/pauseSound.wav";
	static final String START_SCREEN_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/startScreenSound.wav";
	static final String IN_GAME_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/mario.wav";
	static final String GAMEOVER_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/marioDead.wav";
	static final String JUMP_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/marioJump.wav";
	static final String VICTORY_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/victory.wav";
	static final String COIN_SOUND = "/Users/j/Java/MarioBros/marioBros/src/game/coinSound.wav";
	
	private final ImageIcon mainLogo = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/logo.png");
	private final ImageIcon black = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/black.jpg");
	private final ImageIcon gameoverBackground = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/gameOverBackground.png");	
	private final ImageIcon background = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/background.png");
	private final ImageIcon gameWonBackground = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/marioWon.jpg");
	private final ImageIcon pauseSign = new ImageIcon
			("/Users/j/Java/MarioBros/marioBros/src/game/pause.png");
	private final ImageIcon coinImg = new ImageIcon(Game.coinImg);
	
	private final ImageIcon life1 = new ImageIcon("/Users/j/Java/MarioBros/marioBros/src/game/life1.png");
	private final ImageIcon life2 = new ImageIcon("/Users/j/Java/MarioBros/marioBros/src/game/life2.png");
	private final ImageIcon life3 = new ImageIcon("/Users/j/Java/MarioBros/marioBros/src/game/life3.png");
	private final ImageIcon life4 = new ImageIcon("/Users/j/Java/MarioBros/marioBros/src/game/life4.png");
	private final ImageIcon life5 = new ImageIcon("/Users/j/Java/MarioBros/marioBros/src/game/life5.png");
	
	private ImageIcon[] life;
	private int lifeRemaining, wait, blinkSoon, totalScore;
	private Thread thread;
	private boolean running, showLifeScreen, gameOver, gameStart, blink, won, 
					pause;
	private Game game;
	private Sound jump, start, pauseSound;
	
	public Main() {
		totalScore = wait = blinkSoon = 0;
		lifeRemaining = 5;
		life = new ImageIcon[] { life1, life2, life3, life4, life5 };
		showLifeScreen = blink = true;
		pause = running = gameOver = gameStart = won = false;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBounds(0, 0, WIDTH, HEIGHT);
		jump = new Sound(JUMP_SOUND);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mario Run");
		Main game = new Main();
		frame.add(game);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		game.start();
	}

	/**
	 * Render images on the screen. There are screens for before-game, in-game,
	 * and after-game screens. You could get two different type of screens for
	 * after-game.
	 */
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		super.paint(g);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		if(gameStart)
			renderGameScreen(g);
		else {
			if(gameOver) {
				if(won)
					renderAfterGameScreen(g, true);
				else
					renderAfterGameScreen(g, false);
			} else
				renderStartScreen(g);
		}
		g.dispose();
		bs.show();
	}
	
	/**
	 * Render after-game screen. If won, render won-game screen, otherwise
	 * render a regular game-over screen.
	 * 
	 * @param g - graphics to draw
	 * @param victory - true if won, false otherwise
	 */
	private void renderAfterGameScreen(Graphics g, boolean victory) {
		if(victory)
			g.drawImage(gameWonBackground.getImage(), 0, 0, WIDTH, HEIGHT, this);
		else
			g.drawImage(gameoverBackground.getImage(), 0, 0, WIDTH, HEIGHT, this);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Papyrus", Font.BOLD, 70));
		g.drawImage(coinImg.getImage(), 400, 450, 100, 100, this);
		g.drawString("x" + Integer.toString(totalScore), 526, 525);
		if(blink) {
			g.setFont(new Font("Papyrus", Font.BOLD, 45));
			g.drawString("Press 'ENTER' to continue..", 240, 630);
		}
		makeFontBlink();
	}
	
	private void renderStartScreen(Graphics g) {
		g.drawImage(black.getImage(), 0, 0, WIDTH, HEIGHT, this);
		g.drawImage(mainLogo.getImage(), 230, 100, WIDTH/2, HEIGHT/2, this);
		if(blink) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Papyrus", Font.BOLD, 60));
			g.drawString("Press 'ENTER' to start!", 180, 650);
		}
		makeFontBlink();
	}
	
	/**
	 * Render in-game screen. In-game screens consists of two different
	 * screens which are life screen, and actual in-game screen. In an actual
	 * in-game screen, the score for each game is rendered on the top right-
	 * hand corner.
	 * 
	 * @param g - graphics to draw
	 */
	private void renderGameScreen(Graphics g) {   
		if(showLifeScreen) {  
			if(!(lifeRemaining>0)) {  
				gameOver = true;
				gameStart = false;
				return;
			}
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(life[lifeRemaining-1].getImage(), WIDTH/2 - 100, 
					HEIGHT/2 - 50, 200, 100, this);			
		} else {
			g.drawImage(background.getImage(), 8, 0,  WIDTH, HEIGHT, this);
			game.render(g);
			g.drawImage(coinImg.getImage(), WIDTH-180, 5, 80, 80, this);
			g.setFont(new Font("Papyrus", Font.BOLD, 55));
			g.drawString("x" + Integer.toString(game.getScore()), WIDTH-90, 60);
		}
		if(pause) {
			g.drawImage(pauseSign.getImage(), 100, 70, 800, 640, this);
			game.stopAll();
		} else 
			game.resumeAll();
	}
	
	private void update() {          
		if(wait==0 && gameStart && !gameOver && !game.update()) { 
			totalScore += game.getScore();
			game.removeAll();
			showLifeScreen = true;
		}
		if(game.won()) {
			gameOver = true;
			gameStart = false;
			game.update();
			totalScore += game.getScore();
			game.removeAll();
			won = true;
		}
	}

	private void makeFontBlink() {
		if(!blink)
			++blinkSoon;
		else
			--blinkSoon;
		if(blinkSoon>200) {
			blink = true;
			blinkSoon = 0;
		}
		if(blinkSoon<-200) {
			blink = false;
			blinkSoon = 0;
		}
	}
	
	private void holdScreen() {
		if(gameStart && showLifeScreen)
			++wait;
		if(wait>800) {                      
			wait = 0;
			showLifeScreen = false;
			--lifeRemaining;
		}		
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {                                  
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public boolean isFinished() {
		return !running;
	}
	
	/**
	 * Run the game thread 100 frames per second while rendering images, 
	 * and updating.
	 */
	@Override
	public void run() {   
		long prevTime = System.nanoTime();
		double timeDiffInSec = 0., nanoToSec = 1000000000. / 100.; 
		game = new Game();
		addKeyListener(new DirectionListener());
		while(running) {                  
			Long curTime = System.nanoTime();
			timeDiffInSec += (curTime - prevTime) / nanoToSec;			
			while(timeDiffInSec >= 1.) {
				update();
				timeDiffInSec = 0.;
			}
			render();
			prevTime = curTime;
			holdScreen();
		}	
		stop();
	}

	private class DirectionListener implements KeyListener {

		private void changeGameScreen() {
			start = new Sound(START_SCREEN_SOUND);
			start.start();
			if(!gameStart) {
				totalScore = 0;
				if(gameOver) {
					gameOver = false;
					won = false;
					showLifeScreen = true;
					lifeRemaining = 5;
				} else
					gameStart = true;
			}
		}
		
		private void pauseScreen() {
			if(!gameStart)
				return;
			if(gameStart && !gameOver)
				pause = !pause;
			if(pause)
				jump.pause();
			else
				jump.resume();
			pauseSound = new Sound(PAUSE_SOUND);
			pauseSound.start();
		}
		
		private void makeMarioJump() {
			Entity e = game.getEntities().isEmpty() ? 
					null : game.getEntities().get(0);		
			if(e instanceof Mario) {
				if(!((Mario) e).isDead() && !e.isStopped() && 
					((Mario) e).isOnGround() && !e.isJumped()) {
					e.setVelocityY(0);
					jump = new Sound(JUMP_SOUND);
					jump.start();
					e.setJump(true);
					e.setGravity(9.81);
				}
			}
		}
		
		private void marioSlideStop() {
			Entity e = game.getEntities().isEmpty() ? 
					null : game.getEntities().get(0);
			if(e instanceof Mario) {
				Mario m = (Mario) e;	
				m.setSlide(false);	
			}
		}
		
		private void makeMarioSlide() {
			Entity e = game.getEntities().isEmpty() ? 
					null : game.getEntities().get(0);	
			if(e instanceof Mario && !e.isStopped()) {
				Mario m = (Mario) e;
				m.setSlide(true);
			}
		}
		
		public void keyPressed (KeyEvent event) {		
			switch (event.getKeyCode()) {
				case KeyEvent.VK_DOWN: 
					makeMarioSlide();
					break;
				case KeyEvent.VK_SPACE: 
					makeMarioJump();
					break;
				case KeyEvent.VK_ESCAPE:
					pauseScreen();
					break;
				case KeyEvent.VK_ENTER:           
					changeGameScreen();
			}
		}
		
		public void keyTyped (KeyEvent event) { }
		
		public void keyReleased (KeyEvent event) { 
			if(event.getKeyCode() == KeyEvent.VK_DOWN)
				marioSlideStop();
		}
	}
} 
