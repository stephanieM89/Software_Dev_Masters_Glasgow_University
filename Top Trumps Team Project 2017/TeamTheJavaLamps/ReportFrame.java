import java.awt.*;
import javax.swing.*;

/**
 * Class to create ReportFrame showing game statistics
 */


public class ReportFrame extends JFrame 
{
	private JTextArea display;
	
       
	/**
	 * Constructor for ReportFrame
	 * Sets title, size, etc and calls buildReport() method 
	 * Takes the database as parameter
	 * 
	 * @param data		
	 */
	public ReportFrame (Data_Action data)
	{
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Game Statistics");
		setSize(920, 250);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		this.setLocation(400, 100);
		buildReport(data);
	}
	
	/** method to get data from the database and format them for the statistics report
	 *  the method takes an instance of the database as parameter
	 *  the method returns a String containing the full statistics report
	 *
	 * @param  data 	the database
	 * @return report+row	returns the full report with data as a String
	 * 
	 */
	public String buildReport (Data_Action data)
	{
		int gameNum = data.getGameNo();				// get the count of all the games from the database
		int compWins = data.getCompWins();			// gets the number of times the computer won from the database
		int humWins = data.getHumanWins();			// gets the number of times the user won from the database
		double avgDraws = data.getAvgDraws();		// gets the average number of draws from the database
		int rounds = data.getMaxRounds();			// gets the highest number of rounds played from the database
		
		String report = String.format("  %-8s	%-15s 	%-16s	%-16s	%-22s	%n" ,						// format the column names for report
				"Games", "Computer Wins", "Human Wins", "Average Draws", "Largest Number of Rounds");
	
		String stars = String.format("**********************************************"
				+ "********************************************************************* %n");

		report += stars;
		display.setText(report);
		
		String row = String.format("   %-16d %-23d %-23d %-26.2f %-22d", gameNum, 							// format the information retrieved from the database
				compWins, humWins, avgDraws, rounds);
		
		display.append(row + "\n");																			// add data to report
		
		return report + row;
		
		
	}

	
	
}