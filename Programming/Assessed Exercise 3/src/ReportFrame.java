import java.awt.*;
import javax.swing.*;
 
/**
 * Class to define window in which attendance report is displayed.
 */
 
 
public class ReportFrame extends JFrame{
	 private JTextArea display;
	 
     
	    /**
	     * constructor method used to initialise the FitnessProgram instance variable
	     * and add theJTextArea component to the window
	     *
	     * @param FitnessProgram
	     */
	    public ReportFrame (FitnessProgram program)
	    {
	        setDefaultCloseOperation(HIDE_ON_CLOSE);
	        setTitle("Attendance Report");
	        setSize(750, 250);
	        display = new JTextArea();
	        display.setFont(new Font("Courier", Font.PLAIN, 14));
	        add(display, BorderLayout.CENTER);
	        this.setLocation(400, 100);
	        buildReport(program);
	    }
	   
	   
	    /**
	     * method to build report for display on JTextArea
	     *
	     * @param FitnessProgram program
	     * @return String report
	     */
	    public void buildReport (FitnessProgram program)
	    {
	        FitnessClass fc [] = program.sortCourses();
	        String row = " ";   //String to store the report's rows
	        String overAvg = " ";   //String to store overall attendance average
	   
	        //Format top line
	        String report = String.format(" %-5s    %-18s   %-15s   %-20s   %-20s   %n" ,
	                "Id", "Class", "Tutor", "Attendances", "Average Attendance");
	         
	        String stars = String.format("**********************************************"
	                        + "********************************************** %n");
	       
	        report += stars;
	       
	        display.setText(report);
	       
	        for (int i = 0; i < fc.length; i++) //only consider existing classes
	        {
	            if (fc[i] != null)
	            {  
	                //format and fill row
	                row = String.format(" %-5s %-23s %-15s %-28s %-20.2f", fc[i].getClassID(),
	                        fc[i].getCourse(), fc[i].getTutor(), fc[i].attendColumn(), fc[i].getAvg());
	               
	                display.append(row + "\n");
	            }
	        }
	        display.append("\n");
	       
	        //display overall attendance average
	        overAvg = String.format("%65s", "Overall Average:    " + program.getOverallAttendanceAvg());
	        display.append(overAvg + "\n\n");
	    }  
	
}
