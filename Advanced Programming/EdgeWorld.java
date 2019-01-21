/**
 * Specific world class that extends the AbstractWorld method
 * and therefore inherits its methods. The class overwrites the 
 * abstract world rule methods.
 * @author Stephanie Man 2286857m
 *
 */
public class EdgeWorld extends AbstractWorld {
	
	/**
	 * EdgeWorld constructor
	 * @param r Number of rows
	 * @param c Number of columns
	 */
	public EdgeWorld(int r, int c){
		super(r, c);	
	}
	
	/**
	 * Method which returns false as children should
	 * not be placed elsewhere in the EdgeWorld
	 */
	public boolean placeChildElsewhere(){
		reproduce = false;
		return reproduce;
	}
	
	/**
	 * Method to return the new row. In this world the new
	 * row is the same as the current row as children should not
	 * be placed elsewhere
	 */
	public AbstractCreature[] newRow(int x, int z){
		return creatureArray[x];
	}
	
	/**
	 * Returns the current Y coordinate as in this
	 * world the children should not be placed elsewhere
	 */
	public int newY(int y){
		return y;
	}

	/**
	 *  Returns the current X coordinate as in this 
	 *  world the children should not be placed elsewhere
	 */
	public int newX(int x){
		return x;
	}	
}
