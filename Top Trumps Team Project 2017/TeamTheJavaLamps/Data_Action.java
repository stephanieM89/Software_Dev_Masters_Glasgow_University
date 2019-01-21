import java.sql.*;

import javax.security.sasl.Sasl;


/**
 * 
 * The Data_Action class performs all the queries on the database.
 * 
*/
public class Data_Action 


{
	//instance variables
	private Connection connection = null;
	
	/**
	 * connect to the database
	 */
	public Data_Action()
	{
		 String dbname = "m_16_2284062p";
		 String username = "m_16_2284062p";
		 String password = "2284062p";
		
		try
		{
			connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" 
		+ dbname, username, password);
		}
		catch (SQLException e)
		{
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		if (connection != null)
		{
			System.out.println("Connection successful");
		}
		else
		{
			System.err.println("Failed to make connection!");
		}
	}
	
	/**
	 * 
	 * Closing the database connection.
	 * 
	 */
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
	

	
/**
 * method to add the results of a game to the database's Game table	
 * method takes the winner of the game, the number of draws, and the number of rounds played as a parameter
 * 
 * @param  winner, draw, noRounds
 *
*/
	public void addGame(String winner,int draw,int noRounds)
	{
		
		int gameNumber = setGameNo(getGameNo());								// stores the most recent game count
		
		
		Statement stmt = null;
		String query = "INSERT INTO Game (game_no, winner, draw, no_rounds) "
				+ "VALUES (" + gameNumber + ", " + "'" + winner + "' , " + draw + ", " + noRounds +");";
				

		try
		{
			stmt = connection.createStatement();
			stmt.executeUpdate(query);	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * method to write the number of rounds each player won in a single game to the database (second table: Rounds)
	 * takes as parameter the number of rounds won by each player
	 * note that the rounds won by players who did not participate in game is set to 0
	 * 
	 * @param roundsWon		the integer array of the rounds each player won
	 */
	
	public void addRounds(int[] roundsWon)
	{
		int gameNumber = getGameNo();		//gets the number of games stored in the database
		int humRounds = 0;					//initialise rounds won to 0 for all possible players
		int c1Rounds = 0;
		int c2Rounds = 0;
		int c3Rounds = 0;
		int c4Rounds = 0;
		
		if(roundsWon.length < 3)			//update rounds won to actual values when 2 players are playing
		{
			humRounds = roundsWon[0];
			c1Rounds = roundsWon[1];
		}
		
		if (roundsWon.length < 4)			//update rounds won to actual values when 3 players are playing
		{
			humRounds = roundsWon[0];
			c1Rounds = roundsWon[1];
			c2Rounds = roundsWon[2];
		}
		else if (roundsWon.length < 5)		//update rounds won to actual values when 4 players are playing
		{
			humRounds = roundsWon[0];
			c1Rounds = roundsWon[1];
			c2Rounds = roundsWon[2];
			c3Rounds = roundsWon[3];
		}
		
		else if (roundsWon.length == 5)		//update rounds won  for all 5 players
		{
			humRounds = roundsWon[0];
			c1Rounds = roundsWon[1];
			c2Rounds = roundsWon[2];
			c3Rounds = roundsWon[3];
			c4Rounds = roundsWon[4];
			
		}
		
		
		
		
		Statement stmt = null;
		String query = "INSERT INTO Rounds (gameno, human, comp1, comp2, comp3, comp4)" + "VALUES (" 
		+ gameNumber + ", " +  humRounds + " , " + c1Rounds + ", " + c2Rounds +" , " + c3Rounds + ", " 
				+ c4Rounds + ");"; 
	
		try
		{
			stmt = connection.createStatement();
			stmt.executeUpdate(query);	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * method to get the number of games that have already been played from the database
	 * @return count	 total number of games played 
	 * 
	 */
	public int getGameNo()
	{
		int games = 0;
		Statement stmt = null;
		
		String query = "SELECT COUNT (game_no) FROM Game;";
		
		try		
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
			games = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return games;
	}
	
	
	/**
	 * method to increase the game count by 1 to get the new game count that is then written to the database
	 * 
	 * @param games
	 * @return games		number of games + 1 
	 */
	public int setGameNo(int games)
	{
		games = getGameNo() + 1;
		return games;
	}
	
	
	/**
	 * method to count how many times the computer has won
	 * 
	 * @return number of times the computer has won  
	 */
	public int getCompWins()
	{
		int compWins = 0;
		Statement stmt = null;
		
		String query = "SELECT COUNT (winner) FROM Game WHERE Winner != 'Human';";
		
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				compWins = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return compWins;
	}
	
	
	/** 
	 * method to count how many times the human has won
	 * 
	 * @return humanWins	 number of times the human has won
	 * 
	 */
	public int getHumanWins()
	{
		int humanWins = 0;
		Statement stmt = null;
		
		String query = "SELECT COUNT (winner) FROM Game WHERE Winner = 'Human';";
		
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				humanWins = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return humanWins;
	}
	
	
	/**
	 * method to calculate the average number of draws
	 * 
	 * @return	avg		 average number of draws 
	 * 
	 */
	public double getAvgDraws()
	{
		double avgDraws = 0;
		Statement stmt = null;
		
		String query = "SELECT to_char(AVG(draw),'999D99') AS average_draw FROM Game;";
		
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				avgDraws = rs.getDouble(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		//round average number of draws to two decimal places
		double avg = Math.round(avgDraws * 100.0) / 100.0;
		
		return avg;
	}
	
	
	/**
	 * 
	 * method to determine the largest number of rounds played in a single game
	 * @return int maxRounds
	 */
	public int getMaxRounds()
	{
		int maxRounds = 0;
		Statement stmt = null;
		
		String query = "SELECT no_rounds FROM Game ORDER BY no_rounds DESC LIMIT 1;";
		
		
		try
		{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				maxRounds = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return maxRounds;
	}
	
	
}
