// This GUI class 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class GymbookingGUI extends JFrame implements ActionListener
{
	// Creates the instance variables which are the components
	private JPanel toppanel,panel2,panel3,panel4;
	private JButton placeBooking, viewCourseInfo,viewMemBooked,viewMembers;
	private JTextField tfMembershipnum,tfCourseid,tfInstructor,tfMaxcapacity,tfNumbers;
	private JLabel bookinglabel, courseLabel, membershipnum, courseid,instructorLabel, maxcapacityLabel,membersbooked,selectcourse,viewMembersCourses;
	private JComboBox courseComboBox, memberComboBox,gymcourseComboBox;
	private JTextArea textArea;
	private Dbconnection dbconnection;
	
	//the constructor adds all the components to the frame
	public GymbookingGUI(Dbconnection database)
	{
		dbconnection = database;
		
		//Create the JFrame for the GUI
		setTitle("Gym Course Bookings");
		setSize(1000,550);
		setLocation(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create the top panel with label
		toppanel= new JPanel();
		GridLayout grid = new GridLayout(4,4);
		bookinglabel = new JLabel("Book member on a course");	
		toppanel.add(bookinglabel);
		add(toppanel,"North");

		/*
		 * Create the second panel with JCombo boxes. The courseComboBox allows user to select a course from which the 
		 * course information can be obtained and select a course for booking.  The memberComboBox allows user to 
		 * select a member to book into a course. 
		 */
		
		panel2 = new JPanel(grid);
		courseComboBox = new JComboBox(dbconnection.getGymcourses());
		courseComboBox.setSelectedIndex(0);
		courseComboBox.addActionListener(this);
		memberComboBox = new JComboBox(dbconnection.getMembershipnum());
		memberComboBox.setSelectedIndex(0);
		memberComboBox.addActionListener(this);
		gymcourseComboBox = new JComboBox(dbconnection.getGymcourses());
		gymcourseComboBox.setSelectedIndex(0);
		gymcourseComboBox.addActionListener(this);
		courseLabel = new JLabel("Gym Courses:");
		membershipnum= new JLabel("Membership number:  ");
		placeBooking = new JButton("Place Booking");
		placeBooking.addActionListener(this);	
		panel2.add(courseLabel);
		panel2.add(courseComboBox);
		panel2.add(membershipnum);
		panel2.add(memberComboBox);
		panel2.add(placeBooking);
		add(panel2,"West");
		
		/*
		 * This 3rd panel allows user to view instructor name, maximum course capacity and
		 * numbers booked on course when the a course is selected from the courseComboBox.
		 */
		
		JPanel panel3 = new JPanel();
		JLabel viewcourselabel = new JLabel("Course information: ");
		JLabel instructorLabel = new JLabel ("Instructor: ");
		JLabel maxcapacityLabel = new JLabel ("Max capacity: ");
		JLabel membersbooked = new JLabel("Numbers booked on course: ");
		tfInstructor = new JTextField(15);
		tfMaxcapacity = new JTextField(2);
		tfNumbers = new JTextField(2);
		tfInstructor.addActionListener(this);
		tfMaxcapacity.addActionListener(this);
		tfNumbers.addActionListener(this);
		tfInstructor.setEditable(false);
		tfMaxcapacity.setEditable(false);
		tfNumbers.setEditable(false);
		panel3.add(viewcourselabel);
		panel3.add(instructorLabel);
		panel3.add(tfInstructor);
		panel3.add(maxcapacityLabel);
		panel3.add(tfMaxcapacity);
		panel3.add(membersbooked);
		panel3.add(tfNumbers);
		add(panel3);
		
		/*
		 * 4th panel allows user to view members booked on a course in a JTextArea
		 * by selecting the courses from the gymcourseComboBox in the 2nd panel.
		 */ 
		
		panel4 = new JPanel();
		viewMembersCourses = new JLabel("View members booked on a course");
		viewMembers = new JButton();
		textArea = new JTextArea(20,50);
		panel4.add(viewMembersCourses);
		panel4.add(gymcourseComboBox);	
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel4.add(scrollPane);
		textArea.setEditable(false);
		add(panel4,"South");

		setVisible(true);		
	}
	
	//Creates action listener events for the GUI
	public void actionPerformed(ActionEvent e)
	{
		String selection = "";
		
		/* When a course is selected in the JComboBox, it will update the textfields with
		 * the instructor name, maximum capacity of the course and the number of members 
		 * already booked onto the course.
		 */
		
		
		if (e.getSource() ==courseComboBox) 
		{
			//Shows the instructor taking the course. The method getInstructorname is called from the Dbconnection class
			selection = courseComboBox.getSelectedItem().toString();
			String instructor = dbconnection.getInstructorname(selection);
			tfInstructor.setText(instructor);
//			System.err.println("test123");
			
			//Shows the the maximum capacity. The method getMaxcapacity is called from the Dbconnection class
			selection = courseComboBox.getSelectedItem().toString();
			int capacity = dbconnection.getMaxcapacity(selection);
			tfMaxcapacity.setText("" + capacity);
//			System.err.println("test123");

//			//Shows the number of members already booked in field
			selection = courseComboBox.getSelectedItem().toString();
			int numbers = dbconnection.getNumbers(selection);
			tfNumbers.setText(""+ numbers);
//			System.err.println("test456");
	
		}
		
		
		/* When an event occurs in this ComboBox, it will retrieve a list of 
		 * members from the getCoursemembers method in the Dbconnection class 
		 */
		if(e.getSource()==memberComboBox)
		{	
			String membershipid = memberComboBox.getSelectedItem().toString();
			int memid = Integer.parseInt(membershipid);
//			System.err.println("Test: memid" + memid);
		}
		
		
		/* When an event occurs in this ComboBox, it will retrieve a list of 
		 * courses from the getCoursemembers method in the Dbconnection class 
		 * and update the textArea with the members that are booked onto the course.
		 */
		if(e.getSource() == gymcourseComboBox)
		{
				String gymCourse = gymcourseComboBox.getSelectedItem().toString();
				textArea.setText(dbconnection.getCoursemembers(gymCourse));
		}
		
		
		/*
		 *  When this button is clicked, the methods from the DBconnection class will be passed through
		 *  to enable the user to place a booking for a member on a course.
		 */
		if(e.getSource() == placeBooking) 
		
		{	
			String courseselection = courseComboBox.getSelectedItem().toString();
			System.err.println("Test: course" + courseselection);
				
			String membershipid = memberComboBox.getSelectedItem().toString();
			int memid = Integer.parseInt(membershipid);
			System.err.println("Test: member" + memid);

			dbconnection.getCourseID(selection);
			dbconnection.updateMemberbooking(selection, memid);
			int store = dbconnection.getNumbers(courseselection);	
			System.err.println(""+store);
			tfNumbers.setText(""+ store);
	
		}
	}
}
		
		
		
		

