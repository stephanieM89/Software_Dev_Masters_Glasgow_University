import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/**
 *  @author Stephanie Man
 *  Display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener {
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	// Boolean set true if a file for processing ends in P, false if it ends C
	private boolean fileEndPC;
	
	// Monocipher object
	private MonoCipher mcipher;
	
	// VCipher object
	private VCipher vcipher;
	
	// Stores the filename entered in GUI
	private String fileName = "";
	
	// Stores the keyword entered in GUI
	private String keyword = "";
	
	// Stores the filename with the final character removed
	private String formattedFileName = "";

	// Creates the GUI by adding all components to the frame
	public CipherGUI() {
		this.setSize(400, 150);
		this.setLocation(100, 100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}

	// Helper method to add components to the frame
	public void layoutComponents() {
		// top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top, BorderLayout.NORTH);

		// middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle, BorderLayout.CENTER);

		// bottom panel is green and contains 2 buttons
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		
		// create mono button and add it to the bottom panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		
		// create vigenere button and add it to the bottom panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		
		// add the top panel
		this.add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Listens for and react to button press events
	 * @param the event
	 */
	public void actionPerformed(ActionEvent e) {
		//Determines whether monobutton or vigenere button pressed
		//Boolean fileFound used to determine whether processFile can run
		boolean fileFound = true;
		
		if (e.getSource() == monoButton) {
			if (getKeyword() && processFileName()) 
			{
				mcipher = new MonoCipher(keyword);
				fileFound = processFile(false);
			}
		} 
		else {
			if (getKeyword() && processFileName()) {
				vcipher = new VCipher(keyword);
				fileFound = processFile(true);
			}
		}
		
		//If processFile is unable to process, prints error to JPane 
		if (!fileFound){
			JOptionPane.showMessageDialog(null, "The file you have entered does not exist", "AE2",
					JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
		}
	}

	/**
	 * Obtains cipher keyword. If the keyword is invalid, error message is produced
	 * @return true if keyword is valid, false otherwise
	 */

	private boolean getKeyword() {
		boolean validKey = true;
		//Confirms that keyword has been entered, prints error message and returns boolean false otherwise
		keyword = keyField.getText();
		int keyLength = keyword.length();
		if (keyLength == 0) {
			JOptionPane.showMessageDialog(null,
					"Please enter a keyword", "AE2", JOptionPane.ERROR_MESSAGE);
			validKey = false;
		}
		
		//confirms keyword validity,prints error message and returns boolean false if invalid
		else if (!checkStringCase(keyword) || !checkDuplication(keyword)) {
			JOptionPane.showMessageDialog(null,
					"Please enter valid Keyword -" 
					+ " upper case alpha characters only, with no duplication", "AE2",
					JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			validKey = false;
		} 
		return validKey;
	}

	/**
	 * Checks if a string is all uppercase
	 * @param keyword - The string to check
	 * @return true if the file is all upper case, false otherwise
	 */
	private boolean checkStringCase(String keyword) {
		boolean upperCase = true;
		int keyLength = keyword.length();
		int i;
		for (i = 0; i < keyLength; i++) {
			if (keyword.charAt(i) < 'A' || keyword.charAt(i) > 'Z') {
				upperCase = false;
			}	
		}
		return upperCase;
	}

	/**
	 * Checks for duplicate characters in a string
	 * @param keyword
	 * @return notDuplication - true if all characters are unique
	 */
	private boolean checkDuplication(String keyword) {
		boolean notDuplication = true;
		int keyLength = keyword.length();
		
		for (int i = 0; i < keyLength; i++){
			for (int n = i + 1; n < keyLength; n++){
				if (keyword.charAt(i) == keyword.charAt(n)){
					notDuplication = false;
				}				
			}
		}
		return notDuplication;
		
	}

	/**
	 * Obtains filename from GUI. The details of the filename and the type of
	 * coding are extracted. If the filename is invalid, a message is produced
	 * The details obtained from the filename are stored in instance variables.
	 * 
	 * @return validName - true is valid filename, false otherwise
	 */
	private boolean processFileName() {
		boolean validName = false;
		fileName = messageField.getText();
		int len = fileName.length();  
			if (fileName.substring(len - 1).equals("P")) {// File is plaintext before encryption
				formattedFileName = fileName.substring(0, fileName.length() - 1); // Filename without last letter "P"
				fileEndPC = true;
				validName = true;
			} 
			else if (fileName.substring(len - 1).equals("C")) { // Cipher text file
				formattedFileName = fileName.substring(0, fileName.length() - 1);	// Filename without last letter "C"
				fileEndPC = false;
				validName = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "File names must end with 'C' or 'P'", "AE2",
						JOptionPane.ERROR_MESSAGE);
				messageField.setText("");
			}
		return validName;
	}

	/**
	 * Reads the input text file character by character, each character is
	 * encoded or decoded as appropriate and written to the output text file
	 * and information is passed to a LetterFrequencies object
	 * 
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return True if I/O operations were successful, false otherwise
	 */	
	private boolean processFile(boolean vigenere) {
		//Instantiates FileIO and LetterFrequencies objects, and readC int for characters
		//FileReader and FileWriter used for encryption/decryption
		//PrintWriter used for LetterFrequencies report 
		FileReader filein = null;
		FileWriter writer = null;
		PrintWriter printer = null;
		LetterFrequencies LF = new LetterFrequencies();
		int readC = 0;
		boolean fileProcessed = true;
		
		//Creates reader/writer objects, uses fileEndPC bool to decide whether encryption (true) or decryption (false)
		try {
			filein = new FileReader(fileName + ".txt");
			printer = new PrintWriter(formattedFileName + "F.txt");
			if (fileEndPC) {
				writer = new FileWriter(formattedFileName + "C.txt");
			}
			else {
				writer = new FileWriter(formattedFileName + "D.txt");
			}
			
			//Reads chars from file in turn. If upper case alpha, encodes/decodes. When file end reached, breaks.
			//uses fileEndPC and vigenere bools to determine encode/decode and mcipher/vcipher
			//If character non-uppercase alpha, writes directly to file uncoded
			char coded = 0;
			for (;;) {
				readC = filein.read();
				if (readC == -1) {
					break;
				} else {
					//Encode/decode characters, write information to fileout and LetterFrequencies object
					if (fileEndPC && !vigenere) {
						coded = mcipher.encode((char) readC);
					}
					else if (!fileEndPC && !vigenere) {
						coded = mcipher.decode((char) readC);
					}
					else if (fileEndPC && vigenere) {
						coded = vcipher.encode((char) readC);
					}
					else {
						coded = vcipher.decode((char) readC);
					}
					writer.write(coded);
					LF.addChar(coded);
				}
			}
			
			//produces letter frequencies report, closes fileIO objects and closes GUI 
			printer.print(LF.getReport());
			printer.close();
			filein.close();
			writer.close();
			System.exit(0);
			} 
		catch (IOException e) {
			fileProcessed = false;
		}
		return fileProcessed;
	}
	
}