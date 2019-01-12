package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.Color;
import java.util.ArrayList;


public class City {

	/** board is a 2D array with an entry for each space in the city.
	 * if board[x][y] == 1 then the space is a building, board[x][y] == 2 then the space is a Person,
	 * board[x][y] == 3 then the space is a Zombie, board[x][y] == 4 then the space is a Warrior,
	 * board[x][y] == 0 then the space is empty. Humans should never go into spaces that have a wall.
	 *
	*/
	
	 /**
	 * Within the Array of directions 1=left, 2=up, 3=right, 4=down
	 * 
	 */
	protected int board[][];
	private int numH; //number of Humans
	private int numW; //number of Warriors
	protected ArrayList<Person> humans = new ArrayList<>();
	protected ArrayList<Zombie> zombies = new ArrayList<>();
	protected ArrayList<Warrior> warriors = new ArrayList<>();
	private int[] directions = {1, 2, 3, 4};
	private int width, height;


	/**
	 * Create a new City and fill it with buildings and Person(s).
	 * @param w: width of city
	 * @param h: height of city
	 * @param numB: number of buildings
	 * @param numH: number of Humans
	 */
	public City(int w, int h, int numB, int numH, int numW) {
		width = w;
		height = h;
		board = new int[w][h];
		this.numH = numH;
		this.numW = numW;

		randomBuildings(numB);
		populate(numH);
		infectCity();

	}

	/**
	 * Create a new City and fill it with buildings and Person(s).
	 * @param x: x location to add Zombie
	 * @param y: y location to add Zombie
	 */
	protected void addZombie(int x, int y) {
		
		if(checkCoordinateValidity(x, y)){

			System.out.println("Adding Zombie");
			int newDirection = directions[Helper.nextInt(4)] ;
			Zombie zombie = new Zombie(x, y, newDirection);
			zombies.add(zombie);
			board[x][y] = 3;
			zombie.draw();
		}
		
		
	}

	/**
	 * Generates numPerson random Person distributed throughout the city.
	 * Person must not be placed inside board!
	 *
	 * @param numPerson: the number of Person(s) to generate
	 */
	private void populate(int numPerson){
		//System.out.println("populate is working");
		// Generate numPerson new humans randomly placed around the city.
		int humansPlaced = 0;
		while(humansPlaced < numH) {
			//Get two random numbers
			int xCoord = Helper.nextInt(width); 
			int yCoord = Helper.nextInt(height);
			
			boolean placeHuman = checkCoordinateValidity(xCoord, yCoord);
			if(placeHuman) {
				int newDirection = directions[Helper.nextInt(4)];
				Person person = new Person(xCoord, yCoord, newDirection);
				//System.out.println("human placed: " + humansPlaced);
				humans.add(person);
				this.board[xCoord][yCoord] = 2;
				humansPlaced++;
			}
		}
		
	
	}
	
	/**
	 * Generates 1 Zombie to be placed in a random location outside buildings
	 */
	private void infectCity() {
		while(zombies.size() < 1) {
			int xCoord = Helper.nextInt(width); 
			int yCoord = Helper.nextInt(height);
			
			boolean placeZombie = checkCoordinateValidity(xCoord, yCoord);
			if(placeZombie) {
				int newDirection = directions[Helper.nextInt(4)];
				Zombie zombie = new Zombie(xCoord, yCoord, newDirection);
				zombies.add(zombie);
				this.board[xCoord][yCoord] = 3;
			}
			
		}
		
		
	}
	
	/**
	 * Generates 1 warrior to be placed in a random location outside buildings
	 */
	private void addWarrior() {
		int xCoord = Helper.nextInt(width); 
		int yCoord = Helper.nextInt(height);
		
		boolean placeWarrior = checkCoordinateValidity(xCoord, yCoord);
		if(placeWarrior) {
			int newDirection = directions[Helper.nextInt(4)];
			Warrior warrior = new Warrior(xCoord, yCoord, newDirection);
			this.board[xCoord][yCoord] = 4; //make sure the spot at that is set the warrior code
			warriors.add(warrior);
		}
		
		
	}
	
	/**
	 * Ensures coordinates of characters being placed are within the game map and outside buildings
	 * @param xCoord: x-coordinate to be checked
	 * @param yCoord: y-coordinate to be checked
	 */
	protected boolean checkCoordinateValidity(int xCoord, int yCoord) {
		if((xCoord < 0 || xCoord >= width) ||(yCoord < 0 || yCoord >= height) ) {
			return false;
		}
		
		
		if(board[xCoord][yCoord] == 1) {
			return false; //if board[x][y] == 1, a building is present, character cannot move in that direction
		} else {
			return true; //
		}
	}

	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width/6;
		int bldgMinSize = width/50;

		/* Produce a bunch of random rectangles and fill in the board array */
		for(int i=0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width); //initializes to some value between 0 and width 
			ty = Helper.nextInt(height); //initializes to some value between 0 and height 
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for(int r = ty; r < ty + th; r++) {
				if(r >= height)
					continue;
				for(int c = tx; c < tx + tw; c++) {
					if(c >= width)
						break;
					this.board[c][r] = 1;
				}
			}
		}
	}

	/**
	 * Updates the state of the city for a time step.
	 */
	public void update() {
		for(int i = 0; i < humans.size(); i++) {
			humans.get(i).updatePerson(this);
		}
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).updateZombie(this);
		}
		
		if(humans.size() < 25 && warriors.size() < numW) {
			addWarrior();
		}
		for(int i = 0; i < warriors.size(); i++) {
			warriors.get(i).updateWarrior(this);
		}
		
	}

	/**
	 * Draw the buildings and all characters.
	 */
	public void draw(){ 
		/* Clear the screen and make it black */
		ZombieSim.dp.clear(Color.black);
		drawboard();
		
		for(int i = 0; i < humans.size(); i++) {
			Person p = humans.get(i);
			p.draw();
		}
		
		for(int i = 0; i < zombies.size(); i++) {
			Zombie z = zombies.get(i);
			z.draw();
		}
		
		for(int i = 0; i < warriors.size(); i++) {
			Warrior w = warriors.get(i);
			w.draw();
		}
	}

	/**
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
	private void drawboard() {
		ZombieSim.dp.setPenColor(Color.GRAY);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(board[c][r] == 1)
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

}
