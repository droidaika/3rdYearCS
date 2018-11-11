package Prog8;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClient  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5982667209359481059L;

	private final InetAddress serverAddress;
	
    BufferedReader in;
    PrintWriter out;
    //JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(20);
    JTextArea messageArea = new JTextArea(8, 40);
   

    
    public OutputStream outputStream;
    private JFrame frame;
	
	private JButton send;
	//private JTextArea messages;
	private JLabel pic;
	
	private Icon Avatar = new ImageIcon(new ImageIcon ("animal1.jpeg").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	private JRadioButton setButton;
	private JRadioButton imageButton;
	private JButton uploadButton;
	private Color rand_color = Color.BLACK;
    
    public ChatClient() throws Exception, IOException {
    	
    	super("Chat Open");
    	serverAddress = InetAddress.getLocalHost();
		frame = this;
		
		
		
		
		
		
		
		
		
	    
	     
		
		
		pic = new JLabel(Avatar);
		send = new JButton("Send");
		textField = new JTextField(20);
		
		JPanel TextPanel = new JPanel();
		
		TextPanel.setLayout(new BoxLayout(TextPanel, BoxLayout.LINE_AXIS));
		
		TextPanel.add(Box.createRigidArea(new Dimension(8,8)));
		TextPanel.add(pic);
		TextPanel.add(Box.createRigidArea(new Dimension(8,8)));
		TextPanel.add(textField);
		TextPanel.add(Box.createRigidArea(new Dimension(8,8)));
		TextPanel.add(send);
		TextPanel.add(Box.createRigidArea(new Dimension(8,8)));
		
		setButton  = new JRadioButton("Setting");
		imageButton = new JRadioButton("Image");
		uploadButton = new JButton("Upload");
		JPanel ButoonPanel = new JPanel();
		ButoonPanel.setLayout(new BoxLayout(ButoonPanel, BoxLayout.LINE_AXIS));
		ButoonPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		ButoonPanel.add(setButton );
		ButoonPanel.add(Box.createRigidArea(new Dimension(8,8)));
		ButoonPanel.add(imageButton);
		ButoonPanel.add(Box.createRigidArea(new Dimension(8,8)));
		ButoonPanel.add(uploadButton);
		
		
		
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c,BoxLayout.PAGE_AXIS));
		
		JPanel pnl = new JPanel();
		messageArea = new JTextArea(30,30);
		pnl.add(messageArea);
		c.add(pnl);
		
		
		
	
		
		
		c.add(TextPanel);
		c.add(Box.createRigidArea(new Dimension(8,8)));
		c.add(ButoonPanel);
		
		messageArea.setEditable(false);
		
		//add Listeners
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				out.println(textField.getText());
				textField.setText("");
			}
		});
		
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				Color color =new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
				rand_color = color;
				c.setBackground(rand_color);
				
				
			}
		});
		
		
		imageButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				 chooser.showOpenDialog(null);
				 File f = chooser.getSelectedFile();
				 String filename = f.getAbsolutePath();
				 try {
					File file = new File(filename);
					
					BufferedImage image = ImageIO.read(file);
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(image, "jpg", baos);
					
					out.println("StoreImage");
					//messageArea.append("uploadA");
					//messageArea.append("uploadU");
					outputStream.write(baos.toByteArray());
					//messageArea.append("uploadB");
					outputStream.flush();
				
					
					//outputStream.flush();
					//out.println(baos);
					
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
			
		});
		
		uploadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//only save the image to the server once the client pressed
				//upload button
				out.println("Upload");
			}
			
			
		});
		
		Random rand = new Random();
		Color color =new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		rand_color = color;
		
		
		c.setBackground(rand_color);
		
		
		pnl.setOpaque(false);
		
		setSize(400,600);
		pack();
    	
    }

   
    private String NameSelection() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }

    
    private void run() throws IOException {

        //set up serverAddress and port
        Socket socket = new Socket(serverAddress, 1);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        outputStream = socket.getOutputStream();

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(NameSelection());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
                messageArea.append("Welcome to the Chat Room ^-^\n");
                out.println("@"+serverAddress);
            } else if (line.startsWith("MESSAGE")) {
            	
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

    
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}