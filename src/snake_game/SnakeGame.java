package snake_game;

import javax.swing.*;

public class SnakeGame extends JFrame{
	
	SnakeGame(){
		
		super("Snake Game");
		add(new Board());
		pack(); 
		
		ImageIcon icon = new ImageIcon(SnakeGame.class.getResource("/icons/snake1.jpg"));
        setIconImage(icon.getImage());
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new SnakeGame();
	}

}
