/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resteraunt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

	//lists containing chefs and servers
	private static ArrayList<Thread> chefList = new ArrayList<Thread>();
	private static ArrayList<Thread> serverList = new ArrayList<Thread>();
	private static String fileLocation = "orders.txt";

	public static void main(String args[]) throws ClassNotFoundException, IOException, InterruptedException {
		
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(50);
		List<String> textOrders = Collections.synchronizedList(new ArrayList<String>());
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileLocation)));
		String line;
	    while((line = reader.readLine()) != null ) {
	    	//textOrders contains contents of orders.txt
			textOrders.add(line);
		}
	    reader.close();

	    ///adds chefs
		chefList.add( new Thread(new Chef("Mark",q,textOrders)));
		chefList.add(  new Thread(new Chef("John",q,textOrders)));
		
		//adds servers
		serverList.add( new Thread(new Server("Katie",q)));
		serverList.add( new Thread(new Server("Andrew",q)));
		serverList.add( new Thread(new Server("Emily",q)));
		
		
		for(Thread chef : chefList) {
			 
			chef.start();
			//.join 10 to spread out beginning of chefs so they don't cook the same meal 
			chef.join(40);
			
		 }
		for(Thread server : serverList) {
			
			server.start();
			//server.join(50);
			
		 }

		
		
	}
	 

}