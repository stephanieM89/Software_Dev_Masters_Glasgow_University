import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;

/*
 * This class contains the main method.
 * The purpose is to build a Java program with a GUI which connects to the University
 * Gym database and allows the user to view course booking information from the database
 * and add course bookings to the database. 
 */

public class main {

	public static void main(String[] arg)
	{
		
		// This creates the objects for the GymBookingGUI and Dbconnection classes 
		
		Dbconnection dbconnection = new Dbconnection();
		GymbookingGUI gymbookinggui = new GymbookingGUI(dbconnection);
			
	}
}
