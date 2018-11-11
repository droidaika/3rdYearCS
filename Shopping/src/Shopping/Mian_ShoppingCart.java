package Shopping;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Mian_ShoppingCart {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		System.out.println("Inventory list before\n");
		
		//inventory before cart A removes items
		FileInputStream fis = new FileInputStream("InventoryList.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);	
		ArrayList<Inventory> inventoryList = (ArrayList<Inventory>) ois.readObject();
		Main_Inventory.printInventory(inventoryList);
		
		//creates shopping cart A and adds and removes from inventory to it
		ShoppingCart cart1 = new ShoppingCart("Cart A","20/10/2017");
		

		cart1.addItem("Apple", 2);

		
		cart1.addItem("Orange", 5);

		cart1.addItem("Milk", 2);

		cart1.addItem("Blue Cheese", 4);

		cart1.addItem("Candy", 25);

		cart1.removeItem("Candy", 5);
		
		//inventory after cart A has removed items
		System.out.println("Inventory list after Cart A\n");
		FileInputStream fis1 = new FileInputStream("InventoryList.txt");
		ObjectInputStream ois1 = new ObjectInputStream(fis1);	
		inventoryList = (ArrayList<Inventory>) ois1.readObject();
		Main_Inventory.printInventory(inventoryList);
		
		ois1.close();
	   
	    fis1.close();
	    
	    //creates, adds and removes from cart B
		ShoppingCart cart2 = new ShoppingCart("Cart B","10/10/2017");

		cart2.addItem("Apple", 2);

		cart2.addItem("Orange", 5);

		cart2.addItem("Milk", 2);

		cart2.addItem("Blue Cheese", 4);

		cart2.addItem("Cheddar", 3);

		cart2.addItem("Beef", 6);

		cart2.addItem("Candy", 20);

		cart2.addItem("Chocolate", 10);

		cart2.addItem("Chicken", 2);

		cart2.removeItem("Chocolate", 5);

		cart2.removeItem("Blue Cheese", 1);
		
		//prints out cart A and cart B
		System.out.println(cart1.viewCart());
		System.out.println(cart2.viewCart());
		
		//inventory left after cart B
		System.out.println("Inventory list after cart B\n");
		FileInputStream fis2 = new FileInputStream("InventoryList.txt");
		ObjectInputStream ois2 = new ObjectInputStream(fis2);	
		inventoryList = (ArrayList<Inventory>) ois2.readObject();
		Main_Inventory.printInventory(inventoryList);
		
		ois2.close();
	   
	    fis2.close();
	    ois.close();
		   
	    fis.close();
	    
	    //shuffle cart B and sort again
	    System.out.println("\nShuffle cartB and sort in ascending order");
		cart2.sortItems();
		
	}

}
