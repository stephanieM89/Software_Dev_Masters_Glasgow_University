/**
 * This class represents a Card in the game.
 * It stores a description of the card and the values of the card.
 *
 */
public class Card {
	
	private String description;			// The name of the card e.g. T-rex.
	private int[] values;				// An integer array of the scores for each category this card holds.
	private final int CATEGORIES = 5;	// The number of categories per card.
	
	/**
	 *  The default constructor for a Card object. It initializes the values array.
	 */
	public Card()
	{
		values = new int[CATEGORIES];
	}
	
	/**
	* Constructor that can be fed a String of formatted information about a card and use
	* that String to set the instance variables.
	*
	* @param info 	A String containing information about the Card formatted in a predefined way.
	*/
	public Card(String info)
	{
		values = new int[CATEGORIES];		
													// Sets array to have length of the number of categories.
		String[] list = info.split(" +");			// Split the String whenever there is one or more space.
		description = list[0];						// Sets the description of the card.
		
		for(int i = 1; i < CATEGORIES + 1; i++)		// For the number of categories:
		{
			values[i-1] = Integer.valueOf(list[i]);	// Set the values to the values from the deck.txt file
		}
	}
	
	/**
	 * Method to get the card description.
	 * 
	 * @return	A String of the card description.
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Method to get the score at a particular index of the values array.
	 * 
	 * @param i		The desired index.
	 * @return		The value stored at that index.	
	 */
	public int getValueAtIndex(int i)
	{
		return values[i];
	}
	
	/**
	 * Method to pick the category with the highest score.
	 * 
	 * @return	The index which has the highest score.
	 */
	public int selectCategory()
	{
		int maxIndex = 0;							// Set the max index to the first one.
		for(int i = 1; i < CATEGORIES; i++)			// Iterate through the rest of the values
		{
			if(values[i] > values[maxIndex])		// If another value is bigger than the first one.
			{
				maxIndex = i;						// Replace maxIndex with the index of the bigger one.
			}
		}
		return maxIndex;							// Return the index which holds the highest score.
	}

	
	/**
	 * Method to get the number of categories a card object has.
	 * 
	 * @return	The number of categories.
	 */
	public int getCATEGORIES()
	{
		return CATEGORIES;
	}

}