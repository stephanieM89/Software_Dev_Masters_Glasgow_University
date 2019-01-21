/**
 * Specific world class that extends the AbstractWorld method
 * and therefore inherits its methods. The class overwrites the 
 * abstract world rule methods.
 * @author Stephanie Man 2286857m
 */

public class WrapWorld extends AbstractWorld {
	/**
	 * WrapWorld constructor
	 * @param r Number of rows
	 * @param c Number of columns
	 */
	public WrapWorld(int r, int c){
		super(r, c);	
	}
	
	/**
	 * Method which returns true as in this world
	 * children will be placed elsewhere when a parent
	 * is at an edge
	 */
	public boolean placeChildElsewhere(){
		reproduce = true;
		return reproduce;
	}
	
	/**
	 * Method which returns a new row based on where
	 * the world places children of parents at the edge of the world
	 */
	public AbstractCreature[] newRow(int x, int z){
		// If the parent is at the top edge set the new row to the bottom
		if (x + z < 0){
			x = rows-1;
			return creatureArray[x];
		}
		// If the parent is at the bottom edge set the new row to the top
		else if (x+z > rows-1){
			x = 0;
			return creatureArray[x];
		}
		// If the parent is not at an edge return the existing row
		else{
			return creatureArray[x];
		}
	}
	
	/**
	 * Method to return the new Y coordinate of a child who's
	 * parent is at the edge of the world
	 */
	public int newY(int y){
		// If the parent is at the left of the world set the child to the right
		if ( y == 0){
			y = cols-1;
			return y;
		}
		// If the parent is at the right of the world set the child to the left
		else if ( y == cols-1){
			y = 0;
			return y;
		}
		// If the parent is not at an edge simply return the same value
		else{
			return y;
		}
	}
	
	/**
	 * Method to return the new X coordinate of a child who's
	 * parent is at the edge of the world
	 */
	public int newX(int x){
		// If the parent is at the top set the child to the bottom
		if ( x == 0){
			x = rows-1;
			return x;
		}
		// If the parent is at the bottom set the child to the top
		else if ( x == rows-1){ 
			x = 0;
			return x;
		}
		// If the parent is not at an edge then simply return the same value
		else{
			return x;
		}
	}
}
