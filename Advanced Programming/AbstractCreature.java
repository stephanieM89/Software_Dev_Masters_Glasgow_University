import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The AbstractCreature class sets up the general creature methods
 * @author Stephanie Man: 2286857m
 *
 */
public abstract class AbstractCreature extends Thread{
	
	protected final int maxLife, myXPosition, myYPosition, type;
	protected final double fitness;
	protected AbstractWorld theWorld;
	protected AbstractCreature[] tempRow;
	protected AbstractCreature child;
	protected ReentrantLock worldLock;
	
/**
 * AbstractCreature constructor
 * @param m Maximum life span for the creature
 * @param f Fitness of each creature
 * @param t Type of the creature
 * @param w The AbstractWorld that the creature belongs too
 * @param x The X coordinate of the creature
 * @param y The Y coordinate of the creature
 */
	public AbstractCreature(int m, double f, int t, AbstractWorld w, int x, int y){	
		// Initialise the instance variables
		maxLife = m;
		fitness = f;
		type = t;
		theWorld = w;
		myXPosition = x;
		myYPosition = y;
		worldLock = new ReentrantLock();
	}
	
	/**
	 * Abstract method which each species will overwrite with their own
	 * method for creating a new species of their own type.
	 * @param world The world that the creature belongs too
	 * @param z The modified X coordinate
	 * @param w The modified Y coordinate
	 * @return
	 */
	public abstract AbstractCreature createNew(AbstractWorld world, int z, int w);
	
	/**
	 * Method to create a random life span up to the maximum life
	 * @return Random integer between 0 and max life
	 */
	public int getLife(){
		Random r = new Random();
		int x = r.nextInt(maxLife);
		int lifeSpan = x;
		return lifeSpan;
	}

	/**
	 * @return The creatures fitness
	 */
	public double getFitness(){
		return fitness;
	}
	
	/**
	 * @return The creatures type
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * @return The creatures X coordinate
	 */
	public int getX(){
		return myXPosition;
	}
	
	/**
	 * @return The creatures Y coordinate
	 */
	public int getY(){
		return myYPosition;
	}

	/**
	 * The run method that must be implemented as this
	 * class extends thread. The method puts the thread
	 * to sleep for the life span of the creature. It 
	 * then calls methods to kill the creature and reproduce
	 * at the end of its life.
	 */
	public void run(){
		try{
			// sleep for life span
			sleep(getLife());	
		}
		catch(InterruptedException e){}
		// Make current cell null as creature has reached the end of its life
		theWorld.addDeath(this);
		// Check cells then reproduce
		checkRows();
	}
	
	/**
	 *  Method which checks rows and calls method to check columns
	 *  to determine where the adult creature will reproduce
	 */
	public void checkRows(){
		// Check with world how many cells can be reproduced in
		int rowLimit = theWorld.verticallimit();
		int rowInc = theWorld.verticalRule();
		int colLimit = theWorld.horizlimit();
		int colInc  = theWorld.horizRule();
		// Boolean that indicates whether the row should be changed if the world rules dictate that 
		// the creatures can wrap around the world
		boolean changeRow = false;
	
		// For the number of rows that the world allows one creature to reproduce in check the rows.
		while (rowInc<rowLimit){	
			// check that row is not out of bounds
			if (theWorld.checkRow(myXPosition, rowInc) == true){
				// Set the temp row to the current row of the world and check each cell in that row
				tempRow = theWorld.getRowofWorld(myXPosition, rowInc);
				changeRow = false;
				checkCols(colInc, colLimit, rowInc, changeRow);
			}
			// Check that the row is out of bounds and if the world rule says to place the children elsewhere
			// then set the temp row to the new row as per the world rules, then check each cell in that row
			else if (theWorld.placeChildElsewhere() == true){
				tempRow = theWorld.newRow(myXPosition, rowInc);
				changeRow = true;
				checkCols(colInc, colLimit, rowInc, changeRow);
			}
				// reset the column modifier
				colInc  = theWorld.horizRule();
				// Increase the row modifier
				rowInc++;
			}		
	}

	
	/**
	 * Method which checks the cells of the current row 
	 * to determine whether an adult creature will reproduce
	 * @param w The column modifier
	 * @param colLimit The number of columns the world allows one creature to reproduce in
	 * @param z The row modifier
	 * @param changeRow The boolean which indicates whether a new row is required as adult creature at boundary
	 */
	public void checkCols(int w, int colLimit, int z, boolean changeRow){
		
		// For the number of columns the world allows one species to reproduce in
		while( w<colLimit){
			// If the row and the column are within the world boundary
			if (theWorld.checkCell(myYPosition, w) == true && changeRow == false){
				// Place children in the new position
				reproduce(myXPosition,z,myYPosition,w);
				w++;
			}
			// If the row is within the boundary but the column is not
			// Check the world rules to determine whether a child should be placed elsewhere
			else if (theWorld.checkCell(myYPosition, w) == false && theWorld.placeChildElsewhere() == true && changeRow == false){
				// Check the world rules to determine where to place the child
				reproduce(myXPosition,z,theWorld.newY(myYPosition),0);
				w++;
			}
			// If the column is within the boundary but the row is not
			// Check the world rules to determine whether a child should be placed elsewhere
			else if (theWorld.checkCell(myYPosition, w) == true && theWorld.placeChildElsewhere() == true && changeRow == true){
				// Check the world rules to determine where to place the child
				reproduce(theWorld.newX(myXPosition),0,myYPosition,w);
				w++;
			}
			// If neither the row or column are within the boundary i.e at a corner
			// Check the world rules to determine whether a child should be placed elsewhere
			else if (theWorld.checkCell(myYPosition, w) == false && theWorld.placeChildElsewhere() == true && changeRow == true){
				// Check the world rules to determine where to place the child
				reproduce(theWorld.newX(myXPosition),0,theWorld.newY(myYPosition),0);
				w++;
			}
			// The child should not be placed elsewhere so do not reproduce
			else{
				w++;
			}
		}	
	}
	
	
	/**
	 * Method that actually reproduces new species after the checks have been made
	 * @param x Parent X coordinate
	 * @param z X coordinate modifier
	 * @param y Parent Y coordinate
	 * @param w Y coordinate modifier
	 */
	public void reproduce(int x, int z, int y, int w){
		// If there is nothing in your neighbouring cells then reproduce at fitness probability
		if(tempRow[y+w] == null && Math.random()<=fitness){
				// Create a new creature of your species type
				child = this.createNew(theWorld, x+z, y+w);
				// Add the children to the world
				addChildren();
			}
		// if there is a different species in your neighbour then battle for population depending on fitness
		else if ( tempRow[y+w] != null && tempRow[y+w].getType() != type  && Math.random()<=fitness-tempRow[y+w].getFitness()){ 
			// kill previous thread
			tempRow[y+w].interrupt();
			// Create a new creature of your species type
			child = this.createNew(theWorld, x+z, y+w);
			// Add the children to the world
			addChildren();
		}
	}
	
	/**
	 * Method which updates the world with newly born children.
	 * A lock is required so that more than one thread cannot
	 * alter the world object at the same time.
	 * The method also starts the new children threads running.
	 */
	public void addChildren(){
		worldLock.lock();
		try {
			theWorld.updateWorld(child);
		}
		finally {
			worldLock.unlock();
			// Start child thread running
			child.start();
		}
	}
}

