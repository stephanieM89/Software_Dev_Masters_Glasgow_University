import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/*
 * @author Stephanie Man
 * Main method for the Lilybank Wine Merchant application 
 */

public class AssEx1 {

	public static void main(String[] args) {
		
		String customerName = "";
		double customerBalance = 0;
		boolean validInput = false;

		// Asks for customer's name, if field is empty program exits
		try {	
			customerName = JOptionPane.showInputDialog(null, "Enter Customer Name: ");
			if(customerName == null || customerName.length()==0 || customerName == ""){
				JOptionPane.showMessageDialog(null, "Error - no customer name entered", "Blank field error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);; 
			}
		} catch(Exception e) {
			System.exit(0);  // Program exits if cancel or close window buttons are pressed
		}
		
		// Asks for customer's starting balance and validates input is numerical
		try {
			while(!validInput) {
				try {
					customerBalance = Double.parseDouble(JOptionPane.showInputDialog(null, "Customer Balance: "));
					validInput = true;
				}catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid number entered - try again", "Invalid Number Error", JOptionPane.ERROR_MESSAGE);
					validInput = false;
				}
			}
			
		}catch(Exception e) {
			System.exit(0); // Program exits if cancel or close window are pressed
		}
		
		
		// Creates a new customer account object passing name and balance information
		CustomerAccount customerAccount = new CustomerAccount(customerName, customerBalance);
			
		// Runs the GUI passing customer account object					
		LWMGUI test = new LWMGUI(customerAccount);	
		
		
		
		
		
	}

}