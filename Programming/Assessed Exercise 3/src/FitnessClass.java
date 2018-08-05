
/** 
 * Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    // Instance variables
	private String classId;
	private String className;
	private String tutorName;
	private int startTime;
	private int [] attendance;
	
	private static final int WEEKS = 5;
	private double [] averageAttendance;
	

	// Default constructor
	public FitnessClass()
	{	

	}
	
	public FitnessClass(String id, String classname, String tutorname, int starttime, int [] classAttendance)
	{
		classId = id;
		className = classname;
		tutorName = tutorname;
		startTime = starttime;	
		attendance = classAttendance;
	}
	
	public FitnessClass(String id, String classname, String tutorname, int starttime)
	{
		classId = id;
		className = classname;
		tutorName = tutorname;
		startTime = starttime;
	}
	
//	public fitnessobject(String readline){
		
//	}
	
	public void setClassid(String id)	{classId = id;}
	
	public void setClassname(String classname)	{ className = classname;}
	
	public void setTutorname(String tutorname)	{ tutorName = tutorname;}
	
	public void setStarttime(int starttime)		{ startTime = starttime;}
	
	public void setAttendances(int [] classattendance){ attendance = classattendance;}
	
	public String getClassid() {return classId;}
	
	public String getClassname() {return className;}
	
	public String getTutorname()	{return tutorName;}
	
	public int getStartTime()	{return startTime;}
	
	
	/**
	 * This method calculates and returns the average attendance over 5 weeks for a class
	 * @param classattendance
	 * @return attendAverage
	 */	
	public double[] getAverage(int [] classattendance)
	{
		int totalattendance=0;							//Represents the total attendance over 5 weeks for a class
		double[] averageAttendance = new double [WEEKS]; //Represents the Average Attendance over 5 weeks for a class
		double average = 0;

		for (int i=0; i<WEEKS; i++)	//This look calculates the average attendance and saves as Array attendAverage[];
		{
			totalattendance = totalattendance + classattendance[i]; //The total attendance is calculated
			average = (double)totalattendance/WEEKS;				//This finds the average attendance for each class
			averageAttendance[i] = average;
			i++;
		}
		return averageAttendance;
	}
	
	
	/** 
	 *  Returns a String formatted appropriately for the attendance report
	 *  @return the report
	 */
	public String returnAttendanceReport()
	{
		String report = String.format("%5s %15s %12s %20s %20s\r\n", "Id","Class","Tutor","Attendances","Average Attendance");
		
		//This loops to add more rows with class information
		for (int i=0; i<WEEKS; i++)
		{
//			report +=
//			String.format("%5s %15s %12s %20s %20.2f"\r\n", classId[i], className[i], tutorName[i], attendance[i],averageAttendance[i]);				
		}
		//This gives the overall average of the average attendance
//		report +=String.format("\r\n\t\t\tOverall average: "+overallAverage);
			return report;
	}
	
	
    public int compareTo(FitnessClass other) {
	  return 0; // replace with your code
    }
}
