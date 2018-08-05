/**
* This class represents a player in the game.
* It stores all the Card objects the player currently has 
* and has methods to get and set information about the player.
*
*/
public class Player {
	
	private Card[] cards;				// An array of Card objects that stores all the Cards the player currently has.
	private static int SIZE = 40;		// The total number of cards in the game.
	private int currentCards;			// The number of cards a player currently has.
	private boolean inGame;				// Shows whether the player is in the game or out.

	/**
	* The constructor for the Player object.
	* It initializes the instance variable for the Player.
	* 
	*/	
	public Player()
	{
		currentCards = 0;				// Current number of cards is zero.
		cards = new Card[SIZE];			// Initializes array to max number of cards a Player can have.
		inGame = true;					// Player is in game.
	}

	/**
	* This method takes a Card object as a parameter and adds that Card
	* to the back of the Players cards.
	*
	* @param c 	A Card that should be added to the Players array of Cards.
	*/
	public void addCard(Card c)
	{
		cards[currentCards] = c;		// Adds the Card to the back of deck.
		currentCards += 1;				// Updates the number of current cards.
		
	}
	
	/**
	* This method removes the top card from the players deck.
	*
	* @return 	The top card.
	*/	
	public Card removeCard()
	{	
		Card drawn = cards[0];					// Save a copy of the top card.
		currentCards -= 1;						// Update the number of current cards.
		for(int i = 0; i < currentCards; i++)	// For the number of cards in the deck.
		{
			cards[i] = cards[i+1];				// Move each card up the array.
		}
		return drawn;							// Return the top card.
	}
	
	
	/**
	* A method that lets you peek at the players top card.
	*
	* @return 	The top card.
	*/
	public Card seeTopCard()
	{
		return cards[0];
	}
	
	
	/**
	* A getter method that returns the number of current cards the player has.
	*
	* @return 	The number of current cards.
	*/
	public int getCurrentCards()
	{
		return currentCards;
	}
	
	
	/**
	* This method set the players inGame instance variable.
	*
	* @param x	The boolean value you wish to set inGame to.
	*/
	public void setInGame(boolean x)
	{
		inGame = x;
	}
	
	
	/**
	* This method checks to see if the player is still in the game.
	*
	* @return inGame 	The current game status of the player.
	*/
	public boolean seeIfInGame()
	{
		return inGame;
	}
	
	/**
	* This method is for testing purposes.
	* It prints out the contents of each card the player has.
	*/
	public void printOutDeck()
	{
		String test = "";
		for(int i = 0; i < currentCards; i++)
		{
			test += "\n" + cards[i].getDescription();
			for(int j = 0; j < cards[i].getCATEGORIES(); j++)
			{
				test += " " + cards[i].getValueAtIndex(j);
			}
		}
		System.out.println(test);
	}
}