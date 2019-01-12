package cs2113.zombies;

import java.awt.Color;

import cs2113.util.Helper;

public class Zombie {
	//public static int numZombies = 0;
	protected int xPosition;
	protected int yPosition;
	protected int direction;
	
	
	protected Zombie(int xPosition, int yPosition, int direction) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;

	}
	
	/**
	 * Allows the zombies to see if there are any humans in a certain direction 
	 *
	 * @param zombiePosition: tracks the zombies position and makes adjustments prior to coordinate checking 
	 * @param City: City object currently being used 
	 */
	private int[] followHuman(int[] zombiePosition, City City) { 
			
			switch(zombiePosition[0]) {
				case 1:  //direction is left
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition - i, yPosition)) {
							if(City.board[xPosition - i][yPosition] == 2) {
								zombiePosition[1] = xPosition - 1;
								zombiePosition[2] = yPosition;
								zombiePosition[3] = 1;
								return zombiePosition;
							}
						}
					 }
					
				case 2: //direction is up 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition, yPosition-i)) {
							if(City.board[xPosition][yPosition-i] == 2) {
								zombiePosition[1] = xPosition;
								zombiePosition[2] = yPosition - 1;
								zombiePosition[3] = 1;
								return zombiePosition;
							}
						}
					 }
				
				case 3: //direction is right 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition+i, yPosition)) {
							if(City.board[xPosition + i][yPosition] == 2) {
								zombiePosition[1] = xPosition + 1;
								zombiePosition[2] = yPosition;
								zombiePosition[3] = 1;
								return zombiePosition;
							}
						}
					 }
					
				case 4: //direction is down 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition, yPosition+i)) {
							if(City.board[xPosition][yPosition + i] == 2) {
								zombiePosition[1] = xPosition;
								zombiePosition[2] = yPosition + 1;
								zombiePosition[3] = 1;
								return zombiePosition;
							}
						}	
					}
			}
		 
		return zombiePosition;
		
	}
	
	/**
	 * Generates a random direction and moves one step in that direction
	 */
	private int[] moveRandomly(int[] zombiePosition) {

		 //direction did not change, AKA there are no humans find a random direction and move there 
			direction = findRandomDirection();
			
			if(direction == 1) {
				zombiePosition[1] = xPosition - 1;
			} else if(direction == 2) {
				zombiePosition[2] = yPosition - 1;
			} else if (direction == 3) {
				zombiePosition[1] = xPosition + 1;
			} else if(direction == 4) {
				zombiePosition[2] = yPosition + 1;
			} 

		return zombiePosition;
	}
	
	/**
	 * Uses the Helper class to generate a random direction 20% of the time
	 */
	private int findRandomDirection() {
		//20% chance they face a random direction
		double chance = Helper.nextDouble();
		if(chance <= 0.20) {
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
		
		//If the Zombie does not change directions then continue in the same direction
		return direction;
	}
	
	public void updateZombie(City city) {
		move(city);
	}
	
	/** 
	 * Allows Zombies to kill humans and convert them into zombies
	 */
	private void eatBrains(City City){
			Person aHuman;
			int aHumanDirection = 0;
			
			if(City.checkCoordinateValidity(xPosition - 1,yPosition) && City.board[xPosition - 1][yPosition] == 2) {
				//Take human away from Human ArrayList
				for(int k = 0; k < City.humans.size(); k++) {
					aHuman = City.humans.get(k);
					
					if(aHuman.xPosition == (xPosition-1) && aHuman.yPosition == (yPosition) ) { //we know the adjacent human
						aHumanDirection = aHuman.direction;
						City.humans.remove(k); //remove this human
						City.board[xPosition-1][yPosition] = 0;
						
						//Add zombie to Zombie ArrayList
						Zombie newZombie = new Zombie(xPosition - 1,yPosition, aHumanDirection);
						City.zombies.add(newZombie);
						
						return;
					}
				}
				
			} else if(City.checkCoordinateValidity(xPosition,yPosition-1) && City.board[xPosition][yPosition - 1] == 2) {
				
				for(int k = 0; k < City.humans.size(); k++) {
					aHuman = City.humans.get(k);
					
					if((aHuman.xPosition == (xPosition)) && (aHuman.yPosition == (yPosition - 1)) ) { //we know the given human
						aHumanDirection = aHuman.direction;
						City.humans.remove(k);
						City.board[xPosition][yPosition - 1] = 0;
						
						//Add zombie to Zombie ArrayList
						Zombie newZombie = new Zombie(xPosition,yPosition - 1, aHumanDirection);
						City.zombies.add(newZombie);
						
						return;
					}
				}
			} else if(City.checkCoordinateValidity(xPosition + 1,yPosition) && City.board[xPosition + 1][yPosition] == 2) {
				
				for(int k = 0; k < City.humans.size(); k++) {
					aHuman = City.humans.get(k);
					
					if((aHuman.xPosition == (xPosition + 1)) && (aHuman.yPosition == (yPosition)) ) { //we know the given human
						aHumanDirection = aHuman.direction;
						City.humans.remove(k);
						City.board[xPosition + 1][yPosition] = 0;
						
						//Add zombie to Zombie ArrayList
						Zombie newZombie = new Zombie(xPosition + 1,yPosition, aHumanDirection);
						City.zombies.add(newZombie);
						
						return;
					}
				}
				
			} else if (City.checkCoordinateValidity(xPosition ,yPosition+1) && City.board[xPosition][yPosition + 1] == 2) {
				for(int k = 0; k < City.humans.size(); k++) {
					aHuman = City.humans.get(k);
					
					if((aHuman.xPosition == (xPosition)) && (aHuman.yPosition == (yPosition + 1)) ) { //we know the given human
						aHumanDirection = aHuman.direction;
						City.humans.remove(k);
						City.board[xPosition][yPosition + 1] = 0;
						
						//Add zombie to Zombie ArrayList
						Zombie newZombie = new Zombie(xPosition,yPosition + 1, aHumanDirection);
						City.zombies.add(newZombie);
						
						return;
					}
				}
			 }
	}

	/** 
	 * Allows Zombies to kill warriors and convert them into zombies
	 */
	private void destroyWarrior(City City){
		Warrior aWarrior;
		int aWarriorDirection = 0;
		
		if(City.checkCoordinateValidity(xPosition - 1,yPosition) && City.board[xPosition - 1][yPosition] == 4) {
			//Take human away from Human ArrayList
			for(int k = 0; k < City.warriors.size(); k++) {
				aWarrior = City.warriors.get(k);
				
				if(aWarrior.xPosition == (xPosition-1) && aWarrior.yPosition == (yPosition) ) { //we know the given warrior
					aWarriorDirection = aWarrior.direction;
					City.warriors.remove(k);
					City.board[xPosition-1][yPosition] = 0;
					
					//Add zombie to Zombie ArrayList
					Zombie newZombie = new Zombie(xPosition - 1,yPosition, aWarriorDirection);
					City.zombies.add(newZombie);
					
					return;
				}
			}
			
		} else if(City.checkCoordinateValidity(xPosition,yPosition-1) && City.board[xPosition][yPosition - 1] == 4) {
			
			for(int k = 0; k < City.warriors.size(); k++) {
				aWarrior = City.warriors.get(k);
				
				if((aWarrior.xPosition == (xPosition)) && (aWarrior.yPosition == (yPosition - 1)) ) { //we know the given warrior
					aWarriorDirection = aWarrior.direction;
					City.warriors.remove(k);
					City.board[xPosition][yPosition - 1] = 0;
					
					//Add zombie to Zombie ArrayList
					Zombie newZombie = new Zombie(xPosition,yPosition - 1, aWarriorDirection);
					City.zombies.add(newZombie);
					
					return;
				}
			}
		} else if(City.checkCoordinateValidity(xPosition + 1,yPosition) && City.board[xPosition + 1][yPosition] == 4) {
			
			for(int k = 0; k < City.warriors.size(); k++) {
				aWarrior = City.warriors.get(k);
				
				if((aWarrior.xPosition == (xPosition + 1)) && (aWarrior.yPosition == (yPosition)) ) { //we know the given warrior
					aWarriorDirection = aWarrior.direction;
					City.warriors.remove(k);
					City.board[xPosition + 1][yPosition] = 0;
					
					//Add zombie to Zombie ArrayList
					Zombie newZombie = new Zombie(xPosition + 1,yPosition, aWarriorDirection);
					City.zombies.add(newZombie);
					
					return;
				}
			}
			
		} else if (City.checkCoordinateValidity(xPosition ,yPosition+1) && City.board[xPosition][yPosition + 1] == 4) {
			for(int k = 0; k < City.warriors.size(); k++) {
				aWarrior = City.warriors.get(k);
				
				if((aWarrior.xPosition == (xPosition)) && (aWarrior.yPosition == (yPosition + 1)) ) { //we know the given warrior
					aWarriorDirection = aWarrior.direction;
					City.warriors.remove(k);
					City.board[xPosition][yPosition + 1] = 0;
					
					//Add zombie to Zombie ArrayList
					Zombie newZombie = new Zombie(xPosition,yPosition + 1, aWarriorDirection);
					City.zombies.add(newZombie);
					
					return;
				}
			}
		 }
}
	
	/**
	 * Regulates all the actions that occur during movement
	 *
	 * @param City: City object currently being used 
	 */
	private void move(City City) {
		int[] newPosition = {direction, xPosition, yPosition, 0}; //a zero indicates the zombie is not following a human
		
		//Check if any humans' brains can be eaten
		eatBrains(City);
		
		//check if any warriors can be destroyed
		destroyWarrior(City);
		
		//See if any humans can be followed
		newPosition = followHuman(newPosition, City);
		
		if(newPosition[3] == 0) { //if newPosition[3] == 0 then a human is not being followed 
			newPosition = moveRandomly(newPosition); //move randomly 
		}
	
		//if the newly calculated coordinate is not a building, change the original x and y values and adjust the numeric codes
		if(City.checkCoordinateValidity(newPosition[1], newPosition[2])) { 
			City.board[xPosition][yPosition] = 0;
			xPosition = newPosition[1];
			yPosition = newPosition[2];
			City.board[xPosition][yPosition] = 3; //
		} else {
			direction = findRandomDirection(); //Used from Phase solutions to avoid getting stuck at walls/edge of board
		}
		
	}

	
	public void draw() {
		ZombieSim.dp.setPenColor(Color.RED);
		ZombieSim.dp.drawDot(xPosition, yPosition);
	}
	

}	



