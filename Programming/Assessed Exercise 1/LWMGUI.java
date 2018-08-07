import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *  @author Stephanie Man
 *  Creates the GUI interface and methods to handle button events
 */

public class LWMGUI extends JFrame implements ActionListener {
	
	private CustomerAccount customerAccount;
	private JButton saleButton, returnButton;
	private JTextField wineNameField, quantityField, priceField, transactionField, balanceField;
	private JLabel wineLabel, quantityLabel, priceLabel, transactionLabel, balanceLabel, itemLabel;
	private String wineName;
	private double winePrice;
	private int wineQuantity;
	
	private static final int FRAME_HEIGHT = 400;
	private static final int FRAME_WIDTH = 500;

	// Creates the GUI and passes in customer account details
	public LWMGUI(CustomerAccount customerAccount) {

		this.customerAccount = customerAccount;
		
	// Creates frame elements
		createNameField();
		createQuantityField();
		createPriceField();
		createSaleButton();
		createReturnButton();
		createItemField();
		createTransactionField();
		createBalanceField();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
	// Initialises frame properties
		this.setTitle("Lilybank Wine Merchants: " + customerAccount.getCustomerName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	// Displays customer's starting balance when GUI launches
		updateBalance();
	}

	// Retrieve's customer balance: a negative number shows account is in credit
	// and is converted to positive by negating negative value (-) and attaching the sufficx 'CR'
	private void updateBalance() {
		double customerBalance = customerAccount.getCustomerBalance();
		if(customerBalance<0) {
			transactionField.setText(String.format("£%.2f", customerAccount.getTransactionAmount()));
			balanceField.setText(String.format("£%.2f CR", -(customerAccount.getCustomerBalance())));
		} else {
			transactionField.setText(String.format("£%.2f", customerAccount.getTransactionAmount()));
			balanceField.setText(String.format("£%.2f", customerAccount.getCustomerBalance()));
		}
	}
	
	// Delete data from the name, quantity and price fields
	private void clear() {
		wineNameField.setText("");
		quantityField.setText("");
		priceField.setText("");
	}
	
	// Sale is processed by creating a wine object and passing to calculateSale() method in customer account
	private void processSale() {
		Wine wine = new Wine(wineName, winePrice, wineQuantity); 
		customerAccount.calculateSale(wine);
		
		// Check whether single or multiple bottles of wine purchased 
		if(wineQuantity > 1) {
			itemLabel.setText("Wine purchased: "+ wine.getWine() + " (" + wine.getQuantity() + " bottles)");;
		}
		else {
			itemLabel.setText("Wine purchased: "+ wine.getWine() + " (" + wine.getQuantity() + " bottle)");;
		}
		
		updateBalance();
		clear();
	}
	
	// Process return by creating a wine object and passing to calculateReturn() method in customer account
	private void processReturn() {
		Wine wine = new Wine(wineName, winePrice, wineQuantity);
		customerAccount.calculateReturn(wine);
		
		// Check whether single or multiple bottles of wine purchased 
		if(wineQuantity > 1) {
			itemLabel.setText("Wine returned: "+ wine.getWine() + " (" + wine.getQuantity() + " bottles)");;				
		} else {
			itemLabel.setText("Wine returned: "+ wine.getWine() + " (" + wine.getQuantity() + " bottle)");;			
		}

		updateBalance();
		clear();
	}
	// Create wine name label and field
	private void createNameField() {
		wineLabel = new JLabel("Name: ");
		final int FIELD_WIDTH = 15;
		wineNameField = new JTextField(FIELD_WIDTH);
	}
	
	// Create quantity label and field
	private void createQuantityField() {
		quantityLabel = new JLabel("Quantity: ");
		final int FIELD_WIDTH = 5;
		quantityField = new JTextField(FIELD_WIDTH);
	}
	
	// Create price label and field
	private void createPriceField() {
		priceLabel = new JLabel("Price: ");
		final int FIELD_WIDTH = 6;
		priceField = new JTextField(FIELD_WIDTH);  
	}
	
	// Create sale button and attaches ActionListener
	private void createSaleButton() {
		saleButton = new JButton("Process Sale");
		saleButton.addActionListener(this);
	}
	
	// Create return button and attaches ActionListener
	private void createReturnButton() {
		returnButton = new JButton("Process Return");
		returnButton.addActionListener(this);
	}
	
	// Create item label and field
	private void createItemField() {
		itemLabel = new JLabel("");
	}
	
	// Create transaction label and field
	private void createTransactionField() {
		transactionLabel = new JLabel("Transaction amount: ");
		final int FIELD_WIDTH = 8;
		transactionField = new JTextField(FIELD_WIDTH);
		transactionField.setEditable(false);
	}
	
	// Create balance label and field
	private void createBalanceField() {
		balanceLabel = new JLabel("Current balance: ");
		final int FIELD_WIDTH = 8;
		balanceField = new JTextField(FIELD_WIDTH);
		balanceField.setEditable(false);
	}
	
	// Creates the pancels and attach labels, fields and buttons in layout of 4 rows and 1 column
	private void createPanel() {
		setLayout(new GridLayout(4,1));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		// Top/First panel: name, quantity and price of wine
		panel1.add(wineLabel);
		panel1.add(wineNameField);
		panel1.add(quantityLabel);
		panel1.add(quantityField);
		panel1.add(priceLabel);
		panel1.add(priceField);
		
		// Middle panel: sale and return buttons
		panel2.add(saleButton);
		panel2.add(returnButton);
		
		// Middle panel: confirmation of name and quantity of items sold or returned
		panel3.add(itemLabel);
		
		// Bottom panel: transaction and balance amounts
		panel4.add(transactionLabel);
		panel4.add(transactionField);
		panel4.add(balanceLabel);
		panel4.add(balanceField);
		
		// Adds panels to frame
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		
	}
	
	// Validates field data (with error messages) then confirms which button pressed and either
	// calls processSale() or processReturn()

	@Override
	public void actionPerformed(ActionEvent event) {
		
		wineName = wineNameField.getText();
		
		if(wineNameField.getText().isEmpty() || quantityField.getText().isEmpty() || priceField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fields cannot be empty - try again", "Blank Field Error", JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			try {
				wineQuantity = Integer.parseInt(quantityField.getText());
				if(wineQuantity < 0) { // Quantity can't be a negative
					JOptionPane.showMessageDialog(null, "Quantity must be a positive number - try again", "Number type error", JOptionPane.ERROR_MESSAGE);
					clear();
				} else {
					try {
						winePrice = Double.parseDouble(priceField.getText());
						if(winePrice < 0) {
							JOptionPane.showMessageDialog(null, "Price must be a positive number - try again" , "Number type error", JOptionPane.ERROR_MESSAGE);
							clear();
						} 
						else {
							if(event.getSource() == saleButton) { // If sale button is pressed
								processSale();
								clear();
							} else if(event.getSource() == returnButton) {
								processReturn();
								clear();
							}
						}
					}
					catch(NumberFormatException error) {
						JOptionPane.showMessageDialog(null, "Invalid number - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
						clear();
					}	
				}
			
			}
			catch(NumberFormatException error) {
				JOptionPane.showMessageDialog(null, "Invalid number - please try again", "Number type error", JOptionPane.ERROR_MESSAGE);
				clear();
			}
		}
	}
}
