/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resteraunt;


import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
private String name;
private final ArrayBlockingQueue<String> queue;
private final HashMap<String, Integer>  completeOrders = new HashMap<String, Integer>();



	public Server(String n,ArrayBlockingQueue<String> q) {
		name = n;
		queue = q;
	}
	
	@Override
	public void run() {
		
		
		while(true) {
			
			//retrieves next item in queue
			//if there is no update to queue within 2 seconds than return null to serve
			String serve;
			try {
				serve = queue.poll(2, TimeUnit.SECONDS);
			
			
			//if serve queue is empty and poll has expired then print servers order
			if(serve == null) {
				System.out.println(printServerOrders());
				
				break;
			}else {
				//serves food and adds to hash map
				 String key = serve.split("\\d+", 2)[0];
                 if(completeOrders.get(key) != null)
                 {
                	 completeOrders.put(key, completeOrders.get(key) + 1);
                 }
                 else
                 {
                	 completeOrders.put(key, 1);
                 }
                 
			System.out.println("Server " + name + " is serving "+ serve);
			
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	
	 public String printServerOrders() {

		
	 int size = 0;
	 //calculates total dishes served by server
     for(Integer value : completeOrders.values())
     {
         size += value;
     }
     
     String output = "Server " + name + " finished serving " + size + " orders including: \n" ;
     //retrieves how many of each dish is served by server
     for(HashMap.Entry<String, Integer> entry : completeOrders.entrySet())
     {
         output += entry.getValue() + " " + entry.getKey()+ "\n";
         
     }
     
     return output;
 }
	
	 
	 
	
}