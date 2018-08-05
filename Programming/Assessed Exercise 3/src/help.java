
public class help {


/** From fitnessClass 
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
 *  @return String report
 */
public String returnAttendanceReport()
{
	String report = String.format("%5s %15s %12s %20s %20s\r\n", "Id","Class","Tutor","Attendances","Average Attendance");
	
	//This loops to add more rows with class information
	for (int i=0; i<WEEKS; i++)
	{
//		report +=
//		String.format("%5s %15s %12s %20s %20.2f"\r\n", classId[i], className[i], tutorName[i], attendance[i],averageAttendance[i]);				
	}
	//This gives the overall average of the average attendance
//	report +=String.format("\r\n\t\t\tOverall average: "+overallAverage);
		return report;
}

