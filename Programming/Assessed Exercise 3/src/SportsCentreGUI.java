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
   
    /** FitnessProgram object */
    FitnessProgram program;
   
    /** class constant: earliest start time for a course */
    private final int EARLIEST_START_TIME = 9;
   
    /** class constant: maxNum of classes: 7 */
    private final int MAX_CLASSES = 7;
 
    /** Names of input text files */
    private final String classesInFile = "ClassesIn.txt";
    private final String classesOutFile = "ClassesOut.txt";
    private final String attendancesFile = "AttendancesIn.txt";
   
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
   
        program = new FitnessProgram();
        initLadiesDay();
        updateDisplay();
    }
   
 
    /**
     * Creates the FitnessProgram list ordered by start time
     * using data from the file ClassesIn.txt
     */
   
    public void initLadiesDay()
    {
        String line = "";
       
        try
        {
        //read in file
        FileReader fr = new FileReader(classesInFile);
        Scanner in = new Scanner(fr);
       
            while (in.hasNext())
            {
                //store document content in String
                line = line + in.next() + " ";     
            }
           
            in.close();
        }
       
        catch (Exception e)
        {
            System.err.println(e);
        }
       
       
    // split String content and store in array
        String [] content = line.split("[ ]+");    
        FitnessClass fClass;
        int count = 0;
       
        //loop through document and divide by 4 because each course
        //is made up of 4 elements (ID, name, tutor, time)
        for(int i = 0; i < content.length / 4; i++)
        {
            //create new Fitness Class object
            fClass = new FitnessClass(content[count], content[count+1],
                    content[count+2], Integer.parseInt(content[count+3]));
            //add to program
            program.addClass(fClass, fClass.getTime() - 9);                    
            count = count + 4;     
        }      
       
        initAttendances();
    }
 
    /**
     * Initialises the attendances using data
     * from the file AttendancesIn.txt
     */
    public void initAttendances()
    {
        String attendances = "";
       
        try
        {
        //read in file
        FileReader fr = new FileReader(attendancesFile);
        Scanner in = new Scanner(fr);
       
            while (in.hasNext())
            {
                //store file data in String
                attendances = attendances + in.next() + " ";   
            }
           
            in.close();
        }
       
        catch (Exception e)
        {
            System.err.println(e);
        }
       
       
    // split String content and store in array
        String [] contents = attendances.split("[ ]+");
        int one, two, three, four, five;    //for the five weeks that are being monitored
        int count = 0;
           
        //a line in AttendancesIn.txt has 6 elements, so loop while there are lines
        for (int i = 0; i < (contents.length)/6; i++)  
        {
            one = Integer.parseInt(contents[count + 1]);
            two = Integer.parseInt(contents[count + 2]);
            three = Integer.parseInt(contents[count + 3]);
            four = Integer.parseInt(contents[count + 4]);
            five = Integer.parseInt(contents[count + 5]);
           
            int [] attendArray = {one, two, three, four, five};
            program.searchByID(contents[count]).setAttendance(attendArray);
           
            //increase count by 6 to jump to new line of attendances
            count = count + 6;     
        }          
    }
 
    /**
     * Instantiates timetable display and adds it to GUI
     */
    public void updateDisplay()
    {
        //top row of display showing the time slots
        String top = String.format("  %-10s   %-10s   %-10s   %-10s   %-10s   %-10s   %-10s %n %n",
                "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");
       
        display.setText(top);
       
        FitnessClass[] fcTable = program.getCourses();
        String courseNames = " ";   //2nd row of display showing the courses
        String tutorNames = " ";    //3rd row of display showing the instructors
        String whiteSpace = " ";
       
       
       for (int i = 0; i < 7; i++)  //loop through all available time slots
       {
           if (fcTable[i] != null)  //if course at index exists, display course name and tutor
           {
               courseNames = courseNames + String.format("%-13s",fcTable[i].getCourse());
               tutorNames = tutorNames + String.format("%-13s", fcTable[i].getTutor());
           }
           
         //if no course at index, display "Available" and leave instructor empty
           else if (fcTable[i] == null)
           {
               courseNames = courseNames + String.format("%-13s", "Available");
               tutorNames = tutorNames + String.format("%-13s", whiteSpace);
           }
       }
       
      courseNames = courseNames + "\n";
      display.append(courseNames);
      display.append(tutorNames);
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
    public void processAdding()
    {
        //get data from textfields
        String classID = idIn.getText();
        String className = classIn.getText();
        String tutorName = tutorIn.getText();
       
        //get earliest time available
        int time = program.getFirstAvailable();
       
        //check fields are not empty
        if (classID.equals("") || className.equals("") || tutorName.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please make sure to fill out all the fields!");
        }
       
        //check if entered class id already exists
        else if (program.courseExists(classID))
        {
            JOptionPane.showMessageDialog(null, "Course ID already exists!");
        }
       
        //check if there is an available space
        else if (time == -1)
        {
            JOptionPane.showMessageDialog(null, "The timetable is already full!");
        }
       
        //fields are not empty, class does not exist, space available => add
        else   
        {
            //Create new fitness class object
            FitnessClass fc = new FitnessClass(classID, className, tutorName, time);   
            //add class to program
            program.addClass(fc, time - EARLIEST_START_TIME);                          
            updateDisplay();
        }
    }
 
    /**
     * Processes deleting a class
     */
    public void processDeletion()
    {
       //get data from text fields
        String classID = idIn.getText();
        String className = classIn.getText();
        String tutorName = tutorIn.getText();
       
        //check fields are not empty
        if (classID.equals("") || className.equals("") || tutorName.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please make sure to fill out all the fields!");
        }
       
        //check id exists
        else if (! program.courseExists(classID))
        {
            JOptionPane.showMessageDialog(null, "Course ID not recognised!");
        }
       
        else    //fields are not empty and course exists
        {
            program.deleteClass(classID);
            updateDisplay();
        }
    }
 
    /**
     * Instantiates a new window and displays the attendance report
     */
    public void displayReport()
    {
       report = new ReportFrame(program);
       report.setVisible(true);
    }
 
    /**
     * Writes lines to file representing class name,
     * tutor and start time and then exits from the program
     */
    public void processSaveAndClose() throws FileNotFoundException
    {
        String row = "";    //row holding course information
        File fout = new File (classesOutFile);  //file to be written to
        PrintWriter writer = new PrintWriter(fout);
        FitnessClass fc;
       
       for (int i = 0; i < MAX_CLASSES; i++)    //loop through classes
       {
         //find class by index position and store fitnessclass
           fc = program.findClass(i);  
           
           if (fc != null)  //if class at index position exists
           {
               row = row + fc.getClassID() + " " + fc.getCourse() + " "
               + fc.getTutor() + " " + fc.getTime() + "\n";
           }
       }
       
       writer.write(row);   //write to file
       writer.close();
       System.exit(0);      //end program
    }
 
    /**
     * Process button clicks.
     * @param ae the ActionEvent
     */
    public void actionPerformed(ActionEvent ae) {
       
        if (ae.getSource().equals(addButton))   //if Add is pressed
        {
            processAdding();
            clear();
        }
       
        else if (ae.getSource().equals(deleteButton))   //if Delete is pressed
        {
            processDeletion();
            clear();
        }
           
       
        else if (ae.getSource().equals(closeButton))    //if Save and Close is pressed
        {
            try
            {
                processSaveAndClose();
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       
        else if (ae.getSource().equals(attendanceButton))
        {
            displayReport();
        }
       
    }
   
   
    /**
     * method to clear the JTextFields
     */
    public void clear()
    {
        idIn.setText("");
        classIn.setText("");
        tutorIn.setText("");
    }
}