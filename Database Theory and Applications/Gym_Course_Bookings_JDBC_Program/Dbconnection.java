import java.sql.Connection;
import java.awt.*;
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
 *   This class is where the database interaction will be performed in.
 */

// This method establishes a connection with the University Gym database.
public class Dbconnection {
	//Set up a connection with the data source
	private Connection connection =null;
	public Dbconnection()
	{	
		String dbname = new String("m_16_2286857m");
		String username = new String("m_16_2286857m");
		String password = new String("2286857m");
	
	try 
	{
	connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + dbname, username, password);
	}
	
	catch (SQLException e)
	{
		System.err.println("Connection Failed!");
		e.printStackTrace();
		return;
	}
	
	if (connection != null)
	{
		System.out.println("Connection Successful");
	} else
	{
		System.err.println("Failed to make a connection!");
	}
	}
	
	// This method closes the connection
	public void close()
	{
		try
		{
		connection.close();
		System.out.println("Connection closed");
		}
	catch (SQLException e)
		{
		e.printStackTrace();
		System.out.println("Connection could not be closed - SQL exception");
		}
	}
	
	
	/*
	 * This method gets a list of the gym courses available from the gymcourse
	 * table and will be used by the courseComboBox so the user can select a course
	 * to make a booking or view course information.
	 */
	
	public String [] getGymcourses()
	{	
		int i =0;
		String [] gymCourse = new String [9];
		try
		{	Statement stmt3 = null;
			String gCourse = "SELECT* FROM gymcourse";		
			stmt3 = connection.createStatement();
			ResultSet rs = stmt3.executeQuery(gCourse);
			while (rs.next())
			{		
				gymCourse[i] = rs.getString("name");
				i++;
			}	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return gymCourse;
	}
	
	
	/*
	 *  This method gets the member membershipnumber which is
	 *  used by the memberComboBox so the user can make a member booking.
	 */
	public String [] getMembershipnum()
	{
		int i=0;
		String [] getMem = new String [9];
		try
		{
			Statement stmt = null;
			String membernum = "SELECT membershipnum FROM member";
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(membernum); 
			while(rs.next())
			{	getMem [i] = rs.getString("membershipnum");
				i++;
			}
		}
		catch (SQLException e)
		{
		e.printStackTrace();
		}
		return getMem;
	}

	
	// This method gets a list of the courseIDs.
	public String [] getCourseid()
	{
		int i=0;
		String [] courseId = new String [9];
		try
		{
			Statement stmt = null;
			String courseID = "SELECT id FROM gymcourse";
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(courseID);
			while(rs.next())
			{
				courseId[i] = rs.getString("id");
				i++;
			}
			System.err.println(courseId[2]);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return courseId;
	}
	
	// This method connects the course name to course id. 
	public int getCourseID(String selection)
	{
		Statement stmt = null;
		String select = selection;
		int courseid = 0;
		String  getCourseid = "SELECT id FROM member WHERE name = '" + select + "'";
		try{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(getCourseid);
			while(rs.next())
			{
				courseid = rs.getInt(1);
			}
		}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
	
			return courseid;	
	}

	//This method gets the instructor names from the gymcourse and instructor table.
	public String getInstructorname(String selection)
	{
		String select = selection;
		String instructorName = "";
		Statement stmt = null;
		String instructorname = "SELECT fname,lname from instructor INNER JOIN gymcourse ON instructor.courseid = gymcourse.id WHERE gymcourse.name = '" + select + "'";
		try
		{	
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(instructorname);
			
			while(rs.next())
			{
				String instructorfName = rs.getString("fname");
				String instructorlName = rs.getString("lname");
				instructorName =  instructorfName + " "+ instructorlName;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return instructorName;
	}
	
	// This method gets the maximum capacity of members that can be booked onto a course
	public int getMaxcapacity(String selection)
	{
		String select = selection;
		int maxCapacity = 0;
		Statement stmt = null;
		String capacity = "Select maxcapacity FROM gymcourse WHERE gymcourse.name = '" + select + "'";
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(capacity); 
			while(rs.next())
			{
				maxCapacity = rs.getInt("maxcapacity");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return maxCapacity;
	}
	
	/*
	 *  This methods counts the number of members that are currently booked into a course
	 */
	
	public int getNumbers(String selection)
	{
		String select = selection;
		int numBooked = 0;
		Statement stmt = null;
		String numbersbooked = "SELECT COUNT(courseid) FROM membooking INNER JOIN gymcourse ON membooking.courseid= gymcourse.id WHERE gymcourse.name= '" + select + "'";
		try
		{	

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(numbersbooked); 
			while(rs.next())
			{
				numBooked = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return numBooked;
	}
	
	/*
	 * 	This method updates the member booking table with a new booking.  It calls executeUpdate as the statement uses 
	 *  an INSERT command and requires a subquery to access the name of the course.
	 */
	public void updateMemberbooking(String courseselection, int memid) // memberid
	{
		String select = courseselection;
		System.out.print(select);
		int membershipid = memid;
		Statement stmt = null;
		String memberBooking = "INSERT INTO membooking VALUES '" + select + "' '"+ membershipid + "')";	

		try
		{	
			stmt = connection.createStatement();
			stmt.executeUpdate(memberBooking); 
			System.err.println("test2 booking");
		}

		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/* 
	 * This method shows all members that are booked onto a particular course. This calls executesQuery which joins three tables  
	 * to link the member information with the courses that they are currently booked on.
	 */
	
	public String getCoursemembers(String selection)
	{
		String a = "Membership number" +"\t" + "First Name" + "\t" + "Last Name" + "\t" + "Course" + "\n";
		String select = selection;
		Statement stmt = null;
		String viewMembers = "SELECT member.membershipnum, member.fname, member.lname, gymcourse.name FROM member INNER JOIN membooking ON member.membershipnum=membooking.member INNER JOIN gymcourse ON membooking.courseid=gymcourse.id WHERE gymcourse.name = '" + select + "'";
		
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(viewMembers);
			while(rs.next())
			{
				String memNumber = rs.getString("membershipnum");
				String firstName = rs.getString("fname");
				String lastName = rs.getString("lname");
				String courseName = rs.getString("name");

				a += memNumber + "\t" + "\t"+ firstName + "\t"+ lastName + "\t"+ courseName + "\n";
			}
		} 
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return a;
	}	
}