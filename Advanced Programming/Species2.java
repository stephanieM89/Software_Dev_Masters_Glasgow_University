/**
 * Specific creature species that extends the AbstractCreature method
 * and therefore inherits its methods. The class overwrites the 
 * abstract method creatNew.
 * @author Stephanie Man 2286857m
 *
 */
public class Species2 extends AbstractCreature{
	/**
	 * Species 2 constructor
	 * @param w World
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Species2(AbstractWorld w, int x, int y){
		// Defining the maximum life, fitness and type of species
		super(500, 0.4, 2, w, x, y);
	}

	/**
	 * Method to create new creatures of the same type
	 */
	public Species2 createNew(AbstractWorld world, int z, int w) {
		Species2 child = new Species2(world, z, w);
		return child;
	}
}
