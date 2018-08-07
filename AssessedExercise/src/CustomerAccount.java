/*
 * @author Stephanie Man
 * This class defines the Customer Account object.  It's purpose is to store the 
 * customer's name and balance, and to perform operations on the current balance
 * to update it in the case of a sale or return. 
 */

public class CustomerAccount {
	
	private String customerName;					
	private double customerBalance, transactionAmount;
	private Wine wine;

	private final double SERVICE_CHARGE = 0.8;		// Bottles that are returned are refunded

	// Constructs a customer account with the customer name with their balance details
	public CustomerAccount(String customerName, double customerBalance) {
		this.customerName = customerName;
		this.customerBalance = customerBalance;
	}
	
	// Calculates total amount of wine sold and updates customer balance
	public void calculateSale(Wine wine) {
		this.wine = wine;
		transactionAmount = wine.getPrice() * wine.getQuantity();
		customerBalance += transactionAmount;
	}

	//  which updates accountBalance & returns transaction cost
	public void calculateReturn(Wine wine) {
		this.wine = wine;
		transactionAmount = (wine.getPrice() * wine.getQuantity())* SERVICE_CHARGE;
		customerBalance -= transactionAmount;
	}
	
	
	// Returns the number of bottles
	public double getTransactionAmount() {
		return transactionAmount;
	}
	
	// Returns the customer name
	public String getCustomerName() {
		return String.format(customerName);
	}

	// Returns customer balance
	public double getCustomerBalance() {
		return customerBalance;
	}
}


