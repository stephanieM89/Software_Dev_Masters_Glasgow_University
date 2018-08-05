import java.io.*;
import java.util.Scanner;

/**
 * The main class
 */
public class AssEx3 {
	/**
	 * The main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SportsCentreGUI display = new SportsCentreGUI();
		display.setVisible(true);
		
		
		
		
		
		//This method openes the file ClassesIn.txt
		display.initLadiesDay();
		//Check method opens attendance file
//		display.initAttendances();
		
		//Create FitnessClass object
		FitnessClass fitnessclass = new FitnessClass();
		
		
		//Create FitnessProgram object
		FitnessProgram fitnessprogram = new FitnessProgram();
//		fitnessprogram.getLine();
		
		
		//Add fitnessclass objects
//		fitnessprogram.add(fitnessclass);
	}
		}


	

	
		
	
	
