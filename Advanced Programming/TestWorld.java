import java.util.Random;

/**
 * This class is the main class. It initialises a world
 * and then adds a random amount of each species to random
 * cells on the world grid.
 * @author Stephanie Man 2286857m
 *
 */
public class TestWorld {

	public static void main(String[] args) {
		
		// Create new world object with the number of rows and columns as inputs
		WrapWorld test = new WrapWorld(10,10);
		// To run the EdgeWorld comment the above line out and include the line below.
		//EdgeWorld test = new EdgeWorld(10,10);

		// Initialise the world with a random number (between 1 and 7) of both species types
		Random r1 = new Random();
		int noSpecies1 = r1.nextInt(6)+1;
		int noSpecies2 = r1.nextInt(6)+1;
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		AbstractCreature currentChild;
		
		// Place the species1 creatures randomly on the world grid
		for (int i = 0; i < noSpecies1; i++){
			x1 = r1.nextInt(10);
			y1 = r1.nextInt(10);
			currentChild = new Species1(test, x1, y1);
			// update the world grid
			test.updateWorld(currentChild);
			// start the creatures thread
			currentChild.start();
		}
		
		// Place the species2 creatures randomly on the world grid
		for (int j = 0; j < noSpecies2; j++){
			x2 = r1.nextInt(10);
			y2 = r1.nextInt(10);
			currentChild = new Species2(test, x2, y2);
			// update the world grid
			test.updateWorld(currentChild);
			// Start the creatures thread
			currentChild.start();
		}
		
		// Print the world grid continuously
		while(true){
			test.printGrid();
		}
	}
}
