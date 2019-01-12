package cs2113.zombies;

import java.awt.Color;

import cs2113.util.Helper;

public class Warrior {
	//public static int numZombies = 0;
	protected int xPosition;
	protected int yPosition;
	protected int direction;
	
	
	protected Warrior(int xPosition, int yPosition, int direction) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;

	}
	
	public void updateWarrior(City city) {
		move(city);
	}
	
	private void killZombie(City City) {
		Zombie aZombie;
		int aHumanDirection = 0;
		
		if(City.checkCoordinateValidity(xPosition - 1,yPosition) && City.board[xPosition - 1][yPosition] == 3) {
			//Take Zombie away from Zombie ArrayList
			for(int k = 0; k < City.zombies.size(); k++) {
				aZombie = City.zombies.get(k);
				
				if(aZombie.xPosition == (xPosition-1) && aZombie.yPosition == (yPosition) ) { //we know the given Zombie
					aHumanDirection = aZombie.direction;
					City.zombies.remove(k);
					City.board[xPosition-1][yPosition] = 0;
					
					//Add Person to human ArrayList
					Person newHuman = new Person(xPosition - 1,yPosition, aHumanDirection);
					City.humans.add(newHuman);
					
					return;
				}
			}
			
		} else if(City.checkCoordinateValidity(xPosition,yPosition-1) && City.board[xPosition][yPosition - 1] == 3) {
			
			for(int k = 0; k < City.zombies.size(); k++) {
				aZombie = City.zombies.get(k);
				
				if((aZombie.xPosition == (xPosition)) && (aZombie.yPosition == (yPosition - 1)) ) { //we know the given human
					aHumanDirection = aZombie.direction;
					City.zombies.remove(k);
					City.board[xPosition][yPosition - 1] = 0;
					
					//Add zombie to Zombie ArrayList
					Person newHuman = new Person(xPosition,yPosition - 1, aHumanDirection);
					City.humans.add(newHuman);
					
					return;
				}
			}
		} else if(City.checkCoordinateValidity(xPosition + 1,yPosition) && City.board[xPosition + 1][yPosition] == 3) {
			
			for(int k = 0; k < City.zombies.size(); k++) {
				aZombie = City.zombies.get(k);
				
				if((aZombie.xPosition == (xPosition + 1)) && (aZombie.yPosition == (yPosition)) ) { //we know the given human
					aHumanDirection = aZombie.direction;
					City.zombies.remove(k);
					City.board[xPosition + 1][yPosition] = 0;
					
					//Add zombie to Zombie ArrayList
					Person newHuman = new Person(xPosition + 1,yPosition, aHumanDirection);
					City.humans.add(newHuman);
					
					return;
				}
			}
			
		} else if (City.checkCoordinateValidity(xPosition ,yPosition+1) && City.board[xPosition][yPosition + 1] == 3) {
			for(int k = 0; k < City.zombies.size(); k++) {
				aZombie = City.zombies.get(k);
				
				if((aZombie.xPosition == (xPosition)) && (aZombie.yPosition == (yPosition + 1)) ) { //we know the given human
					aHumanDirection = aZombie.direction;
					City.zombies.remove(k);
					City.board[xPosition][yPosition + 1] = 0;
					
					//Add Human to Human ArrayList
					Person newHuman = new Person(xPosition,yPosition + 1, aHumanDirection);
					City.humans.add(newHuman);
					
					return;
				}
			}
		 }
	}
	
	/**
	 * Allows the zombies to see if there are any humans in a certain direction 
	 *
	 * @param  warriorPosition: tracks the warriors position and makes adjustments prior to coordinate checking 
	 * @param City: City object currently being used 
	 */
	private int[] followZombie(int[] warriorPosition, City City) {
		switch(warriorPosition[0]) {
		case 1:  //direction is left
			for(int i = 1; i < 11; i++) {
				if(City.checkCoordinateValidity(xPosition - i, yPosition)) {
					if(City.board[xPosition - i][yPosition] == 3) {
						warriorPosition[1] = xPosition - 1;
						warriorPosition[2] = yPosition;
						warriorPosition[3] = 1;
						return warriorPosition;
					}
				}
			 }
			
		case 2: //direction is up 
			for(int i = 1; i < 11; i++) {
				if(City.checkCoordinateValidity(xPosition, yPosition-i)) {
					if(City.board[xPosition][yPosition-i] == 3) {
						warriorPosition[1] = xPosition;
						warriorPosition[2] = yPosition - 1;
						warriorPosition[3] = 1;
						return warriorPosition;
					}
				}
			 }
		
		case 3: //direction is right 
			for(int i = 1; i < 11; i++) {
				if(City.checkCoordinateValidity(xPosition+i, yPosition)) {
					if(City.board[xPosition + i][yPosition] == 3) {
						warriorPosition[1] = xPosition + 1;
						warriorPosition[2] = yPosition;
						warriorPosition[3] = 1;
						return warriorPosition;
					}
				}
			 }
			
		case 4: //direction is down 
			for(int i = 1; i < 11; i++) {
				if(City.checkCoordinateValidity(xPosition, yPosition+i)) {
					if(City.board[xPosition][yPosition + i] == 3) {
						warriorPosition[1] = xPosition;
						warriorPosition[2] = yPosition + 1;
						warriorPosition[3] = 1;
						return warriorPosition;
					}
				}	
			}
	}
		
		
		
		
		return warriorPosition;
		
	}
	
	/**
	 * Generates a random direction and moves one step in that direction
	 */
	private int[] moveRandomly(int[] warriorPosition) {
		//direction did not change, AKA there are no humans find a random direction and move there 
		direction = findRandomDirection();
		
		if(direction == 1) {
			warriorPosition[1] = xPosition - 1;
		} else if(direction == 2) {
			warriorPosition[2] = yPosition - 1;
		} else if (direction == 3) {
			warriorPosition[1] = xPosition + 1;
		} else if(direction == 4) {
			warriorPosition[2] = yPosition + 1;
		} 
		
		return warriorPosition;
		
	}
	
	/**
	 * Regulates all the actions that occur during movement
	 *
	 * @param City: City object currently being used 
	 */
	private void move(City City) {
		int[] newPosition = {direction, xPosition, yPosition, 0}; //a zero indicates the zombie is not following a human
		
		//Check if any Zombies can be killed
		killZombie(City);
		
		//check if any Zombies can be followed
		followZombie(newPosition, City);
		
		if(newPosition[3] == 0) { //a zombie is not being followed
			newPosition = moveRandomly(newPosition); //move randomly 
		}

		//if the newly calculated coordinate is not a building, chance the original x and y values adjust the numeric codes
		if(City.checkCoordinateValidity(newPosition[1], newPosition[2])) { 
			City.board[xPosition][yPosition] = 0;
			xPosition = newPosition[1];
			yPosition = newPosition[2];
			City.board[xPosition][yPosition] = 4; //
		}else {
			direction = findRandomDirection(); //Used from Phase solutions to avoid getting stuck at walls/edge of board
		}
	}
	
	/**
	 * Uses the Helper class to generate a random direction 30% of the time
	 */
	private int findRandomDirection() {
		//20% chance they face a random direction
		double chance = Helper.nextDouble();
		if(chance <= 0.30) {
			int newDirection = Helper.nextInt(4);
			if(newDirection == 0) {
				return 1;
			} else if(newDirection == 1) {
				return 2;
			}else if(newDirection == 2) {
				return 3;
			}else if(newDirection == 3) {
				return 4;
			}
		}
		
		//If the Warrior does not change directions then continue in the same direction
		return direction;
	}
	
	public void draw() {
		ZombieSim.dp.setPenColor(Color.BLUE);
		ZombieSim.dp.drawDot(xPosition, yPosition);
	}
	
	

}
