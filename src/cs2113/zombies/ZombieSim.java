package cs2113.zombies;

import cs2113.util.DotPanel;
import cs2113.util.Helper;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


/*
 * You must add a way to represent humans.  When there is not a zombie apocalypse occurring, humans
 * should follow these simple rules:
 * 		if (1 in 10 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space if not blocked by a wall
 *
 * We will add additional rules for dealing with sighting or running into zombies later.
 */

public class ZombieSim extends JFrame {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;

	
	public boolean spaceBarPressed = false;
	
	/* Define constants using static final variables */
	public static final int MAX_X = 200;
	public static final int MAX_Y = 200;
	private static final int DOT_SIZE = 3;
	private static final int NUM_HUMANS = 100;
	private static final int NUM_WARRIORS = 30;
	private static final int NUM_BUILDINGS = 50;



	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares to the screen.
	 */
	public ZombieSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Braaiinnnnnsss");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);
		
		/* Create our city */
		City world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS, NUM_WARRIORS);
		
		Container cPane = this.getContentPane();
		cPane.add(dp);
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		
		class SpaceBar implements KeyListener{

			@Override
			public void keyTyped(KeyEvent e) {
				
			
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				//get key code
				int id = e.getKeyCode();
				
				//if the code was 32(spacebar) then change the global variable
				if(id == 32) {
					spaceBarPressed = true;
					System.out.println("SPACEBAR PRESSED" );
				} else {
					spaceBarPressed = false;
				}
				
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		class MouseClick implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {

				//get the location of the where the mouse was clicked
				int x = (e.getX() / DOT_SIZE);
				int y = (e.getY() / DOT_SIZE);

				//add a zombie at that location
				world.addZombie(x, y);
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		this.addKeyListener(new SpaceBar()); //add keyListener to the JFrame
		dp.addMouseListener(new MouseClick()); //add the MouseListener
		
		this.setVisible(true);
		
		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having humans take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			world.update();
			// Draw to screen and then refresh
			world.draw();
			if(spaceBarPressed) {
					break;
			} else {
				dp.repaintAndSleep(30);
			}
			

		}
		
		/* Add the panel to the frame */
	

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		


	}
	
	public static void main(String[] args) {
		/* Create a new GUI window  */
		while(true) {
			new ZombieSim();
		}
		
	}

}
































