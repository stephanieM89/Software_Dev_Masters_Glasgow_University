import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;


/**
 * Class to define window in which game is played.
 */
public class GameGUI extends JFrame implements ActionListener {
	// Instance Variables
	/** Main Display */

	private final String resultsFile = "ResultsOut.txt";
	
	private JButton startButton, statsButton, saveButton, selectButton;
	private JRadioButton [] radButtonArray;
	private JTextArea [] hands, values;
	private JTextArea player0, player1, player2, player3, player4, description, categoryLabel1,
						categoryLabel2, categoryLabel3, categoryLabel4, categoryLabel5, hand1, hand2, hand3, hand4, hand5, value1, value2, value3, value4, value5,selectedCat,
						category1, category2, category3, category4, category5, communal;
	private JComboBox numberPlayers;
	private ButtonGroup group;
	private String titles;
	private Game game;
	private int numberSelected;
	private Data_Action database;
	private ReportFrame scoresReport;
	private String [] categoryArray;

	
	
	
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public GameGUI() {
																// Set the GUI window size, title and closer operations
		setDefaultCloseOperation(EXIT_ON_CLOSE);				
		setTitle("Dinosaur Top Trumps");						
		setSize(450, 600);
																// Create GUI Grid layout with enough rows for each widget
		GridLayout guiGrid = new GridLayout(15,1);
		setLayout(guiGrid);
		this.layoutGrid();		
		
		database = new Data_Action();							// Create the database object 

		
	}
	
	public void layoutGrid(){
																		
		JPanel guiPane1 = new JPanel(new FlowLayout());			// Create all required JPanels for each row of the grid layout
		JPanel guiPane2 = new JPanel(new FlowLayout());
		JPanel guiPane3 = new JPanel(new FlowLayout());
		JPanel guiPane4 = new JPanel(new FlowLayout());
		JPanel guiPane5 = new JPanel(new FlowLayout());
		JPanel guiPane6 = new JPanel(new FlowLayout());
		JPanel guiPane7 = new JPanel(new FlowLayout());
		JPanel guiPane8 = new JPanel(new FlowLayout());	
		JPanel guiPane9 = new JPanel(new FlowLayout());	
		JPanel guiPane10 = new JPanel(new FlowLayout());
		JPanel guiPane11 = new JPanel(new FlowLayout());
		JPanel guiPane12 = new JPanel(new FlowLayout());
		JPanel guiPane13 = new JPanel(new FlowLayout());
		JPanel guiPane14 = new JPanel(new FlowLayout());
		JPanel guiPane15 = new JPanel(new FlowLayout());

		startButton = new JButton("New Game");					// Create row 1 with the new game and view stats button
		statsButton = new JButton("View Game Stats");
		saveButton = new JButton ("Save Stats to File");
		saveButton.setEnabled(false);
		guiPane1.add(startButton);
		guiPane1.add(statsButton);
		guiPane1.add(saveButton);
		
																
		String number [] = new String[] {"", "2 players", 		// Create row 2 with the number of players drop down option
				"3 players", "4 players", "5 players"};
		numberPlayers = new JComboBox(number);
		JLabel numberLabel = new JLabel("Select number of Players:");
		guiPane2.add(numberLabel);
		guiPane2.add(numberPlayers);
		
		
		description = new JTextArea(1,30);						// Create row 3 with Card Description information
		description.setEnabled(false);
		guiPane3.add(description);
		
		
		radButtonArray = new JRadioButton [5];					// Create row 4 with the first category and radio button
		categoryLabel1 = new JTextArea(1,15);
		radButtonArray[0] = new JRadioButton();
		category1 = new JTextArea(1,3);
		category1.setEnabled(false);
		guiPane4.add(categoryLabel1);
		guiPane4.add(category1);
		guiPane4.add(radButtonArray[0]);
		
	
		categoryLabel2 = new JTextArea(1,15);					// Create row 5 with second category and radio button
		radButtonArray[1] = new JRadioButton();
		category2 = new JTextArea(1,3);
		category2.setEnabled(false);
		guiPane5.add(categoryLabel2);
		guiPane5.add(category2);
		guiPane5.add(radButtonArray[1]);
		

		categoryLabel3 = new JTextArea(1,15);					// Create row 6 with third category and radio button
		radButtonArray[2] = new JRadioButton();
		category3 = new JTextArea(1,3);
		category3.setEnabled(false);
		guiPane6.add(categoryLabel3);
		guiPane6.add(category3);
		guiPane6.add(radButtonArray[2]);
		

		categoryLabel4 = new JTextArea(1,15);					// Create row 7 with fourth category and radio button
		radButtonArray[3] = new JRadioButton();
		category4 = new JTextArea(1,3);
		category4.setEnabled(false);
		guiPane7.add(categoryLabel4);
		guiPane7.add(category4);
		guiPane7.add(radButtonArray[3]);
		
	
		categoryLabel5 = new JTextArea(1,15);					// Create row 8 with fifth category and radio button
		radButtonArray[4] = new JRadioButton();
		category5 = new JTextArea(1,3);
		category5.setEnabled(false);
		guiPane8.add(categoryLabel5);
		guiPane8.add(category5);
		guiPane8.add(radButtonArray[4]);
		
		
		selectButton = new JButton("Select Category");			// Create row 9 with category select button
		guiPane9.add(selectButton);
		selectButton.setEnabled(false);
		
		JLabel turnHeading = new JLabel("The current "			//	Create row 10 with information about the highlighted player
				+ "player is highlighted in pink");
		guiPane10.add(turnHeading);
		
		player0 = new JTextArea(1,7);							// Create row 11 with row of player headings
		player1 = new JTextArea(1,7);
		player2 = new JTextArea(1,7);
		player3 = new JTextArea(1,7);
		player4 = new JTextArea(1,7);
		
		guiPane11.add(player0);
		guiPane11.add(player1);
		guiPane11.add(player2);
		guiPane11.add(player3);
		guiPane11.add(player4);
		
		player0.setEnabled(false);								// Ensure all player headings are disabled
		player1.setEnabled(false);
		player2.setEnabled(false);
		player3.setEnabled(false);
		player4.setEnabled(false);
		

		hands = new JTextArea[]{hand1,hand2,					// Create row 12 with row of player hand information
				hand3,hand4,hand5};
		for (int i =0; i<hands.length; i++)
		{
			hands[i] = new JTextArea(1,7);
			guiPane12.add(hands[i]);
			hands[i].setEnabled(false);
		}
		

		JLabel categoryLabel = new JLabel("The chosen "			// Create row 13 with information about the chosen category
				+ "category is: ");
		selectedCat = new JTextArea(1,7);
		guiPane14.add(categoryLabel);
		guiPane14.add(selectedCat);
		

		values = new JTextArea[]{value1,value2,value3,			// Create row 14 with information about the scores from each round
				value4,value5};
		for (int i =0; i<values.length; i++)
		{
			values[i] = new JTextArea(1,7);
			guiPane13.add(values[i]);
			values[i].setEnabled(false);
		}
		

		communal = new JTextArea(1,15);							// Create row 15 with information about the cards in the communal pile
		guiPane15.add(communal);
		communal.setEnabled(false);

		
		this.add(guiPane1);										// Add each panel to the GUI grid
		this.add(guiPane2);
		this.add(guiPane3);
		this.add(guiPane4);
		this.add(guiPane5);
		this.add(guiPane6);
		this.add(guiPane7);
		this.add(guiPane8);
		this.add(guiPane9);
		this.add(guiPane10);
		this.add(guiPane11);
		this.add(guiPane12);
		this.add(guiPane13);
		this.add(guiPane14);
		this.add(guiPane15);
		
		
		group = new ButtonGroup();								// Group all the radio buttons so that only one can be selected at a time
		int i = 0;
		while (i<radButtonArray.length)
		{
			group.add(radButtonArray[i]);
			i++;
		}
		
		startButton.addActionListener(this);					// Add action listeners to the buttons and drop down list
		numberPlayers.addActionListener(this);
		selectButton.addActionListener(this);
		statsButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		this.setVisible(true);									// Set the GUI to be visible
		
	}
	
	/**
	 *  Method which reads in the deck file and initialises the deck
	 *  The method also sets the title string to be the first row
	 *  of the input text file.
	 */
	
	public void initDeck()
	{
		try
		{
				FileReader reader = new FileReader("deck.txt");		// Read in the file and scan through the first line and set this to title
				Scanner in = new Scanner(reader);
				titles = in.nextLine();
				while(in.hasNext())									// Loop through the remaining lines in the input file and store the information as cards
				{
					String s = in.nextLine();
					Card temp = new Card(s);
					game.addCard(temp);
				}
				in.close();
		}
		catch(IOException e) 										// Exception thrown if the file is not found
		{
			JOptionPane.showMessageDialog(null,
					"File Not Found",
				    "Error Reading File",
				    JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	
	/**
	 *  Method to initialise the category labels which is called 
	 * at the start of a new game
	 */
	public void initCategories(){
		
					categoryArray = titles.split(" ");				// Create an array of strings from the title string
					
					String c1 = categoryArray[0];
					String c2 = categoryArray[1];
					String c3 = categoryArray[2];
					String c4 = categoryArray[3];
					String c5 = categoryArray[4];
					String c6 = categoryArray[5];
					
					getFormat(categoryLabel1);						// Set each category label to the information from the title string
					categoryLabel1.setText(c2);
					
					getFormat(categoryLabel2);
					categoryLabel2.setText(c3);
					
					getFormat(categoryLabel3);
					categoryLabel3.setText(c4);
					
					getFormat(categoryLabel4);
					categoryLabel4.setText(c5);
					
					getFormat(categoryLabel5);
					categoryLabel5.setText(c6);
		
	}
	
	/**
	 *  Method to initialise the GUI display with the number of
	 *  players selected by the user
	 */
	public void initDisplay(){
		
		player0.setText("");										// Clear previous player selection
		player1.setText("");
		player2.setText("");
		player3.setText("");
		player4.setText("");
		
																	// Set number of players depending on the users choice from the drop down menu
		String choice = (String)numberPlayers.getSelectedItem();
		 if (choice == null || choice == "")
		 {	
				numberSelected = 0;
		 }
			 if (choice.equals("2 players"))
			 {
				player0.setText("You");
				player1.setText("Player 1");	
				numberSelected = 2;
			}
			else if (choice.equals("3 players"))
			{
				player0.setText("You");
				player1.setText("Player 1");
				player2.setText("Player 2");
				numberSelected = 3;
			}
			else if (choice.equals("4 players"))
			{
				player0.setText("You");
				player1.setText("Player 1");
				player2.setText("Player 2");
				player3.setText("Player 3");
				numberSelected = 4;
			}
			else if (choice.equals("5 players"))
			{
				player0.setText("You");
				player1.setText("Player 1");
				player2.setText("Player 2");
				player3.setText("Player 3");
				player4.setText("Player 4");
				numberSelected = 5;
			}			
		}


	/**
	 *  Method which disables the buttons during game play
	 */
	public void disableButtons()
	{
		selectButton.setEnabled(false);
		group.clearSelection();
		startButton.setEnabled(false);
		
	}
	
	/**
	 * Method which enables the buttons required during game play
	 */
	public void enableButtons()
	{
		selectButton.setEnabled(true);
		group.clearSelection(); 
	}
	
	/**
	 * Method to highlight the current player in pink
	 * @param input 	input is the index of the current player
	 */
	public void highlight(int input)
	{

		JTextArea turn [] = new JTextArea[] {player0, player1, player2, player3, player4};
		
		for (int i =0; i<turn.length; i++)
		{
			
			if (i == input)
			{
				turn[i].setBackground(Color.pink);				// If current turn set text area background to pink
			}
			else
			{
				turn[i].setBackground(Color.white);				// If not current turn set text are background to white
			}
		}
	}

	/**
	 * Method to format the card text
	 * @param input		The input is the current JTextArea to be formatted
	 */
	public void getFormat(JTextArea input)
	{
		input.setFont(new Font("Courier", Font.BOLD, 20));		// Sets the text to bold and light grey
		input.setBackground(Color.LIGHT_GRAY);
	}
	
	/**
	 * Method to return the users selected category from the
	 * radio buttons
	 * @return		returns an integer which represents which button was selected
	 */
	// Method to return users selection
	public int getCategory()
	{
		for (int i =0; i<radButtonArray.length; i++)
		{
			if (radButtonArray[i].isSelected())
			{
				return i;
			}	
		}
		return -1;
	}
	
	
	/**
	 *  Method which updates the GUI displays throughout game play.
	 *  The updates are made to the values of each category so that
	 *  the users top card is always on display.
	 *  The method also updates the number of cards in each players
	 *  hand and the number of cards in the communal pile.
	 */
	public void updateDisplay(){
			
		if (game.getPlayerAt(0).seeIfInGame()==true)							// Check if the user is still in the game
		{
		
		Card temp = game.getHumanCard();										// Get the users current top card
		
		getFormat(description);		
		description.setText(temp.getDescription());								// Set the description from the current card
		
		getFormat(category1);
		category1.setText(""+temp.getValueAtIndex(0));							// Set the category 1 value from the top card
		
		getFormat(category2);
		category2.setText(""+temp.getValueAtIndex(1));							// Set the category 2 value from the top card
		
		getFormat(category3);
		category3.setText(""+temp.getValueAtIndex(2));							// Set the category 3 value from the top card
		
		getFormat(category4);
		category4.setText(""+temp.getValueAtIndex(3));							// Set the category 4 value from the top card
		
		getFormat(category5);
		category5.setText(""+temp.getValueAtIndex(4));							// Set the category 5 value from the top card

		}
		
		else
		{
			description.setText("");											// If the user is not in the game then set the card in the GUI to blank								
			category1.setText("");
			category2.setText("");	
			category3.setText("");	
			category4.setText("");	
			category5.setText("");
		}
		
		communal.setText("Communal cards: "+ game.getCardsinDeck() );			// Set number of cards in the communal pile
		
		for (int i = 0; i<numberSelected; i++ )									// Set the number of cards in each players hand
		{
			hands[i].setText("Cards: "+game.getPlayerAt(i).getCurrentCards());
		}		
	}

	
	/**
	 * Method to save results to text file. The method accesses
	 * the database to get the required information.
	 * @throws IOException
	 */
	public void saveFile() throws IOException
	{
		File fout = new File(resultsFile);										// Create output file and file printer
		PrintWriter writer = new PrintWriter(fout);							
	
		String [] file = {"Game Statistics", "Games: " + database.getGameNo(),	// Create string array with the required information
				"Computer Wins: " + database.getCompWins(), 
				"Human Wins: " + database.getHumanWins(), 
				"Average Draws: " + database.getAvgDraws(),
				"Largest Round: " + database.getMaxRounds()};
	
		String rows = "";
		
		for (int i = 0; i < file.length; i++)									// Loop through adding each value from the string array 
		{																		// to an individual row
			rows = rows + file[i] + "\r\n";
		}
	
		writer.write(rows);														// Write the aligned rows to file
		writer.close();															// Close the file printer
	}
	

	
	/**
	 * Method to set the individual players scores for each round.
	 * The method also prints this information to the console for testing purposes.
	 * @param scores
	 */
	public void setValues(int[] scores)
	{
		System.out.println("--------------------------------------------");
		System.out.println("THE VALUES FOR THE SELECTED CATEGORY");
		for (int i =0; i<scores.length; i++)
		{
			values[i].setText("Score: "+scores[i]);
			System.out.println(+scores[i]);	
		}
		System.out.println("--------------------------------------------");
	}
	
	/**
	 * Method to set the category selection in the GUI.
	 * The method also prints this out to the console for testing purposes.
	 */
	public void setSelection()
	{
		int topScoreIndex = 0;
		if (game.getWhosTurn() == 0)
		{
			topScoreIndex = getCategory();
		}
		else
		{
			topScoreIndex = game.getSelectedIndex();
		}
		
		selectedCat.setText(categoryArray[topScoreIndex+1]);
			
		System.out.println("--------------------------------------------");
		System.out.println("CATEGORY SELECTED IS: " + categoryArray[topScoreIndex+1] );
		System.out.println("--------------------------------------------");
			
			
	}
	
	/**
	 * Method which calls the required game play methods in the correct
	 * order and continues to loop through until it is the users turn
	 * or until the game is over.
	 */
	public void playGame() 
	{
		while(!game.onlyOnePlayerLeft())
		{
			statsButton.setEnabled(false);
			saveButton.setEnabled(false);
			
			while(!(game.getWhosTurn() == 0) && !game.onlyOnePlayerLeft())		// While it is the computers turn and it is not the end of the game
			{
				highlight(game.getWhosTurn());									// Highlight the current player
				updateDisplay();												// Update the GUI display
				disableButtons();												// Disable buttons not required during computer turn
				int[] temp = game.pickCategoryAI();								// Returns an array of integers of each players scores for the selected category
				setSelection();													// Sets the category selection in the GUI
				setValues(temp);												// Sets the values of each players score in the GUI
				int winner = game.compareScores(temp);							// Returns an integer of the winners index
				game.winHand(winner);											// Deals with redistributing the cards to the winner and deals with a draw scenario
				
			
		
			}
			
			while(game.getWhosTurn() == 0 )										// While it is the users turn and it is not the end of the game
			{
				highlight(game.getWhosTurn());									// Highlight the current player
				updateDisplay();												// Update the GUI display
				enableButtons();												// Enable the buttons required for the users turn
				return;															// Return to action performed method to wait for users selection
			}
		}
		
		if(game.getCardsinDeck() > 0)
		{
			game.winHand(game.getWhosTurn());
		}
		
		endOfGame();
			
	}
	
	/**
	 *  Method to save the game to the database
	 */
	
	public void saveGame()
	{

		int draw = game.getNumDraws();						// Save to database table 1
		int noRounds = game.getNumRounds();
		int winner = game.getWhoWon();
		String winningPlayer ="";
		
		if (winner ==0 )
		{
			winningPlayer = "Human";
		}
		else
		{
			winningPlayer = "Comp"+winner;
		}
		
		
		database.addGame(winningPlayer, draw, noRounds);
		database.addRounds(game.getNumWins());				// Save number of rounds won to database table 2
	}
	
	/**
	 * This method is used to print out who won the game to the console for
	 * testing purposes
	 * @param winner
	 */
	public void printWinner(int won){
		
		System.out.println("--------------------------------------------");
		if(won == 0)
		{
		System.out.println("THE WINNER OF THE GAME IS: YOU!!!!!!!!");
		}
		else
		{
			System.out.println("THE WINNER OF THE GAME IS: PLAYER" + won);
		}
	}
	
	/**
	 *  This method is used to deal with everything that happens when a game
	 *  ends. It prints out the winner of the game to the console for testing.
	 *  The method also enables all the required buttons so that the player can
	 *  play again or view/save statistics. The method also creates a Joption
	 *  pane that asks the user if they want to save the results to a file.
	 */
	
	public void endOfGame()
	{
		updateDisplay();																// On completion of the game update the display one last time
		
		int options;
		if(game.getWhoWon() == 0)														// Print the winner out. A different message used when user wins.
		{
			System.out.println("THE WINNER OF THE GAME IS: YOU!!!!!!!!");
			options = JOptionPane.showConfirmDialog(null, "Human" + " won the game! " + "Would you like to save the game?");
		}
		else
		{
			System.out.println("THE WINNER OF THE GAME IS: PLAYER " + game.getWhoWon());
			options = JOptionPane.showConfirmDialog(null, "Comp "+ game.getWhoWon() + " won the game! " + "Would you like to save the game?");
		}																				
		startButton.setEnabled(true);													// Enable the start, view and save button
		statsButton.setEnabled(true);
		saveButton.setEnabled(true);
		
		if (options == JOptionPane.YES_OPTION)											// Provide an option to save the game
		{
			saveGame();

																						//Second JOption Pane to output result file 
			int file = JOptionPane.showConfirmDialog(null, "Save results to file?", "File Output", JOptionPane.YES_NO_OPTION);
				if(file == JOptionPane.YES_OPTION)
				{
					try
					{
					saveFile();
					}
					catch (Exception e)
					{
						
					}
				}
		}
	}

	/**
	 * This method listens for the user input. It controls what happens
	 * when the user starts a new game and selects a category during game play.
	 * It also controls the user selecting to view or save files.
	 */
	public void actionPerformed(ActionEvent	e) {
		
		if (e.getSource() == statsButton)						// Deal with statistics button selection
		{
			scoresReport = new ReportFrame(database);			// Create JFrame and set to visible
			scoresReport.setVisible(true);
		}
		
		
		if (e.getSource() == saveButton)						// Deal with save button selection
		{
			try
			{
			saveFile();											// Save the file
			}
			catch (Exception x)									// Catch file format exception
			{
				
			}
		}
		
		
		if (e.getSource()==startButton){						// Deal with start button being selected
		
			initDisplay();										// Initialise the GUI display
		
			if (numberSelected == 0)
			{
				JOptionPane.showMessageDialog(null,"Please "	// Check that the user has selected a number of players
						+ "select the number of players", "Input Error",JOptionPane.ERROR_MESSAGE);
			}
			else{
				game = new Game(numberSelected);				// Create a new game object and pass it the number of players
				initDeck();										// Initialise the deck of cards
				initCategories();								// Initialise the categories and display them in the GUI
				game.printDeck();								// Print the deck for testing
				game.shuffle();									// Shuffle the deck
				game.printDeck();								// Print the shuffled deck for testing
				game.deal();									// Deal out the cards to the current players
				game.printPlayersDeck();						// Print out each players deck
				updateDisplay();								// Update the GUI display
				game.whoStarts();								// Pick the first player to start at random
				this.highlight(game.getWhosTurn());				// Highlight the first player
				playGame();										// Start playing the game
				}
				
				
			}
			
		
		
		if (e.getSource()==selectButton)
		{														// Deal with select button being selected
			
			if (getCategory()==-1)
			{													// If the user has not selected a radio button before pressing select then display a warning
				JOptionPane.showMessageDialog(null,"Please "
						+ "Pick a category", "Input Error",JOptionPane.ERROR_MESSAGE);
			}
			else{

				int[] temp = game.pickCategoryHuman(getCategory());	// Returns an array of scores for each player based on the users selected category
				setSelection();										// Update category displayed in GUI
				setValues(temp);									// Update the players scores displayed in the GUI
				int winner = game.compareScores(temp);				// Returns an integer of the winners index                                          
				game.winHand(winner);                               // Deals with redistributing the cards to the winner and deals with a draw scenario 
				playGame();											// Re enter the playGame method

			}
		}
	}	
}
	

