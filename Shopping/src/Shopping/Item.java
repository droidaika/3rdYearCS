package Shopping;

import java.io.Serializable;

public class Item implements Serializable {

	private String itemName;
	private double price;
	private int quantity;


	public Item(String itemName, double price, int quantity) {
		this.itemName = itemName;
		this.quantity= quantity;
		this.price = price;
		
	}
	
	public String getItemName() {
		return itemName;
	}

	public double getItemPrice() {
		return price;
	}
	

	public int getItemQuantity() {
		return quantity;
	}

	public void setItemQuantity(int quantity) {
		this.quantity= quantity;
	}


	public double getItemTotal() {
		return price*quantity;
	}

	public String toString() {
		return String.format( "%s %d €%.2f", itemName, quantity,price );
	
	}



}





