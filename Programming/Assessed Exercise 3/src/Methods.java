import java.io.FileReader;
import java.util.Scanner;

public class Methods {
	/**
	 * 
	 * 
	 * 
	 * 	
//	private int i =0;
//	String [][] array = new String [MAX_CLASSES][4];	//2D array 


	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
//		String [][] array = new String [MAX_CLASSES][4];	//2D array 
	
	String line="";
	try
	{
		FileReader filereader = new FileReader("ClassesIn.txt");
		Scanner input = new Scanner(filereader);
//		System.out.print("" + input.nextLine());//Check file can be read
		int i=0;
		input.nextLine();
			while(input.hasNextLine())
			{
//				String line = input.nextLine();
				line = input.nextLine();
				System.out.println(line);
//				array[i] = line.split(" +");
				i++;
			}
				input.close();
			}
			catch(Exception e)
			{
				System.out.println("Could not find file");	
			}
	}
	
	
	
	
	/** Populate the FitnessClass array by reading each line at a time from
	 * ClassesIn.txt from within initLadiesDay 
	 */
//	public String getLine(){
//		SportsCentreGUI a = new SportsCentreGUI();
//		a.initLadiesDay();	//call the method to read the input file
		//Method to open the file ClassesIn.txt			
//		String [][] array = new String [MAX_CLASSES][4];	//2D array 
//		String line="";
//		try
//		{
//			FileReader filereader = new FileReader("ClassesIn.txt");
//			Scanner input = new Scanner(filereader);
////			System.out.print("" + input.nextLine());//Check file can be read
//			int i=0;
//			input.nextLine();
//				while(input.hasNextLine())
//				{
////					String line = input.nextLine();
//					line = input.nextLine();
//					System.out.println(line);
////					array[i] = line.split(" +");
//					i++;
//				}
////			System.out.println(array[0][0]+"\t\t"+ array[0][1]+"\t\t"+array[0][2]+"\t"+array[0][3]+"\t");
////			System.out.println(array[1][0]+"\t\t"+ array[1][1]+"\t\t"+array[1][2]+"\t"+array[1][3]+"\t");
////			System.out.println(array[2][0]+"\t\t"+ array[2][1]+"\t\t"+array[2][2]+"\t"+array[2][3]+"\t");
////			System.out.println(array[3][0]+"\t\t"+ array[3][1]+"\t"+array[3][2]+"\t"+array[3][3]+"\t");
////			System.out.println(array[4][0]+"\t\t"+ array[4][1]+"\t\t"+array[4][2]+"\t"+array[4][3]+"\t");
////			System.out.println(array[5][0]+"\t\t"+ array[5][1]+"\t\t"+array[5][2]+"\t"+array[6][3]+"\t");
////			System.out.println(array[6][0]+"\t\t"+ array[6][1]+"\t\t"+array[6][2]+"\t"+array[6][3]+"\t");
////			System.out.println(array[7][0]+"\t\t"+ array[7][1]+"\t\t"+array[7][2]+"\t"+array[7][3]+"\t");
////			System.out.println(Arrays.deepToString(array)); //Print the 2D array
//			input.close();
//			}
//		catch(Exception e)
//		{
//			System.out.println("Could not find file");	
//		}
////		return array;
//		return line;
	}
try{
	FileReader filereader = new FileReader("AttendancesIn.txt");
	Scanner input = new Scanner(filereader);
//	System.out.print("" + input.nextLine());	//Check file can be read
	while(input.hasNextLine()){			
		String example = input.nextLine();
//		System.out.println(example);	
		String [] attendancearray = example.split(" +");
		for (int i=0; i< attendancearray.length;i++)
			System.out.println("Token "+i+" is: "+ attendancearray[i]);
	}
	input.close();
}
catch(Exception e){
System.out.println("Could not find file");	
}
}
}
