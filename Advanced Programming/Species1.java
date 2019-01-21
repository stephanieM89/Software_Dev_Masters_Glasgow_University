/**
 * Specific creature species that extends the AbstractCreature method
 * and therefore inherits its methods. The class overwrites the 
 * abstract method creatNew().
 * @author Stephanie Man 2286857m
 *
 */
public class Species1 extends AbstractCreature {
	/**
	 * Species 1 constructor
	 * @param w World
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Species1(AbstractWorld w, int x, int y){
		// Defining the maximum life, fitness and type of species
		super(1000, 0.8, 1, w, x, y);
	}
	
	/**
	 * Method to create new creatures of the same type
	 */
	public Species1 createNew(AbstractWorld world, int z, int w) {
		Species1 child = new Species1(world, z, w);
		return child;
	}
}
