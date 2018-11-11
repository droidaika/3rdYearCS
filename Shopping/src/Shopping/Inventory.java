package Shopping;

import java.io.Serializable;

public class Inventory implements Serializable {

	private String sku;
	private double cost;
	private Item item;
	
	public Inventory(String sku ,String itemName,int quantity,double price,double cost) {
		this.sku=sku;
		item = new Item(itemName,price,quantity);
		
	}
	
	public String toString() {
		return sku +"\t" + item.toString();
	}
	
	public Item getItem() {
		return item;
	}
	
	public String getItemName() {
		return item.getItemName();
	}
	
}
