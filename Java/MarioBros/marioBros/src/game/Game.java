package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Game {
	public static final String coinImg = "/Users/j/Java/MarioBros/marioBros/src/game/coin.png";
	
	private final String mario1 = "/Users/j/Java/MarioBros/marioBros/src/game/0.png";
	private final String mario2 = "/Users/j/Java/MarioBros/marioBros/src/game/1.png";
	private final String mario3 = "/Users/j/Java/MarioBros/marioBros/src/game/2.png";
	private final String mario4 = "/Users/j/Java/MarioBros/marioBros/src/game/3.png";
	private final String mario5 = "/Users/j/Java/MarioBros/marioBros/src/game/4.png";
	private final String marioJump = "/Users/j/Java/MarioBros/marioBros/src/game/jump.png";
	private final String marioSlide = "/Users/j/Java/MarioBros/marioBros/src/game/slide.png";
	
	private final String groundImg = "/Users/j/Java/MarioBros/marioBros/src/game/ground.png";
	private final String brickImg = "/Users/j/Java/MarioBros/marioBros/src/game/afterabnormalwall.png";
	
	private final String flowerImg = "/Users/j/Java/MarioBros/marioBros/src/game/badFlower.png";
	private final String flowerImgR = "/Users/j/Java/MarioBros/marioBros/src/game/badFlowerR.png";
	
	private final String fire1 = "/Users/j/Java/MarioBros/marioBros/src/game/fire1.png";
	private final String fire2 = "/Users/j/Java/MarioBros/marioBros/src/game/fire2.png";
	private final String fire3 = "/Users/j/Java/MarioBros/marioBros/src/game/fire3.png";
	private final String fire4 = "/Users/j/Java/MarioBros/marioBros/src/game/fire4.png";
	private final String fire5 = "/Users/j/Java/MarioBros/marioBros/src/game/fire5.png";
	
	private final String mushroom_1 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroom-1.png";
	private final String mushroom_2 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroom-2.png";
	private final String mushroom_3 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroom-3.png";
	private final String mushroom_4 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroom-4.png";
	private final String mushroom_5 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroom-5.png";
	
	private final String mushroomR_1 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroomR-1.png";
	private final String mushroomR_2 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroomR-2.png";
	private final String mushroomR_3 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroomR-3.png";
	private final String mushroomR_4 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroomR-4.png";
	private final String mushroomR_5 = "/Users/j/Java/MarioBros/marioBros/src/game/mushroomR-5.png";
	
	private final String finishPoint = "/Users/j/Java/MarioBros/marioBros/src/game/finalPoint.png";
	
	private List<Entity> entities;
	private int score;
	
	public Game() {
		setScore(0);
		entities = new ArrayList<>();
	}
	
	/*
	 * Render all the entities on the screen.
	 */
	public void render(Graphics g) {
		for(Entity e : entities)
			e.render(g);
	}
	
	/*
	 * Update all the entites on the screen.
	 */
	public boolean update() {	          
		boolean gameOn = !entities.isEmpty();
		if(!gameOn)
			createAll();
		for(Entity e : entities) {
			e.update();
			if(e instanceof Mario)                           
				gameOn = !((Mario) e).isDead();              
		}
		return gameOn;
	}
	
	/*
	 * Check if the game is won.
	 */
	public boolean won() {
		for(Entity e: entities) {
			if(e instanceof FinishingPoint)
				return ((FinishingPoint) e).isMarioArrived();
		}
		return false;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void removeAll() {
		entities.clear();
		score = 0;
	}
	
	public void stopAll() {
		for(Entity e : entities)
			e.stop();
	}
	
	public void resumeAll() {
		for(Entity e : entities)
			e.resume();
	}
	
	private void createMario() {
		String[] marioImg = new String[] 
				{mario1, mario2, mario3, mario4, mario5, marioJump, marioSlide};
		Mario mario = new Mario(300, 700, 0, 0, 100, 100, true, this, marioImg);
		addEntity(mario);
	}
	
	/*
	 * Create structural elements such as bricks, ground, and finishing pole.
	 */
	private void createMap() {
		// Create the finishing pole.
		addEntity(new FinishingPoint(11300, 0, -8, 0, 100, 800, 
				false, this, new String[] {finishPoint}));
		// Create ground.
		for(int i=0; i<2; ++i) {
			addEntity(new Wall(0 + i*10000,Main.HEIGHT-100,-8,0,5700,100, 
					false, this, new String[] {groundImg}));
			addEntity(new Wall(6000 + i*10000,Main.HEIGHT-100,-8,0,4000,100, 
					false, this, new String[] {groundImg}));
		}	
		// Create bricks.
		for(int i=0; i<6; ++i)  {
			addEntity(new Wall(64+64*i,300,-8,0,64,64, false, this, 
			new String[] {brickImg}));
			addEntity(new Wall(5300+64*i,400,-8,0,64,64, false, this, 
					new String[] {brickImg}));
		}
		for(int i=8; i<15; ++i) {
			addEntity(new Wall(5300+64*i,400,-8,0,64,64, false, this, 
					new String[] {brickImg}));
		}
	}
	
	/*
	 * Create harmful obstacles in the game such as flowers, mushrooms, and 
	 * flying fire balls.
	 */
	private void createObstacles() {
		String[] badFlower = new String[] {flowerImg};
		String[] badFlowerR = new String[] {flowerImgR};
		String[] mushroom = new String[] 
				{mushroom_1, mushroom_2, mushroom_3, 
						mushroom_4, mushroom_5};
		String[] mushroomR = new String[] {mushroomR_1, mushroomR_2, 
				mushroomR_3, mushroomR_4, mushroomR_5};
		String[] fire = new String[] {fire1, fire2, fire3, fire4, fire5};
		
		for(int i=1; i<10; ++i) {
			addEntity(new Monster(300+i*1000,Main.HEIGHT-340,-8,0,64,240, true, 
					this, badFlower));
			addEntity(new Monster(i*1000,0,-8,0,64,240, true, this, badFlowerR));
			addEntity(new Monster(700+1000*i,Main.HEIGHT/2-180,-8,0,64,180, 
					true, this, mushroom));
			addEntity(new Monster(700+1000*i,Main.HEIGHT/2,-8,0,64,180, true, 
					this, mushroomR));
			addEntity(new Monster(700+1500*(i+12),Main.HEIGHT/2-240,-24,0,180,
					180, true, this, fire));
			addEntity(new Monster(700+1900*(i+12),Main.HEIGHT/2-640,-24,0,180,
					180, true, this, fire));
		}
	}
	
	/*
	 * Create coins in the game.
	 */
	private void createCoins() {
		// create coins on the ground.
		for(int i=0; i<100; ++i) {      
			if((1400+100*i)%1000==300) 
				continue;
			addEntity(new Coin(1400+100*i, 600, -8, 0, 100, 100, false, this, 
					new String[] {coinImg}));
		}
		// create one coin each above every bad flowers. 
		for(int i=1; i<10; ++i)  
			addEntity(new Coin(300+i*1000, 250, -8, 0, 100, 100, false, this, 
					new String[] {coinImg}));
	}
	
	private void createAll() {
		if(entities.isEmpty()) {
			createMario();
			createMap();
			createObstacles();
			createCoins();
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
} 
