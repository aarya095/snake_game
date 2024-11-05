package snake_game;

import javax.swing.*;

public class SnakeGame extends JFrame{
	
	SnakeGame(){
		
		super("Snake Game");
		add(new Board());
		pack(); 
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 new SnakeGame();
	}

}
