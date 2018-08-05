/**
 * Defines an object representing a single fitness class
 */ 
 
public class FitnessClass implements Comparable<FitnessClass> { 
	
    // number of weeks of attendance monitoring: 5
    private final int WEEKS = 5;
   
    //instance variables: classID, course, tutor, time, attendance
    private String classID;
    private String course;
    private String tutor;
    private int time;
    private int[] attendance = {0, 0, 0, 0, 0}; //set default attendance to 0
   
    //default constructor
    public FitnessClass()
    {
       
    }
	
    //constructor
    public FitnessClass(String id, String courseName, String tutorName, int timeBeg, int[] attendanceNum)
    {
        classID = id;
        course = courseName;
        tutor = tutorName;
        time = timeBeg;
        attendance = attendanceNum;
    }
 
    //constructor
    public FitnessClass(String id, String courseName, String tutorName, int timeBeg)
    {
        classID = id;
        course = courseName;
        tutor = tutorName;
        time = timeBeg;
    }
   
   
    //accessor methods: getClassID, getCourse, getTutor, getTime, getAttendance
    public String getClassID()
    {
        return classID;
    }
   
    public String getCourse()
    {
        return course;
    }
   
    public String getTutor()
    {
        return tutor;
    }
   
    public int getTime()
    {
        return time;
    }
   
    public int[] getAttendance()
    {
        return attendance;
    }
   
   
    //mutator methods: set...
    public void setClassID (String classID)
    {
        this.classID = classID;
    }
   
    public void setCourse (String course)
    {
        this.course = course;
    }
   
    public void setTutor (String tutor)
    {
        this.tutor = tutor;
    }
   
    public void setTime (int time)
    {
        this.time = time;
    }
   
    public void setAttendance (int[] attend)
    {
        for (int i = 0; i < WEEKS; i++)
        {
            this.attendance[i] = attend[i];
        }
    }
   
   
    /**
     * method to return the average attendance for the class
     *
     * @return double avg
     *
     */
    public double getAvg()
    {
        int sum = 0;    //sum of class attendance initialised to 0
       
        //loop through attendance for all 5 days and calculate sum
        for (int i = 0; i < 5; i++)
        {
            sum = sum + attendance[i];
        }
       
        //calculate average
        double avg = Math.round(((double) sum / 5.0) * 100) / 100.0 ;
       
        return avg;
    }
   
    /**
     * method to return a String formatted appropriately for the attendance report
     *
     * @param
     * @return String report
     */
    public String attendColumn()
    {  
        String attendColumn = " "; 
 
        attendColumn = String.valueOf(String.format(" %3d",attendance[0]))
                + String.valueOf(String.format(" %3d",attendance[1]))
                + String.valueOf(String.format(" %3d",attendance[2]))
                + String.valueOf(String.format(" %3d",attendance[3]))
                + String.valueOf(String.format(" %3d",attendance[4]));  
               
        return attendColumn;       
    }
   
 
    /**
    * method to compare Fitness Class objects on average attendance
    *
    * @param FitnessClass other
    * @return int   1 if other attendance greater and class moves up
    *               0 if class attendences are equal and class stays in place
    *               -1 if average is greater than other and other needs to go down
    */
    public int compareTo(FitnessClass other)
    {
        double average = getAvg();
 
       
        if (average < other.getAvg())
        {
            return 1;
        }
       
        else if (average == other.getAvg())
        {
            return 0;
        }
       
        return -1;
    }
}
