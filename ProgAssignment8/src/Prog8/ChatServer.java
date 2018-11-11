package Prog8;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTextArea;



public class ChatServer {

	

    private static final int PORT = 1;

    // names of people in chat
    private static HashSet<String> names = new HashSet<String>();

    //Message people send to server
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    
	//GUI variables
	public static JTextArea messages = new JTextArea(8,60);
	private JFrame frame;
	
    public static void GuiServer (){
		
		
		
		 JFrame frm = new JFrame();
		 //panel contains the white box and text
		 JPanel pnl = new JPanel();
		 //set layout to null for manual placement
		 frm.setLayout(null);
	     
		  pnl.setLayout(new BoxLayout(pnl, BoxLayout.LINE_AXIS));
	    

	        
	        pnl.setLocation(15,10);
	        frm.setPreferredSize(new Dimension(400, 600));
	       	pnl.setSize(360,400);

	        pnl.setBackground(Color.WHITE);
	        frm.setBackground(Color.GRAY);
	        
	        //adds panel to frame
	        frm.add(pnl);
	        
	       
	        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EDIT
	        frm.setResizable(false);
	        frm.pack();
	        frm.setVisible(true);
	        
	       
			pnl.add(messages);
			
			messages.append("Server Started. \n" );
			messages.setEditable(false);
			
	}
	
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        GuiServer();
		
        ServerSocket listener = new ServerSocket(PORT);
        
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    
    private static class Handler extends Thread {
    	private InputStream imageInput;
    	byte[] imageArray;
		BufferedImage image;
		
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        
        public void run() {
            try {
            	imageInput = socket.getInputStream();

                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Asks for name and then reads in response and adds it to names hash map
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }

                
                out.println("NAMEACCEPTED");
                writers.add(out);
                
                String address = in.readLine();
    			if(address.startsWith("@")) {
    				messages.append(address + " has connected\n");
    			}
    			
                    while(true) {
            			
        				String input = in.readLine();
        				if(input != null) {
        					
        					if(input.startsWith("StoreImage")){
        						//store the image into variable but dont save into server yet
        						imageArray = new byte[50000000]; //max image 50 megabytes for this server 
        														//or else bytes are going to be read with 
        						imageInput.read(imageArray);
        						image = ImageIO.read(new ByteArrayInputStream(imageArray));
        						
        					}
        					else if(input.startsWith("Upload")) {
        						//upload button pressed
        						//save image stored into server
        							messages.append("Received image \n");
        							ImageIO.write(image, "jpg", new File("image"+".jpg"));
        							
        							//Setting imageArray and image as null so we can upload new images
        							imageArray = null;
        							image = null;
        					}
        					
        					else {
        						
        						for(PrintWriter writer: writers) {
        							writer.println("MESSAGE "+ name + ": " + input);
        						}
        					}
        				}
        				
        				else {
        					return;
        				}
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
       
    }
}