import java.util.*;
/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
 
public class FitnessProgram {
	//maxNum of classes: 7
    private final int MAX_CLASSES = 7;
   
    //earliest start time for a course
    private final int EARLIEST_START_TIME = 9;
   
    //array of FitnessClass objects, actual number of non-null objects in list
    private FitnessClass [] listCourses;
   
    //number of existing classes
    private int numClasses;
   
   
    // default constructor to initialise array
    public FitnessProgram()
    {
        listCourses = new FitnessClass[MAX_CLASSES];
        numClasses = 0;
    }
   
   
 
    //methods to return instance variables
    /**
     * method to return array of FintessClass objects at index x
     */
   
    public FitnessClass[] getCourses()
    {
        return listCourses;
    }
   
   
    /**
     * method to return actual number of non-null objects
     */
   
    public int getNumClasses()
    {
        return numClasses;
    }
   
   
  /**
   * This method adds a new class to the list
   *
   * @param fc
   * @param i
   */
   
    public void addClass(FitnessClass fc, int i)
    {
            listCourses[i] = fc;
            numClasses ++;      //increment number of courses
    }
   
   
    /**
     * This method deletes a class from the list
     *
     * @param classID
     */
    public void deleteClass (String classID)
    {
        //loop through all potential 7 courses and search for classID
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            if (listCourses[i] != null && (listCourses[i].getClassID().equals(classID)))
            {
                listCourses[i] = null;  //if found, set to null
                numClasses --;          //decrement number of courses
            }
        }
    }
   
   
 
      /**
        * method to find FitnessClass by index
        *
        * @param int index
        * @return course at index or null (if non-existent)
        */
       public FitnessClass findClass(int index)
       {
           if(listCourses[index] != null)
           {  
               return listCourses[index];   //if found, return class at index      
           }
           
           return null; //if class does not exist, return null  
       }
   
   
    /**
     * method to return FitnessClass starting at particular time (or null if non-existent)
     *
     * @param int time
     * @return course at index i or null (if non-existent)
     */
    public FitnessClass classAt(int time)
    {
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            //loop through courses in list and get their starting time
            if (listCourses[i].getTime() == time && (listCourses[i] != null))
            {
                //if the time equals the time entered and the course exists
                return listCourses[i];  //return class
            }
        }
        return null;    //no class at that time
    }
   
    /**
     * method to find the earliest available starting time for new class
     * return class index for timetable or  -1 if there is no slot available
     *
     * @return i + EARLIEST_START_TIME if there is a free slot OR -1 if no slot is available
     */
    public int getFirstAvailable()
    {
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            if(findClass(i) == null)    //if there is no class at i
            {
                //this is the earliest free slot
                return i + EARLIEST_START_TIME; //calculate and return the free time slot
            }
        }
       
        return -1;  //if no class is null, return -1 to signify that all slots are full
    }
   
    /**
     * method to return FitnessClass object with a given ID number (or null if non-existent)
     *
     * @param String classID
     * @return class index or null (if classID non-existent)
     *
     */
    public FitnessClass searchByID(String classID)
    {  
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            if(listCourses[i] != null && listCourses[i].getClassID().equals(classID))
            {
                return listCourses[i];
            }
        }
       
        return null;    //classID does not exist
    }
 
   
   
    /**
     * method to return a sorted list  in non-increasing order on average attendance for each class
     * uses Arrays.sort method
     *
     * @return sorted FitnessClass array
     */
    public FitnessClass[] sortCourses()
    {
        //new array to sort courses
        FitnessClass [] sortThis = new FitnessClass[numClasses];
       
        //index-count of courses to be sorted
        int count = 0; 
       
        //initialise unsorted array
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            if (listCourses[i] != null) //check if course exists at index-position i
            {
                sortThis[count] = listCourses[i];
                count++;
            }
        }
   
        //sorting the array
        Arrays.sort(sortThis);
       
        return sortThis;   
    }
   
   
    /**
     * method to return the overall average attendance
     * @return double totAvg
     */
   
    public double getOverallAttendanceAvg()
    {
        double sum = 0; //sum of average course attendance initialised to 0
       
        //loop through all courses in array
        for (int i = 0; i < listCourses.length; i++)   
        {
            if (listCourses[i] != null)
            {
                //add up the average attendance for each course in array
                sum = sum + listCourses[i].getAvg();               
            }
        }
       
        //calculate total average of all course attendances
        double totAvg = Math.round(sum/numClasses * 100) /100.0;       
       
        return totAvg;
    }
   
   
    /**
     * method to search for ID in FitnessClass list
     *
     * @param classID
     * @return boolean found/not-found
     */
   
    public boolean courseExists (String classID)
    {
        boolean found = true;   //course already exists
       
        for (int i = 0; i < MAX_CLASSES; i++)
        {
            //if class not found
            if (searchByID(classID) == null)
            {
                //set boolean to false
                found = false;
            }
        }
       
        return found;
    }
}
