/**
 * @author Stephanie Man
 * Contains monoalphabetic cipher and methods to encode and 
 * decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char[] alphabet;

	/** The cipher array. */
	private char[] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword) {
		// create alphabet
		alphabet = new char[SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char) ('A' + i);

		// create cipher, starting with reversed alphabet
		cipher = new char[SIZE];
		for (int i = 0; i < SIZE; i++)
			cipher[i] = (char) ('Z' - i);

		//Creates monocipher from input keyword
		int keyLength = keyword.length();
		for (int i = keyLength; i > 0; i--) {
			int matchElement = 0;
			for (int a = 0; a < SIZE; a++) {
				if (keyword.charAt(i - 1) == cipher[a]) {
					matchElement = a;
					break;
				}
			}
			for (int b = matchElement; b > 0; b--) {
				cipher[b] = cipher[b - 1];
			}
			cipher[0] = keyword.charAt(i - 1);
		}
		
		//Prints cipher to console
		System.out.println("Monocipher is:");
		for (int i = 0; i < 26; i++) {
			System.out.print(cipher[i]);
		}
	}

	/**
	 * Encodes upper case Alpha characters, returns other characters untouched
	 * @param ch the character to be checked for encoding
	 * @return the character, whether encoded or not
	 */	
	public char encode(char ch) {
		char outputCH = ch;
		if (ch >= 'A' && ch <='Z') {
			for (int a = 0; a < SIZE; a++) { 
				if (ch == alphabet[a]) {
					outputCH = cipher[a];
					break;
				}
			}
		}
		return outputCH;
	}

	/**
	 * Decodes upper case Alpha characters, returns other characters untouched
	 * @param ch the character to be checked for decoding
	 * @return the character, whether decoded or not
	 */
	public char decode(char ch) {
		char outputCH = ch;
		if (ch >= 'A' && ch <='Z') {
			for (int a = 0; a < SIZE; a++) { 
				if (ch == cipher[a]) {
					outputCH = alphabet[a];
					break;
				}
			}
		}
		return outputCH;
	}
}