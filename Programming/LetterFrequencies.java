/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	
	/** Character that occurs most frequently */
	private char maxCh;
	
	/** Frequency of characters in cipher documents */
	private double [] freqPercent; 
	
	/** difference between letter frequency and avg counts*/
	private double [] freqDifference;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies() {	
		alphabet = new char[SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char) ('A' + i);
		
		alphaCounts = new int[SIZE];
		for (int i = 0; i < SIZE; i++)
			alphaCounts[i] = 0;
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character to increment count of
	 */
	public void addChar(char ch) {
	    for (int i = 0; i < SIZE; i++) {
	    	if (ch == alphabet[i]) {
	    		totChars+= 1;
	    		alphaCounts[i] += 1;
	    	}
	    }
	}
	
	/**
	 * Gets the maximum frequency, assigns most common letter to maxCH variable
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		int n = 0;
		double max = 0;
		for(int i = 0; i < SIZE; i++)
			if (max <= alphaCounts [i])
			{
				n = i;
				max = alphaCounts[i];
				maxCh = alphabet[i];
			}
	    return freqPercent[n]; 
	}
	
	/**
	 * Returns a String consisting of the full frequency report for all characters
	 * @return the report
	 */
	public String getReport() {		
		//Runs necessary methods to create report
		frequencyCalc();
		freqDiff();
		double maxPC = getMaxPC();
		
		String report = String.format("LETTER ANALYSIS\r\n\r\n%10s %10s %10s %10s %10s %n", 
									"Letter", "Freq","Freq%", "AvgFreq%", "Diff");
		
			//Loop to add additional rows to string containing data on each character
			for(int i = 0;i < SIZE;i ++) {
				report += 
				String.format("%10s %10d %10.1f %10.1f %10.1f \r\n",
					alphabet[i], alphaCounts[i], freqPercent[i],
					avgCounts[i], freqDifference[i]);
			}
			report +=String.format("\r\n\r\nThe most frequent letter is %1s at %3.1f%%", maxCh, maxPC);
		return report;	
	}
	
	/**
	 * Calculates the frequency of each character
	 * Outputs values to freqPercent instance variable
	 */
	private void frequencyCalc() {
		freqPercent = new double [SIZE];
		for (int i = 0; i < SIZE; i++) {
			freqPercent[i] = (100.0 / totChars) * alphaCounts[i];
		}
	}
	
	/**
	 * Calculates difference between character occurrence and real-world usage %
	 * Outputs values to freqDifference instance variable
	 */
	private void freqDiff() {
		freqDifference = new double [SIZE];
		for (int i = 0; i < SIZE; i++) {
			freqDifference [i] = freqPercent[i] - avgCounts[i];
			if (freqDifference[i] < 0 && freqDifference[i] > -0.05) {
				freqDifference [i] = 0;
			}
		}
	}	
}