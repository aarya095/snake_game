package snake_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private int dots;
	private Image apple,dot,head;
	
	private final int  ALL_DOTS = 900;
	private final int DOT_SIZE = 10;
	private final int RANDOM_POSITION = 28;
	
	private int apple_x;
	private int apple_y;
	
	private Timer timer;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private int nextDirection = KeyEvent.VK_RIGHT; // Start moving right by default

	private boolean inGame = true;
	
	private int score = 0;
	
	JButton restartbutton;
	
	public Board(){
		
		
		
		addKeyListener(new TAdapter());
		
		setBackground(Color.blue);
		setPreferredSize(new Dimension(340,360));
		setFocusable(true);
		
		loadImages();
		initGame();
		
		restartbutton = new JButton("Restart");
		restartbutton.setBounds(120, 200, 100, 30);
		restartbutton.setFocusable(false);
		restartbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});
		
	}
	
	private void restartGame() {
		dots = 3;
		score = 0;
		inGame = true;
		
		for (int i=0; i< dots; i++) {
			
			y[i]= 50;
			x[i]= 50 - (i* DOT_SIZE);
		
		}
		
		locateApple();
		
		nextDirection = KeyEvent.VK_RIGHT;
		
		timer.restart();
		
		remove(restartbutton);
		repaint();
		
	}
	
	public void loadImages() {
		
		ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/apple.png"));
		apple = i1.getImage();
		
		ImageIcon i2 = new ImageIcon(getClass().getResource("/icons/dot.png"));
		dot = i2.getImage();
		
		ImageIcon i3 = new ImageIcon(getClass().getResource("/icons/head.png"));
		head = i3.getImage();
		
	}
	
	public void initGame() {
		
		dots = 3;
		
		for(int i = 0; i < dots; i++) {
			
			y[i] = 50;
			x[i] = 50 - (i  * DOT_SIZE);
			
		}
		
		locateApple();
		
		timer = new Timer(120, this);
		timer.start();
	}
	
	public void locateApple() {
	    boolean validPosition = false;
	    
	    while (!validPosition) {
	        int r = (int)(Math.random() * RANDOM_POSITION);
	        apple_x = r * DOT_SIZE + 20; // Offset for left border

	        r = (int)(Math.random() * RANDOM_POSITION);
	        apple_y = r * DOT_SIZE + 40; // Offset for top border

	        // Check if the apple spawns on the snake's body
	        validPosition = true;
	        for (int i = 0; i < dots; i++) {
	            if (x[i] == apple_x && y[i] == apple_y) {
	                validPosition = false; // Found a collision with the snake
	                break;
	            }
	        }
	    }
	    
	    }

	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(Color.darkGray);
		g.fillRect(0,0,340,360);
		
		g.setColor(Color.black);
		g.fillRect(20,40,300,300);
		
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		
		if(inGame) {
			
			g.setColor(Color.BLACK);
			g.drawRect(20, 40, 300, 300);
			
			g.drawImage(apple, apple_x,  apple_y, this);
		
		for (int i = 0 ; i < dots ; i++) {
			if (i == 0) {
				g.drawImage(head, x[i], y[i], this);
			}	
			else {
				g.drawImage(dot, x[i], y[i], this);
			}
		}
		
		//Score
		g.setColor(Color.WHITE);
		g.setFont(new Font ("SAN SERIF", Font.BOLD, 14));
		g.drawString("Score: "+ score, getWidth() - 100, 25);
		
		Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g) {
		String msg = "Game Over";
		Font font = new Font("SAN SERIF", Font.BOLD, 14);
		FontMetrics metrices = getFontMetrics(font);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (340 - metrices.stringWidth(msg)) / 2, 360/2);
		
		//for restarting the game 
		if (restartbutton.getParent() == null) {
			add(restartbutton);
		}
	}
	
	public void move() {
	    if (inGame) {
	        // Shift the body segments to the front
	        for (int i = dots; i > 0; i--) {
	            x[i] = x[i - 1];
	            y[i] = y[i - 1];
	        }

	        // Move the head in the next direction
	        switch (nextDirection) {
	            case KeyEvent.VK_LEFT:
	                x[0] -= DOT_SIZE;
	                break;
	            case KeyEvent.VK_RIGHT:
	                x[0] += DOT_SIZE;
	                break;
	            case KeyEvent.VK_UP:
	                y[0] -= DOT_SIZE;
	                break;
	            case KeyEvent.VK_DOWN:
	                y[0] += DOT_SIZE;
	                break;
	        }
	    }
	}


	
	public void checkApple() {
		
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			score += 10; //increases the score each time apple is eaten
			locateApple();
		}
	}
	
	public void checkCollision() {
	    // Check if the head collides with the body
	    for (int i = 1; i < dots; i++) {
	        if (x[0] == x[i] && y[0] == y[i]) {
	            inGame = false; // Collision detected
	        }
	    }

	    // Check for wall collisions
	    if (x[0] >= 320 || x[0] < 20 || y[0] >= 340 || y[0] < 40) {
	        inGame = false;
	    }

	    // Stop the timer if the game is over
	    if (!inGame) {
	        timer.stop();
	    }
	}


	
	public void actionPerformed(ActionEvent ae) {
	    if (inGame) {
	        // Update the direction before moving
	        switch (nextDirection) {
	            case KeyEvent.VK_LEFT:
	                leftDirection = true;
	                rightDirection = false;
	                upDirection = false;
	                downDirection = false;
	                break;
	            case KeyEvent.VK_RIGHT:
	                rightDirection = true;
	                leftDirection = false;
	                upDirection = false;
	                downDirection = false;
	                break;
	            case KeyEvent.VK_UP:
	                upDirection = true;
	                downDirection = false;
	                leftDirection = false;
	                rightDirection = false;
	                break;
	            case KeyEvent.VK_DOWN:
	                downDirection = true;
	                upDirection = false;
	                leftDirection = false;
	                rightDirection = false;
	                break;
	        }

	        checkApple();
	        checkCollision();
	        move();
	    }
	    repaint();
	}

	
	public class TAdapter extends KeyAdapter {
	    public void keyPressed(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT && !rightDirection) {
	            nextDirection = KeyEvent.VK_LEFT;
	        }
	        if (key == KeyEvent.VK_RIGHT && !leftDirection) {
	            nextDirection = KeyEvent.VK_RIGHT;
	        }
	        if (key == KeyEvent.VK_UP && !downDirection) {
	            nextDirection = KeyEvent.VK_UP;
	        }
	        if (key == KeyEvent.VK_DOWN && !upDirection) {
	            nextDirection = KeyEvent.VK_DOWN;
	        }
	    }
	}



}
