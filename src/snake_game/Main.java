package snake_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{
	
	JButton play;
	
	Main() {
		
		setLayout(null);
		
		ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/main.png")); // This instance is created to load the image from the resources using the ImageIcon class
		Image i2 = i1.getImage().getScaledInstance(512,512, Image.SCALE_DEFAULT); // This instance is created to hold the scaled version of the image
		ImageIcon i3 = new ImageIcon(i2); // This instance is created to wrap the scaled image (i2) back into an ImageIcon
		JLabel image = new JLabel(i3);
		image.setBounds(0,0,512,512);
		add(image);
		
		play = new JButton("Play");
		play.setBounds(210,460,110,40);
		play.addActionListener(this);
		image.add(play);
		
		setSize(522,552);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == play) {
			setVisible(false);
			new SnakeGame();
			
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Main();
	}

}
