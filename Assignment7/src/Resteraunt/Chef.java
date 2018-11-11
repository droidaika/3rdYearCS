package Resteraunt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Chef extends Thread{
	
	
	
	private String name;
	private final ArrayBlockingQueue<String> queue;
	private List<String> fileList;
	//hash map that contains how much of what food was cooked by this chef
	//this means that if a new food item is added to the orders.txt file, code can run it without needing to be edited
	private HashMap<String, Integer> cookedOrders = new HashMap<String,Integer>();;
	
	public Chef(String n,ArrayBlockingQueue<String> q, List<String> oL) throws IOException{
		
		name = n;
		queue= q;
		fileList = oL;
	}	
	
	 @Override
	    public void run() {
			 
				 
			 while(true) {
		               	//if there are no more orders left then break
		            	if(fileList.size() == 0) {
		            		System.out.println(printChefOrders());
			            	break;
			            	
		            	}
		            	else {
		            	
		            	String nextOrder = fileList.get(0);
		            	
		            	//cooks food
                        String key = nextOrder.split("\\d+", 2)[0];
                        if(cookedOrders.get(key) != null)
                        {
                        	cookedOrders.put(key, cookedOrders.get(key) + 1);
                        }
                        else
                        {
                        	cookedOrders.put(key, 1);
                        }
                        
		            	System.out.println("Chef " + name + " is preparing " + nextOrder);
		            	
		            	//adds food to queue to be served
		            	try {
							queue.put(nextOrder);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
		            	
		            	//removes item from list so its not cooked twice
		            	
		            	fileList.remove(0);
		            	}
		            	
		            	//sleep so there is a break between cooking and serving
		            	try {
		            		Thread.sleep(500);
			    		} catch (InterruptedException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
		            	
		            }
			 
	           
		 
	 }
	 
	 
	 
	 public String printChefOrders() 
	 {
	        int size = 0;
	        //calculates total dishes cooked by chef
	        for(Integer value : cookedOrders.values())
	        {
	            size += value;
	        }
	        
	        String output = "\nChef " + name + " finished preparing " + size + " orders including: \n " ;
	        //retrieves how many of each food is cooked
	        for(HashMap.Entry<String, Integer> entry : cookedOrders.entrySet())
	        {
	            output += entry.getValue() + " " + entry.getKey() +"\n";
	            
	        }
	        
	        return output;
	    }
	 
	 
	 
	 
    }

	