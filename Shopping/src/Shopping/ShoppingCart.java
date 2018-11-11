package Shopping;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoppingCart  {

	private String cName;
	private String date;
	
	private List<Inventory> inventoryList;
	private List<Item> shoppingCart = new ArrayList<Item>();

	ObjectInputStream ois;
	FileInputStream fis;
	
	private void openFileInput() throws IOException{
		//use to open text file
		 fis = new FileInputStream("InventoryList.txt");
		 ois = new ObjectInputStream(fis);
	}
	private void closeFileInput() throws IOException{
		//use to save to text file
		 FileOutputStream fos = new FileOutputStream("InventoryList.txt");
		 ObjectOutputStream oos = new ObjectOutputStream(fos);
		 oos.writeObject(inventoryList);
		ois.close();
		fis.close();
	}
	public ShoppingCart(String cName, String date) throws IOException, ClassNotFoundException {
		
		this.cName=cName;
		this.date=date;
		openFileInput();
		inventoryList = (ArrayList<Inventory>) ois.readObject();
		ois.close();
		fis.close();
	}
	
	public void addItem(String name, int quantity) throws IOException {
		
		openFileInput();
		
		Item item = searchInventory(name);	

		//makes sure there is said item in inventory
		if(searchInventory(name) == null){
			System.out.println("Item not found");
			//handles case if item exists in inventory but there is not enough stock or stock is empty
		}else if(quantity > item.getItemQuantity()){
			System.out.println("There is currently not enough stock for that order.");
			System.out.println("The full remaining stock of " + item.getItemQuantity() + " shall be added to your order.");
			shoppingCart.add(new Item(name,item.getItemPrice(),item.getItemQuantity()));
			item.setItemQuantity(0);
		}else {
			//adds items to shopping cart and removes from inventory
			item.setItemQuantity(item.getItemQuantity()-quantity);
			shoppingCart.add(new Item(name,item.getItemPrice(),quantity));
		}

		closeFileInput();
	}
		
	
	
	public void removeItem(String name, int quantity) throws IOException{
		openFileInput();
		for(Item itemCart : shoppingCart) {
			if(itemCart.getItemName() == name){ //if name is found
				
				//new quantity is quantity left in shopping cart after removal
				int newQuantity =itemCart.getItemQuantity() - quantity;
				Item item = searchInventory(name);
				//
				if(newQuantity == 0) { 	
					
					
					//if it completely empties shopping cart exactly then add to inventory and delete record in hsopping cart
					item.setItemQuantity(item.getItemQuantity()+quantity);
					shoppingCart.remove(itemCart);
				}
				else if(newQuantity<0){
					//if u try and remove more of item then there is in cart then remove item from shopping cart and add it all to inventory
					item.setItemQuantity(item.getItemQuantity()+itemCart.getItemQuantity());

					shoppingCart.remove(itemCart);

				}
				else { 
					//if there is mroe than enough of item to remove, remove quantity amount and add to inventory
					itemCart.setItemQuantity(newQuantity);
					item.setItemQuantity(item.getItemQuantity()+quantity);
				}
				//break to prevent errors after removing item
				 break;
			}
			
		}
	
		closeFileInput();
	}
	
	public Item searchInventory(String name){
		
		
		
			int index = Collections.binarySearch(inventoryList,new Inventory(null,name,0,0,0), Comparator.comparing(Inventory::getItemName));
			
			return inventoryList.get(index).getItem();
			
	}
	
	public String viewCart() {
		double total = 0; 
		String output=date+ "      Name: "+cName+"\nQuantity   Item Name  Subtotal\n";
		
		for(Item i : shoppingCart) {
			output+=String.format( "%-10d %-10s €%.2f\n\n", i.getItemQuantity(), i.getItemName() , i.getItemTotal() );
			total+=i.getItemPrice()*i.getItemQuantity();
		}
		
		output+=String.format("             Total: €%.2f", total);
		
		return output;
	}
	public void sortItems() {
	
	    Collections.sort(shoppingCart, Comparator.comparing(Item::getItemTotal));
	    for(Item item: shoppingCart){
		 System.out.println(String.format( "%s %d €%.2f", item.getItemName(), item.getItemQuantity(), item.getItemTotal() ));

	    }
	}

}

