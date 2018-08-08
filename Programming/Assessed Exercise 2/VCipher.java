/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */

public class VCipher
{
	/**Array to store the alphabet */
	char [] alphabet; 
	
	/**Size of the alphabet */
	int SIZE = 26;
	
	/**Array to store VCipher */
    char [][] cipher;
    
    /**Counter for incrementing through 2d array when encoding/decoding */
    int counter = 0;
    
    /** length of input keyword */
	int keyLength;
			
	public VCipher(String keyword)
	{
		
		// create alphabet array
		alphabet = new char[SIZE];
		for (int i = 0; i < SIZE; i++)
		{
			alphabet[i] = (char) ('A' + i);
		}
		
		//Creates 2d array to hold cipher
		int n = 0;
		keyLength = keyword.length();
		cipher = new char [SIZE][keyLength]; // creates array
		
		//Creates the VCipher
		for (n = 0; n < keyLength; n++)
		{ 
			char cipherChar = keyword.charAt(n);
			for (int i = 0; i < SIZE; i++)
			{ 
				if (cipherChar <= 'Z')
				{
					cipher[i][n] = cipherChar;
				}
				if (cipherChar > 'Z')
				{
					cipherChar = 'A';
					cipher[i][n] = cipherChar;
				}
			cipherChar++;
			}
		
		}
		 //Prints VCipher to console
		System.out.println ("The vCipher is:");
		String vCiphStr = "";
		for (n = 0; n < keyLength; n++)
		{ 
			vCiphStr+= "\n";
			for (int i = 0; i < SIZE; i++)
			{
				vCiphStr += cipher [i][n] + " ";
			}
		}
		System.out.println(vCiphStr);
	}
			
	/**
	 * Encodes upper case Alpha characters, returns other characters untouched
	 * @param ch the character to be checked for encoding
	 * @return outputCH the character, whether encoded or not
	 */	
	public char encode(char ch)
	{
		char outputCH = ch;
		if (ch >= 'A' && ch <='Z')
		{
			int i;
			for(i = 0;  i < SIZE; i++)	
			{
				if (ch == alphabet[i])
				{
					break;
				}
			}
			outputCH = cipher [i][counter]; 
			counter++;					
			if (counter >= keyLength)
			{
				counter = 0;
			}
		}
		return outputCH;	
	}
	
	/**
	 * Decodes upper case Alpha characters, returns other characters untouched
	 * @param ch the character to be checked for decoding
	 * @return the character, whether decoded or not
	 */	
	public char decode(char ch)
	{	
		char outputCH = ch;
		if (ch >= 'A' && ch <='Z')
		{
			int i;
			for(i = 0;  i < SIZE; i++)
			{
				if (ch == cipher[i][counter])
				{
					break;
				}
			}
			counter++;
			if (counter >= keyLength)
					counter = 0;
			outputCH = alphabet [i];
		}
		return outputCH;
	}
}