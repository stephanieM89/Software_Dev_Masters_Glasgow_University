/*
 *  @author Stephanie Man
 *  This class creates a wine object
 */
public class Wine {
	
	// Instance variables 	
	private String wineName;  		// Represents name of wine
	private double winePrice;  // Represents price of a bottle of wine
	private int	wineQuantity; 	// Represents quantity of wine

	// Constructs a wine object with name, price and quantity of sale or return
	public Wine(String name, double price, int quantity) {
		this.wineName = name;
		this.wineQuantity = quantity;
		this.winePrice = price;
	}
	
	// Returns name of wine
	public String getWine() {
		return String.format(wineName);	
	}
	
	// Returns price of wine
	public double getPrice() {
		return winePrice;
	}
		
	// Returns quantity of wine items
	public int getQuantity() {
		return wineQuantity;
	}
		
}