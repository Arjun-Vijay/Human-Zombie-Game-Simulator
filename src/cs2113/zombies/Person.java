package cs2113.zombies;

import java.awt.Color;

import cs2113.util.Helper;

public class Person {
	protected int xPosition;
	protected int yPosition;
	protected int direction;
	
	protected Person(int xPosition, int yPosition, int direction) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;

	}
	
	/**
	 * Allows the humans to see if there are any Zombies within 10 squares of their current direction
	 *
	 * personPosition identifies 3 numbers, personPosition[0] indicates direction, 
	 * personPosition[1] indicates their xPosition after turning
	 * personPosition[2] indicates their yPosition after turning
	 */
	private int[] runAway(int[] personPosition, City City) { 
			
			switch(personPosition[0]) { //compare against current direction
				case 1:  //direction is left
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition-i, yPosition)) {
							if(City.board[xPosition - i][yPosition] == 3) {
								personPosition[0] = 3;  //set person to face the right
								personPosition[1] = xPosition + 2; //move the person two spaces to the right 
								personPosition[2] = yPosition;
								//System.out.println("RUNNING AWAY");
								return personPosition;
							}
						}
					 }
					
				case 2: //direction is up 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition, yPosition-i)) {
							if(City.board[xPosition][yPosition-i] == 3) {
								personPosition[0] = 4;  //set person to face the right
								personPosition[1] = xPosition;
								personPosition[2] = yPosition + 2; //move the person two spaces down
								//System.out.println("RUNNING AWAY");
								return personPosition;
							}
						}
					 }
	
				
				case 3: //direction is right 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition+i, yPosition)) {
							if(City.board[xPosition + i][yPosition] == 3) {
								personPosition[0] = 1;  //set person to face the right
								personPosition[1] = xPosition - 2; //move the person two spaces to the left
								personPosition[2] = yPosition;
								//System.out.println("RUNNING AWAY");
								return personPosition;
							}
						}
					 }
					
				case 4: //direction is down 
					for(int i = 1; i < 11; i++) {
						if(City.checkCoordinateValidity(xPosition, yPosition+i))
						if(City.board[xPosition][yPosition + i] == 3) {
							personPosition[0] = 2;  //set person to face the right
							personPosition[1] = xPosition;
							personPosition[2] = yPosition - 2; //move the person two spaces to the left
							//System.out.println("RUNNING AWAY");
							return personPosition;
						}
					 }
			}

		return personPosition;
		
	}
	
	public void updatePerson(City city) {
		move(city);
	}
	
	/**
	 * Generates a random direction and moves one step in that direction
	 */
	private int[] moveRandomly(int[] personPosition) {
		direction = findRandomDirection();

		
		if(direction==1) {
			//System.out.println("Left ");
			personPosition[1]= xPosition - 1;
		} else if (direction==2) {
			//System.out.println("Up ");
			personPosition[2] = yPosition - 1;
		} else if (direction==3) {
			//System.out.println("Right");
			personPosition[1] = xPosition + 1;
		} else if(direction==4) {
			//System.out.println("Down");
			personPosition[2] = yPosition + 1;
		}
		
		return personPosition;
		
	}
	
	/**
	 * Regulates all the actions that occur during movement
	 *
	 * @param City: City object currently being used 
	 */
	private void move(City City) {
		int[] newPosition = {direction, xPosition, yPosition};
		
		//Run away from Zombies
		newPosition = runAway(newPosition, City);
		
		if(newPosition[0] == direction) { //if the direction did not change, AKA there's no zombie within 10 squares, moveRandomly
			newPosition = moveRandomly(newPosition);
		}
		
		//if the newly calculated coordinate is not a building, change the original x and y values adjust the numeric codes
		if(City.checkCoordinateValidity(newPosition[1], newPosition[2])) { 
			City.board[xPosition][yPosition] = 0;
			xPosition = newPosition[1];
			yPosition = newPosition[2];
			City.board[xPosition][yPosition] = 2;
		} else {
			direction = findRandomDirection(); //Used from Phase solutions to avoid getting stuck at walls/edge of board
		}	
	}

	public void draw() {
		ZombieSim.dp.setPenColor(Color.WHITE);
		ZombieSim.dp.drawDot(xPosition, yPosition);
	}
	
	/**
	 * Uses the Helper class to generate a random direction 10% of the time
	 */
	private int findRandomDirection() {
		//check to see if there is a zombie within 10 squares of current direction
		
		//10% chance they face a random direction. 
		double chance = Helper.nextDouble();
		if(chance <= 0.10) {
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
		
		return direction; //if a random direction was not generated then simply return the direction they faced before
	}

}	


