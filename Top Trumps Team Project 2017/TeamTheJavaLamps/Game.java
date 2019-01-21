import java.util.Random;
import javax.swing.JOptionPane;

/**
* The Game class is where all the game mechanics are stored. 
* It has all the logic needed to run the Top Trumps game.
* 
*/
public class Game {
	
	private Card[] deck;			// The deck of cards in the game stored as an array of Card objects. The deck is also used as the communal pile.
	private int CurrentCards;		// The number of cards currently in the deck.
	private Player[] players;		// An array of Player objects that contains the players in the game.
	private static int SIZE = 40;	// The number of cards in the deck.
	private static int PLAYERS; 	// The number of players in the game.  	should this be static?
	private int[] inRound;			// An array of ints that stores which players are in a round with 0-4 representing the players 1-5.
	private int currentTurn;		// Stores whose turn it is as an int with 0-4 representing the players 1-5.
	private int numDraws;			// Keeps a record of the number of draws in a game.
	private int numRounds;			// Keeps a record of the number or rounds in a game.
	private int[] numWins;			// Keeps a record of the number of rounds each players has won, where if numWins[0] = 14 --> player 1 has won 14 rounds.
	private boolean drawLastRound;  // This variable helps execute different gameplay in the case where the last round was a draw.
	
	/**
	* The constructor for the Game class.
	* The constructor takes as a parameter the number of players in the game
	* and uses that to create a Player object to represent each player and store 		
	* them in an array.
	*
	* @param player 	The number of players in the game.
	*/
	public Game(int player)
	{
		players = new Player[player];		// Initialises the players array to be the correct size.
		PLAYERS = player; 					// Sets the number of players in the game to the corrext number.			
		
		for(int i = 0; i < PLAYERS; i++) 	// Fill the players array with Player objects.
		{
			players[i] = new Player();
		}
		
		deck = new Card[SIZE];				// Initialise the deck to the correct size.
		CurrentCards = 0;					// Set number of current cards in deck to zero.
		inRound = new int[PLAYERS];			// Initialise the inRound array to be the size of the number of players in the game.
		for(int i = 0; i <PLAYERS; i++)
		{
			inRound[i] = 1;
		}
		numWins = new int[PLAYERS];			// Initialise the numWins array to be the size of the number of players in the game.
		drawLastRound = false;
	}
	
	
	
	/**
	 * A getter method to access the current number of cards in the main deck.
	 * 
	 * @return	The number of cards currently in the deck.
	 */
	public int getCardsinDeck()
	{
		return CurrentCards;
	}
	
	
	/**
	* A getter method to access the number of draws in the game so it can be added to the database.
	*
	* @return	The number of draws in the game.
	*/
	public int getNumDraws()
	{
		return numDraws;
	}

	/**
	* A getter method to access the number of rounds that took place in the game.
	* This will be needed by the Database class.
	*
	* @return The number of rounds in the game.
	*/
	public int getNumRounds()
	{
		return numRounds;
	}

	/**
	* A getter method to access the number of rounds won by each player.
	* This will be needed by the Database class to compute game stats.
	*
	* @return An array storing the total number of wins for each player (with player 1s wins stored in index 0 etc.)
	*/
	public int[] getNumWins()
	{
		return numWins;
	}
	
	/**
	* A getter method to access who won the game.
	*
	* @return the index of the winning player or -1 if there isn't a winner.
	*/
	public int getWhoWon()
	{
		int playersinGame = 0;						// Stores how many players are left in the game.
		int winner = 0;								// Stores the index of the winning player
		for(int i = 0; i < PLAYERS; i++)			// Loop over the players array.
		{
			if(players[i].seeIfInGame() == true)	// If the player still in game.
			{
				playersinGame += 1;					// Increment the playersInGame counter.
				winner = i;							// Set winner to the index of the player.
			}
		}
		if(playersinGame == 1)						// If there is just one player left in the game:
		{
			return winner;							// Return the index of the winner.
		}
		else
		{
		return -1;		// If there is not a current winner return -1.
		}
	}

	/**
	 * This method determines which player starts the game and gets to pick the first category.
	 * 
	 */
	public void whoStarts()
	{
		Random r = new Random();							// Create a new Random object. 
		int x = r.nextInt(PLAYERS);							// Select a random number between 0 (inclusive) and the number of players (not inclusive).
		currentTurn = x;									// Set the current turn to this number (corresponds to the player index in players array).
		if(x == 0)
		{
			JOptionPane.showMessageDialog(null,				// If the user is selected a pop-up message is displayed.
					"You go first!",
					"Game Message",							
				    JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		{
			JOptionPane.showMessageDialog(null,				// Else a pop-up message is displayed informing the user who
				    "Player "+(x)+" picks first!",			// gets to start.
				    "Game Message",
				    JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * Method for testing purposes. Prints the description of all the cards in the deck
	 * separated by dashes.
	 */
	public void printDeck()
	{
		System.out.println("--------------------------------------------");
		String test = "";
		for(int i = 0; i < CurrentCards; i++)
		{
			test += "\n" + deck[i].getDescription();
			for(int j = 0; j < deck[i].getCATEGORIES(); j++)
			{
				test += " " + deck[i].getValueAtIndex(j);
			}
		}
		System.out.println(test);
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * Method for testing purposes. Prints the deck for each player in the game
	 * and displays which player the deck belongs to. The output is separated
	 * by dashes.
	 */
	public void printPlayersDeck()
	{
		System.out.println("--------------------------------------------");
		for(int i = 0; i < PLAYERS; i++)
		{
			if(i == 0 && players[i].seeIfInGame())
			{
				System.out.println("***USER'S DECK***");
			}
			else if(players[i].seeIfInGame())
			{
				System.out.println("***COMP "+ i+"'S DECK***");
			}
			players[i].printOutDeck();
			System.out.println(" ");
		}
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * Method for testing purposes. Print out the top card of every player
	 * currently in the game. The output is separated by dashes.
	 */
	public void printCurrentCardsInPlay()
	{	
		String test = "";
		System.out.println("CURRENT CARDS IN PLAY:");
		System.out.println("--------------------------------------------");
		for(int i = 0; i < PLAYERS; i++)
		{
			if(players[i].seeIfInGame() == true)
			{
				test += "\n" + players[i].seeTopCard().getDescription();
				for(int j = 0; j < players[i].seeTopCard().getCATEGORIES(); j++)
				{
					test += " " + players[i].seeTopCard().getValueAtIndex(j);
				}
			}
		}
		System.out.println(test);
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * Method for testing purposes. This prints out the top card of every player
	 * in the case of a draw round where only some players will be in the round.
	 */
	public void printCurrentCardsIfDraw()
	{

		String test = "";
		System.out.println("CURRENT CARDS IN PLAY:");
		System.out.println("-------------------------------------------");
		for(int i = 0; i < PLAYERS; i++)
		{
			if(inRound[i] == 1 && players[i].seeIfInGame())
			{
				test += "\n" + players[i].seeTopCard().getDescription();
				for(int j = 0; j < players[i].seeTopCard().getCATEGORIES(); j++)
				{
					test += " " + players[i].seeTopCard().getValueAtIndex(j);
				}
			}
			
		}
		System.out.println(test);
		System.out.println("-------------------------------------------");
		
	}
	/**
	* This method adds a card to the deck and updates the number of cards in the deck.
	*/
	public void addCard(Card c)
	{
		deck[CurrentCards] = c;
		CurrentCards += 1;
	}
	
	/**
	* This method removes a card from the deck and updates the number of cards in the deck.
	*/
	public void removeCard(int index)
	{
		deck[index] = null;
		CurrentCards -= 1;
	}

	/**
	* This method deals out the cards from the deck to each player in the game.
	* 
	*/
	public void deal()
	{
		for(int i = 0; i < SIZE; i += PLAYERS)			// Depending on how many players are in the game move up through the deck by that number.
		{
			if(PLAYERS == 2)							// If there's two players in the game enter this condition. 
			{											// Add a card to each player and remove the cards from the deck.
				players[0].addCard(deck[i]);
				players[1].addCard(deck[i+1]);
				removeCard(i);
				removeCard(i+1);
			}
			else if(PLAYERS == 3)						// If there's three players in the game enter this condition.
			{
				if(i == (SIZE - (SIZE % PLAYERS))) 		// The cards won't be divided equally so when we've reached the remaining card
				{										// Only player 1 gets an extra card.
					players[0].addCard(deck[i]);
					removeCard(i);
				}
				else									// Otherwise just add a card to each player and remove the cards from deck.
				{
					players[0].addCard(deck[i]);
					players[1].addCard(deck[i+1]);
					players[2].addCard(deck[i+2]);
					removeCard(i);
					removeCard(i+1);
					removeCard(i+2);
				}
			}
			else if(PLAYERS == 4)						// If there's four players in the game enter this condition.
			{
				players[0].addCard(deck[i]);			// Add a card to each player and remove the cards from the deck.
				players[1].addCard(deck[i+1]);
				players[2].addCard(deck[i+2]);
				players[3].addCard(deck[i+3]);
				removeCard(i);
				removeCard(i+1);
				removeCard(i+2);
				removeCard(i+3);
			}
			else										// If there's five players in the game enter this condition.
			{
					players[0].addCard(deck[i]);		// Add a card to each players and remove the cards from the deck.
					players[1].addCard(deck[i+1]);
					players[2].addCard(deck[i+2]);
					players[3].addCard(deck[i+3]);
					players[4].addCard(deck[i+4]);
					removeCard(i);
					removeCard(i+1);
					removeCard(i+2);
					removeCard(i+3);
					removeCard(i+4);
			}
		}
	}
		
	
		
		
		
	
	/**
	* This method shuffles the deck so that the cards dealt to each player are different
	* in different games.
	*
	*/
	public void shuffle()
	{
		Card[] temp = new Card[SIZE];			// Initialise a temporary deck of cards.
		Random r = new Random();				// Create a new Random object.
		
		for(int i = 0; i < SIZE; i++)			// For the number of cards in the deck.
		{
			int x = r.nextInt(SIZE - i);		// Generate a random number between 0 and the current number of cards in the deck.
			temp[i] = deck[x];					// Put the randomly selected card from the deck into the ith index of the temporary deck.
			deck[x] = deck[(SIZE - i)-1];		// Move the current last card in the deck to the empty slot in deck.
		}										// This means temp will contain all the cards in a random order.
												
		for(int j = 0; j < SIZE; j++)			// For every card move the cards from temp back into the deck.
		{
			deck[j] = temp[j];
		}										// Deck will now be in a randomised order.
	}
	
	
	/**
	* This method handles giving the cards in the current round to whoever was the winner. 
	* It also handles if the round was a draw.
	*
	* @param player 	The index of the player who won the round, or -1 if it was a draw.
	*/
	public void winHand(int player)
	{
		numRounds += 1; 										// Update the numRounds counter.
		boolean winnerInDraw = false;							// At the start of each round this is set to false.	
																			
		
		// This section handles the case where the round was a draw:
		if (player == -1)
		{
			numDraws += 1; 										// Update the numDraws counter.
			for(int i = 0; i < PLAYERS; i++)					// Loop round all the players.
			{
				if(players[i].seeIfInGame())					// If the player is still in the game:
				{
					addCard(players[i].removeCard());			// Remove their card from the last round and add it to the deck (communal pile).
				}
			
				if(inRound[i] == 1 && i == currentTurn) 		// Checks to see if the winner of the previous round is in the draw round.
				{
					winnerInDraw = true;						// Updates boolean variable if they are.			
				}
			}													// Print deck for testing as cards have been added to the communal pile.
			System.out.println("THESE CARDS WERE ADDED TO THE COMMUNAL PILE:"); 
			printDeck();											
			
			// This section handles the case where the winner of the previous round was not part of the draw:
			if(!winnerInDraw)
			{
				for(int j = 0; j < PLAYERS; j++)
				{
					if(inRound[j] == 1 && players[j].seeIfInGame())
					{
						currentTurn = j;
																// The last player in players array who is in the draw is allowed to pick the category (biased).
					}
					
				}
			}
				JOptionPane.showMessageDialog(null,				// This pop-up informs the user a draw occurred and lets them know
				"It was a draw!",								// it was a draw.	
			    "Game Message",
			    JOptionPane.INFORMATION_MESSAGE);

				drawLastRound = true;							// Set that the last round was a draw.
		}
		
		
		// This section handles game-play where it was not a draw:
		else
		{
			numWins[player] += 1;								// Update the number of wins count for the winning player.
			currentTurn = player;								// Update the currentTurn to be the winner of the last round.
		for(int i = 0; i < PLAYERS; i++)						// Loop round all the players.
		{												
			if(inRound[i] == 1 && players[i].seeIfInGame())		// Checks to see if the player is in the round and in the game.
			{
				players[player].addCard(players[i].removeCard());
																// If they are, add the cards from that player to the back of the winners deck (including the winners card).
			}
		}
		
		
		// This is adding the cards from the deck to the winner of a draw.
		if(CurrentCards > 0)									// If there are cards in the communal pile
		{
			System.out.println("THESE CARDS WERE REMOVED FROM THE COMMUNAL PILE:");
			printDeck();										// Print the cards that leave the communal pile for testing specifications.
		}
		while(CurrentCards > 0)									// While there are still cards in the communal pile:
		{
			if(players[player].seeIfInGame())					// While the winning player is in the game.
			{
				players[player].addCard(deck[CurrentCards-1]);	// Add the card from the communal pile to the winner of the draw.
				removeCard(CurrentCards-1);						// Remove the card from the communal pile.
			}
		}
				
		if (player == 0){										// If user won the round:
			JOptionPane.showMessageDialog(null,					// display a pop-up letting you know you won the round.
					"You won!",
				    "Game Message",
				    JOptionPane.INFORMATION_MESSAGE);
		}
		else													// If comp won the round:
		{														// display a pop-up letting you know who won the round.
			JOptionPane.showMessageDialog(null,
					"Player "+(player)+" won!",
				    "Game Message",
				    JOptionPane.INFORMATION_MESSAGE);
		}
		drawLastRound = false;									// It wasn't a draw so set drawLastRound to false.
		}
		for(int h=0; h < PLAYERS; h++)							// Check to see if any players have lost all their cards.
		{														// If they have set them to be out the game.
			if(players[h].getCurrentCards() == 0)
			{
				players[h].setInGame(false);		
			}
		}
		printPlayersDeck();										// Print out each players deck who's in the game for testing purposes.
	}
	

	/**
	* This method selects the best category when it's the computers turn
	* and gets the corresponding scores from all the other players in the game.
	*
	* @return 	An array of the scores from each player's top card for the selected category.
	*/	
	public int[] pickCategoryAI()
	{
		int[] scores = new int[PLAYERS]; 					// Initializes an array to be able to hold the scores for each player.
		Card temp = players[currentTurn].seeTopCard();  	// The top card of the player whose turn it currently is.
		int category = temp.selectCategory();				// The index of the category with the highest score on the card.

		for(int i = 0; i < PLAYERS; i++)					// For all of the players:
		{													// if they're still in the game, and in the round
															// get the value from their top card that corresponds 
			if(players[i].seeIfInGame() && inRound[i] == 1)	// to the selected category and add it to the
			{												// corresponding index in scores.
				Card c = players[i].seeTopCard();
				int value = c.getValueAtIndex(category);
				scores[i] = value;
			}
			else
			{
				scores[i] = 0;								// If they're not in the game put 0 as their score.
			}
		}
		return scores;										// Return the array of scores.
	}
	
	
	/**
	* This method takes in as a parameter the index of the category selected by the user.
	* It then gets the corresponding scores from all the other players in the game.
	*
	* @param 	The index of the category selected by the user.
	*
	* @return 	An array of the scores from each player's top card for the selected category.
	*/
	public int[] pickCategoryHuman(int category)
	{
		int[] scores = new int[PLAYERS];									// Initializes an array to hold the scores for the number of players in the game.

		for(int i = 0; i < PLAYERS; i++)									// For all the players:
		{																	// if they're still in the game and they're in the round, 
			if(players[i].seeIfInGame() && inRound[i] == 1)					// get the value from their top card that corresponds to the 
			{																// selected category and add it it the corresponding index in scores.
			scores[i] = players[i].seeTopCard().getValueAtIndex(category);
			}
			else
			{
				scores[i] = 0;												// If they're not in the game set their score to zero.
			}
		}
		return scores;														// Return the array of scores.                                                                                
	}        
	
	
	/**
	* This method takes in the scores of all the players for the selected category and compares them to see
	* who was the winner. It then returns the index of the winning player or -1 if it was a draw.
	*
	* @param scores 	An array containing the scores of the players for that round.
	* @return 			The index of the winning player or -1 if a draw occurred.
	*/
	public int compareScores(int[] scores) 
	{
		int max = 0;								// Set the max index to the first one to begin.
		int draw = 0;								// This variable will be used to see if a draw occurred.
		
		for(int i = 1; i < scores.length; i++)		// Loop round the array of scores
		{											// setting max to the index of which
			if(scores[i] >= scores[max])			// player had the highest score.
			{
				max = i;
			}
		}
		
		// This section handles the case when it was a draw last round:
		if(drawLastRound)
		{
			printCurrentCardsIfDraw();					// For testing print out the current hand in the case of a draw.
			int[] temp = new int[PLAYERS];				// Initialise a temporary array to hold whether the players are in the round.
			for(int d = 0; d < PLAYERS; d++)
			{
				temp[d] = inRound[d];					// Set the temp array to the values of inRound.
				
			}
			for(int i = 0; i < scores.length; i++)		// Loop round all the scores and if max score equals
			{											// players score add one to draw and set that player
				if(scores[max] == scores[i])			// to be in the round.
				{
					inRound[i] = 1;
					draw += 1;
				}
				else 									// If max score doesn't equal the player's score
				{										// set them to not be in the round.
					inRound[i] = 0;
				}
			}
			if(draw == 1)								// It was not a draw so set all the players that were 
			{											// part of the draw last round to be in this round.
				for(int x = 0; x <PLAYERS; x++)
				{
					inRound[x] = temp[x];
				}
				return max;								// Return the index of the player who won the round.
			}
			else										// If it was a draw:
			{											// Keep inRound as it is as only players who were part of the last draw 
				return -1;								// and this draw will be in this round (e.g. draw between 3 then draw between 2).
			}											// return -1 as it was draw.
		}
		
		
		// This section handle the case where it was not a draw last round:
		else
		{
			printCurrentCardsInPlay();				// For testing print out the current hand in normal round.
		}
		
		for(int i = 0; i < scores.length; i++)		// Loop round all the scores and if max score equals
		{											// players score add one to draw and set that player
			if(scores[max] == scores[i])			// to be in the round.
			{
				inRound[i] = 1;
				draw += 1;
			}
			else 									// If max score doesn't equal the player's score
			{										// set them to not be in the round.
				inRound[i] = 0;
			}
		}		
		if(draw == 1)								// If draw equals one it was not a draw
		{				
			for(int i= 0; i <PLAYERS; i++)			// so set all players that are still in the game to be in the round.
			{
				if(players[i].seeIfInGame())
				{
					inRound[i] = 1;
				}
			}
			return max;								// Return the index of the player who won the round.
		}
		else										// If it was a draw:
		{											// Keep inRounds the same as only players that were part of the draw will be in the round.
			return -1;								// return -1.
		}
		
		
	}
	
	
	/**
	* Method to get the index of the player who's turn it is.
	*
	* @return 	The index of the player.
	*/
	public int getWhosTurn()
	{
		return currentTurn;
	}

	/**
	* Method to get the top card of the user so their card can be displayed in the GUI.
	*
	* @return 	The top card of the user.
	*/  
	public Card getHumanCard()
	{
		return players[0].seeTopCard();
	}

	

	/**
	* Method to get a Player that's in the game.
	*
	* @param index 	The index of the player you want to get.
	*
	* @return 		The player at that index.
	*/
	public Player getPlayerAt(int index)
	{
		return players[index];
	}
	

	/**
	* This method get's the index of the category currently selected for the AI player.
	* If it's called when it is currently the human players turn it returns -1.
	*
	* @return 	The index of the category that has been selected or -1 if it's the user's turn.
	*/
	public int getSelectedIndex(){
		
		Card temp = players[currentTurn].seeTopCard();
		int category = temp.selectCategory();
		return category;
		
	}
	
	/**
	 * This method checks if there is only one player left in the game
	 * and therefore if there's a winner.
	 * 
	 * @return	True if there's only one player left.
	 */
	public boolean onlyOnePlayerLeft()
	{
		int count = 0;
		for(int i = 0; i < PLAYERS; i++)
		{
			if(players[i].seeIfInGame() == false)
			{
				count +=1;
			}
		}
		if(count == PLAYERS - 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}








