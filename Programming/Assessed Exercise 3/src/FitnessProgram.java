import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
    	
	private final int MAX_CLASSES = 7;	// The constant represents maximum number of classes
	private final int EARLIEST_STARTING_TIME = 9; //The constant represents the earliest starting time for a class
	private final int WEEKS = 5;
	private FitnessClass [] fitnessclasses;  // The instance variable represents the array of FitnessClass objects
	private int numberClasses; 				//The instance variable represents the number of non-null objects in the list

	// Default constructor to instantiate the array
	//	Entry X of the array should contain the Fitness Class starting at time 9+X (or null if time slot is free)
	public FitnessProgram()
	{
		fitnessclasses = new FitnessClass[MAX_CLASSES]; 
		numberClasses = 0;
	}
	
	/**
	 *  The lines from the ClassesIn.txt is passed into this method, and in turn creates a
	 *  FitnessClass object (have a FitnessClass constructor that takes a String parameter.
	 *  This can then be tokenised within the constructor to work out the start time and other details. 
	 *  The method can then get the start time from the FitnessClass object jsut created to work out
	 *  where it should be stored in the array
	 */
	
	
	/** Populate the FitnessClass array by reading each line at a time from
	 * ClassesIn.txt from within initLadiesDay 
	 */
//	public String getLine(String classtext){
//////		SportsCentreGUI gui = new SportsCentreGUI();
//////		gui.initLadiesDay();
////		String line = "";
////		list =classtext;
//		while(line.hasNextLine()){
//			list = input.nextLine();
//			System.out.println(line);
//		
//		return list;
//	}

	/**
	 *  A method to populate the attendance lists for a given Fitness Class in the array, given
	 *  a String representing a single line of AttendancesIn.txt as a parameter
	 */
	
	
	/**
	 * . A method to return the FitnessClass object at index x
	 */
	
	
	
	/**
	 *  A method to return the FitnessClass starting at a particular time (or null of no such class exists)
	 * 
	 */
	
	
	/**
	 *  A method to return the first start time that is available for a class 
	 */
//	public int getStarttime() {return startTime;}
	
	
	/**
	 * 	A method to return a FitnessClass object with a given ID number in the array (or null is not present)
	 */
	
	
	/**
	 *  Method to add a FitnessClass object to the list.  This should check if the class list is already full and display
	 *  a warning message if so.  The classID should be checked and if it matches the list, cancel operation.  Otherwise,
	 *  create a new fitness class and set it's start time to the earliest available time.
	 */
	public void addClass(FitnessClass f, int a){
//
		fitnessclasses[a] = f;
		numberClasses++;
//		if (numberClasses>MAX_CLASSES){ DO ELSEWHERE
//			JOptionPane.showMessageDialog(null, "Class list is full", "Add Class Error", JOptionPane.ERROR_MESSAGE);
//			}
//		else if (fitnessclasses[i] != null && (fitnessclasses[i].getClassid().equals(classid)))
	}
	
	/**
	 *  Method to delete a FitnessClass object from the list.  This checks that the classID entered in the GUI textfield 
	 *  matches a fitness class in the list and if a match is found, the class should be deleted.  
	 *  If no match found, display warning message.
	 */
	public void deleteClass(String classid)
	{
		for (int i=0; i<MAX_CLASSES;i++)
			if (fitnessclasses[i] != null && (fitnessclasses[i].getClassid().equals(classid)))
				{
				fitnessclasses[i] = null;
				numberClasses --;
				}
			else
			{
				JOptionPane.showMessageDialog(null, "Class cannot be found", "Delete Class Error", JOptionPane.ERROR_MESSAGE);
			}
	}
	
	/**
	 * 	This method returns a list sorted in descending order on the Average Attendance
	 *  at each class.  (Use the arrays.sort method)
	 */
	public void averageAttendanceSort(int []averageAttendance)
	{
		int len = averageAttendance.length;
		for (int i=0; i<len; i++)
		{
			//find the maximum element amount entries
			int max = i; //start with first as max
			for( int j = i+1; j<len;j++)
			{
				if (averageAttendance[j] > averageAttendance[max]) 
					max = j; //this one is larger
			}
			//swap entries i and max of averageAttendance
			int temp = averageAttendance[i];
			averageAttendance[i] = averageAttendance[max];
			averageAttendance[max]=temp;
		}
	}
	
	
	/**
	 * 	This method calculates the overall average of the Average Attendances
	 * @param attendAverage
	 * @return overallAverage
	 */
	
	public double getOverallaverage(double[] averageAttendance)
	{
		double overallAverage = 0;  //Represents the overall average of the Average Attendances
		int totalaverage = 0; //Represents the total of the Average Attendances
		
		for(int i=0; i<WEEKS; i++) //Loop the Average Attendances array to calculate total
		{
			totalaverage += averageAttendance[i]; 
			i++;
		}
		overallAverage = (double)totalaverage/WEEKS; 	//Calculate the average by dividing the total by 5
		return overallAverage;
	}
	
}
