import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/** Object for FitnessProgram */
	FitnessProgram fitnessprogram;
	
	/** Constant for course's earliest starting time 9 */
	private final int EARLIEST_STARTING_TIME = 9;
	
	/** Constant for the maximum number of classes 7 */
	private final int MAX_CLASSES = 7;

	
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		
		// REPHRASE CALL INPUT FILE METHODS
		fitnessprogram = new FitnessProgram();
		
		//REPHRASE CALL INITIAL TEXT WINDOW
		initLadiesDay();
		
		//REPHRASE CALL UPDATE
		updateDisplay();
		
		//DELETE TEST REPORT IS FUNCTIONING
		//report.setReport(program);	
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		
		//Reads in the ClassesIn.txt file
		String list="";
		try{
			FileReader filereader = new FileReader(classesInFile);
			Scanner input = new Scanner(filereader);
				while(input.hasNextLine()){
					list = list+ input.nextLine() + ""; //The text is stored as a String named list		
				}
				System.out.println(list);
				input.close();
		}
		catch(Exception e){
			System.out.println("Could not read file");	
		}
		
		//The String is split and stored in an array
		String [] text = list.split("[]+");
		FitnessClass fitnessobj;
		int a = 0;
		for(int i=0; i<text.length;i++){
			//Create the new fitnessclass object
			fitnessobj = new FitnessClass(text[a], text[a+1], text[a+2], Integer.parseInt(text[a+3]));
			//add this object to FitnessProgram
			fitnessprogram.addClass(fitnessobj, fitnessobj,getStartTime()-9);
			a = a+4;
		}
		
		//Call method to initialise class attendances
		initAttendances();
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
		try{
			FileReader filereader = new FileReader(attendancesFile);
			Scanner input = new Scanner(filereader);
//			System.out.print("" + input.nextLine());	//Check file can be read
			while(input.hasNextLine()){			
				String example = input.nextLine();
//				System.out.println(example);	
				String [] attendancearray = example.split(" +");
//				for (int i=0; i< attendancearray.length;i++)
//					System.out.println("Token "+i+" is: "+ attendancearray[i]);
			}
			input.close();
		}
		catch(Exception e){
		System.out.println("Could not find file");	
		}
	}
	

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
	    // your code here
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
//		FitnessProgram fprog = new FitnessProgram();
//		fprog.insertObject();
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
//		FitnessProgram fpg = new FitnessProgram();
//		fpg.deleteObject();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
		
	    ReportFrame rf = new ReportFrame();
	    rf.showReport();
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
	    // your code here
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    /*
	     *  If addButton clicked, the program should check if the class list is already full
	     *  & display a warning message if so.  Otherwise, take the input from the 3 textfields
	     *  idIn, classIn, tutorIn. If idIn matches one on the list - display warning & cancel
	     *  operation.  Otherwise, create a new Fitness Class with that ID class name & tutor & set
	     *  it's start time to the earliest available time.  Check class name and tutor fields are 
	     *  non-empty.  The set of 5 attendances for that class should be initialised to 0 (can't be changed after).
	     *  The "timetable" display in main GUI should be updated.  Newly-added classes should also feature in the 
	     *  Attendance Report (and should be included in for the purposes of computing the overall average attendance.
	     *  
	     */
		
		
		/* 
		 *  If deleteButton clicked, program should look for Class ID number in the 'idIn' textfield of GUI. A 
		 *  matching Fitness Class in the list should be searched for and if a match is found, the Class should be deleted 
		 *  from the list and the timetable display updated.  If no match is found, then a warning should be displayed.  
		 *  Deleted classes shouldn't feature in the Attendance Report (Update attendance report).
		 */
		
		
		/** Method to reset the Jtextfields to clear */
		public void reset(){
		
			
		}
	}
}
