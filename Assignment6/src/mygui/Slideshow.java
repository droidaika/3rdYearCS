package mygui;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Slideshow extends JFrame {
	Container c;
	private JLabel pic;
	private Timer timer;
	private int x = 0;
	private int cat = 0;
	private JButton animalButton,flowerButton,foodButton;
	
	//names of drop down items
	private String names[] =
		{"none", "Animal", "Flower","Food"};
	
	//picture url
	 private String[][] list = {
		 
			 {
		         "","","","","","",""
		       },
			 {
             "animal1.jpeg",//0
             "animal2.jpg",//1
             "animal3.jpeg",//2
             "animal4.jpg",//3
             "animal5.jpg",//4
             "animal6.jpg",//5
             "animal7.jpg"//6
           },
			 {
         "flowers1.jpeg",//0
         "flowers2.jpg",//1
         "flowers3.jpg",//2
         "flowers4.jpg",//3
         "flowers5.jpg",//4
         "flowers6.jpg",//5
         "flowers7.jpg"//6
       },
			 {
           "food1.jpg",//0
           "food2.jpg",//1
           "food3.png",//2
           "food4.jpg",//3
           "food5.png",//4
           "food6.jpg",//5
           "food7.jpg"//6
         }
	 };
	
	
//setup gui
	public Slideshow() {
		super("Assignment 6");
		//jabel of the picture area
		pic = new JLabel();
        pic.setBounds(40, 30, 700, 300);

		
        
        //whole gui screen
		c = getContentPane();
		
		//top area which contains the slideshow selector
		JPanel ddPanel = new JPanel();
		ddPanel.setLayout(new FlowLayout());
	
		//the slideshow selector
		JComboBox imageSetSelector = new JComboBox(names);
		imageSetSelector.setMaximumRowCount(4);
		
		ddPanel.add(imageSetSelector, new FlowLayout());
		//adds dd panel to gui
		c.add(ddPanel, BorderLayout.PAGE_START);
		
		//creates buttons down
		animalButton = new JButton("Animal Background Colour");
		flowerButton = new JButton("Flower Background Colour");
		foodButton = new JButton("Food Background Colour");
		
		//handler controls what buttons do when a mouse action happens to them
		thehandler handler = new thehandler();
		animalButton.addMouseListener(handler);
		flowerButton.addMouseListener(handler);
		foodButton.addMouseListener(handler);
		
		
		//every time image set selecter is acted upon. Change the cat variable and repaint
		imageSetSelector.addItemListener(
		new ItemListener() {
			public void itemStateChanged(ItemEvent e) 
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					
					cat = imageSetSelector.getSelectedIndex();
					c.repaint();
				}
			}
		}
        );
		
		
		
		
		
		//setup jlabel to display image icons
		timer = new Timer(1000, new ActionListener() {
			
			//sets image size of x
			@Override
            public void actionPerformed(ActionEvent e) {

                SetImageSize(x);
                //increases x by one to swap image next turn
                x += 1;
                //if x is max list[] size then rest to 0
                if(x >= list[cat].length )
                    x = 0; 
                c.repaint();
            }
		});
		
		
		
		timer.start();
		
		//adds pic to content gui
		c.add(pic);
		
		//adds buttons to bottom jpanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(animalButton);
		buttonPanel.add(flowerButton);
		buttonPanel.add(foodButton);
		//adds bottom panel to gui
		c.add(buttonPanel,BorderLayout.SOUTH);
			
		setSize(600,600);
		setVisible(true);
}
	
	
	
	public void SetImageSize(int i){

        ImageIcon icon = new ImageIcon(list[cat][i]);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(pic.getWidth()-50, pic.getHeight()-50, Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);
    }
	
	private class thehandler implements MouseListener
    {


        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource()== animalButton)
            {
                c.setBackground(Color.red);
            }

             else if(e.getSource()==flowerButton)
            {
               c.setBackground(Color.blue);
            }

             else if(e.getSource()==foodButton)
            {
                c.setBackground(Color.green);
            }
        }

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


    }
	
public static void main(String args[]) {
	//creating slideshow class
	System.out.println(System.getProperty("user.dir"));
    new Slideshow();
}	
	
}
