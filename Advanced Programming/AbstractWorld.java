import java.util.concurrent.locks.ReentrantLock;

/**
 * This abstract class sets up the general world environment
 * @author Susie Smart: 2286107s
 *
 */
public abstract class  AbstractWorld {
	protected final int rows, cols;
	protected AbstractCreature[][] creatureArray;
	protected boolean reproduce;
	protected ReentrantLock printLock;

	/**
	 * AbstractWorld constructor
	 * @param r The number of rows in the world grid
	 * @param c The number of columns in the world grid
	 */
	public AbstractWorld(int r, int c){
		rows = r;
		cols = c;
		creatureArray = new AbstractCreature [rows][cols];
		printLock = new ReentrantLock();	
	}
	
	/**
	 * Method which returns a specific row of the AbstractCreature
	 * Array based on x coordinates
	 * @param x Original Parent X coordinate
	 * @param z X coordinate modifier
	 * @return An AbstractCreature array that represents one row of the world
	 */
	public AbstractCreature [] getRowofWorld(int x, int z){
			return creatureArray[x+z];	
	}
	
	/**
	 * Abstract method which will be overwritten in the classes which inherit from this one.
	 * @param x Original Parent X coordinate
	 * @param z X coordinate modifier
	 * @return An AbstractCreature array that represents the new row of the world
	 */
	public abstract AbstractCreature[] newRow(int x, int z);
	
	/**
	 * @return Number of Rows the world has
	 */
	public int getRows(){
		return rows;
	}
	
	/**
	 * @return Number of columns in the world
	 */
	public int getCols(){
		return cols;
	}
	
	/**
	 * Method which checks if the row is within the world boundaries
	 * @param x Original Parent X coordinate
	 * @param z X coordinate modifier
	 * @return True if the row is within the boundary, otherwise return false
	 */
	public boolean checkRow(int x, int z){	
		if (x+z > rows-1 || x+z < 0){	
			return false;		
		}
		else {
			return true;
		}
	}
	
	/**
	 * Method which checks if the column is within the world boundaries
	 * @param y Original Parent Y coordinate
	 * @param w Y coordinate modifier
	 * @return True if the column is within the boundary, otherwise return false
	 */
	public boolean checkCell(int y, int w){
		if (y+w > cols-1 || y+w < 0){
				return false;
		}
		else {
			return true;
		}	
	}

	/**
	 * Method which returns the value that the rows will be incremented by
	 * when deciding where children can be placed
	 * @return Integer increment value
	 */
	public int verticalRule(){
		int rowInc =-1;
		return rowInc;	
	}

	/**
	 * Method which returns the value that the columns will be incremented by
	 * when deciding where children can be placed
	 * @return Integer increment value
	 */
	public int horizRule(){
		int colInc = -1;
		return colInc;
	}
	
	/**
	 * Method which returns the number of times the rows will be incremented
	 * when deciding where children will be placed
	 * @return Integer limit value
	 */
	public int verticallimit(){
		int rowLimit =2;
		return rowLimit ;
	}

	/**
	 * Method which returns the number of times the columns will be incremented
	 * when deciding where children will be placed
	 * @return Integer limit value
	 */
	public int horizlimit(){
		int colLimit = 2;
		return colLimit;	
	}
	
	/**
	 * Abstract method will be defined in each class that inherits from this one.
	 * @return True or false depending on whether the children at an 
	 * edge should be placed elsewhere or not
	 */
	public abstract boolean placeChildElsewhere();
	
	/**
	 * Abstract method will be defined in each class that inherits from this one.
	 * @param y Original Parent Y Coordinate
	 * @return new Y Coordinate
	 */
	public abstract int newY(int y);
	
	/**
	 * Abstract method will be defined in each class that inherits from this one.
	 * @param x Original Parent X Coordinate
	 * @return new X Coordinate
	 */
	public abstract int newX(int x);
	
	/**
	 * Method which sets AbstractCreature to null in the array after it has died.
	 * @param t The dead AbstractCreature
	 */
	public void addDeath(AbstractCreature t){
		creatureArray[t.getX()][t.getY()] =  null;
	}
	
	/**
	 * Method which updated the world by adding new creatures to the 
	 * AbstractCreature array
	 * @param t The new AbstractCreature
	 */
	public void updateWorld(AbstractCreature t){
		creatureArray[t.getX()][t.getY()] =  t;	
	}
		
	/**
	 *  Method used to print out the gridArray represented as 1's and 2's
	 *  Method also counts the current total population and prints it.
	 */
	public void printGrid(){
		// Printing should occur only every 0.5 seconds so the thread is sent to sleep for this time
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		int j = 0;
		int i = 0;
		int count = 0;
		// Print new line to separate one grid from the other
		System.out.println("************************************");
		// For the number of rows
		while (j < rows){
			// For the number of columns
			while (i < cols){
				// If the cell of the array is not null then print the creatures type
				if (creatureArray[j][i] != null){
						System.out.print(" "+creatureArray[j][i].getType()+" ");
						count++;
				// If the cell is null print a dash		
				}
				else{
					System.out.print(" - ");
				}
				i++;
			}
				i = 0;
		System.out.println("");
			j++;
		}
		System.out.println("The total population is currently: "+count);
	}			
}

