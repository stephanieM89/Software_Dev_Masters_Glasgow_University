import java.awt.*;
import javax.swing.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
 
	/**
	 *   Will require FitnessProgram and JTextArea objects as instance variables
	 */
	
//	private FitnessProgram; 
	private JTextArea reportTextarea;
	
	/**
	 *  A constructor with a FitnessProgram parameter used to initialise the 
	 *  FitnessProgram instance variable and add the JTextArea component to
	 *  the window
	 */
	public ReportFrame()
	{
		//Create the JFrame
		setTitle("Attendance Report");
		setSize(1000,600);
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		reportTextarea = new JTextArea (100, 100);
		
		
		
	}
	
	
	
	
	
	
	
	/**
	 *  This method shows the attendance report
	 */
	
	public void showReport(){
		FitnessClass fc = new FitnessClass();
		fc.returnAttendanceReport();
	}
	
}