import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class BrickBreaker extends JPanel implements KeyListener,ActionListener, Runnable 
{
	// movement keys...............................................................
	static boolean right = false;
	static boolean left = false;
	
 	// variables declaration for ball..............................................
 	int ballx = 750;
 	int bally = 600;
 	
 	// variables declaration for bat................................................
 	int batx = 680;
 	int baty = 680;
 	
 	// variables declaration for brick..............................................
 	int brickx = 80;
 	int bricky = 100;
	int brickBreadth = 130;
	int brickHeight = 30;
	
	// declaring ball, paddle,bricks.................................................
	Rectangle Ball = new Rectangle(ballx, bally, 20, 20);
	Rectangle Bat = new Rectangle(batx, baty, 140, 20);
	Rectangle[] Brick = new Rectangle[49];

	//reverses.......................................................................
	int movex = -1;
	int movey = -1;
	boolean ballFallDown = false;
	boolean bricksOver = false;
	int count = 0;
	String status;

	BrickBreaker() 
	{
	}

 	public static void main(String[] args)
	{
  		JFrame frame = new JFrame();
  		BrickBreaker game = new BrickBreaker();
  		JButton button = new JButton("RESTART GAME");
  		frame.setSize(1500, 950);
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		frame.setTitle("BRICK BREAKER GAME");
  		frame.add(game);
  		frame.add(button, BorderLayout.SOUTH);
  		frame.setLocationRelativeTo(null);
  		frame.setResizable(false);
  		frame.setVisible(true);
 		button.addActionListener(game);
  		game.addKeyListener(game);
  		game.setFocusable(true);
  		Thread t = new Thread(game);
  		t.start();
	}
 	public void paint(Graphics g)
 	{
  		g.setColor(Color.black);				//Background................. 
  		g.fillRect(0, 0, 1500,910);
  		g.setColor(Color.white);				//Ball.......................
  		g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
  		g.setColor(Color.white);				//Bat........................ 
  		g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);
  		g.setColor(Color.red);					//Boundary...................
  		g.drawRect(0, 0, 1492,700);
  		for (int i = 0; i < Brick.length; i++) 
		{
  	 		if (Brick[i] != null) 
			{
    				g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width,Brick[i].height, true);
   			}
  		}
		if (ballFallDown == true || bricksOver == true) 
		{
   			Font f = new Font("Arial", Font.BOLD, 55);
   			g.setFont(f);
   			g.setColor(Color.green);
  			g.drawString(status, 400, 550);
   			ballFallDown = false;
   			bricksOver = false;
  		}
 	}
 	//Game Loop.......................................................................
	
	//When ball strikes borders.......................................................
	public void run() 
	{
  		//Creating bricks for the game............................................
     		createBricks();
		//Ball reverses when touches the brick....................................
  		while (true) 
		{
   			for (int i = 0; i < Brick.length; i++) 
			{
    				if (Brick[i] != null) 
				{
     					if (Brick[i].intersects(Ball)) 
					{
      						Brick[i] = null;
      						movey = -movey;
      						count++;
     					}
    				}
   			}
			//check if ball hits all bricks....................................
			if (count == Brick.length)
			{
    				bricksOver = true;
				status = "YOU WON THE GAME";
    				repaint();
			}
      			repaint();
   			Ball.x += movex;
   			Ball.y += movey;
			if (left == true) 
			{
    				Bat.x -= 3;
    				right = false;
   			}
   			if (right == true) 
			{
    				Bat.x += 3;
    				left = false;
   			}
   			if (Bat.x <= 4) 
			{
    				Bat.x = 4;
   			}
			else if (Bat.x >= 1347) 
			{
    				Bat.x = 1347;
   			}
			//Ball reverses when strikes the bat................................
   			if (Ball.intersects(Bat))
			{
    				movey = -movey;   
			}
			// Ball reverses when touches left and right boundary...............
   			if (Ball.x <= 0 || Ball.x + Ball.height >= 1490)
			{
    				movex = -movex;
   			}
   			if (Ball.y <= 0) 
			{				// bally + Ball.height >= 700.......
    				movey = -movey;
   			}
  
   			if (bricksOver!= true)
			{
    				if (Ball.y >= 680) 	// when ball falls below bat........
    				{
					ballFallDown = true;
    					status = "YOU LOST THE GAME";
    					repaint();
				}
			}
  			try 
			{
    				Thread.sleep(3);
   				} catch (Exception ex)
				{
   			}

		}		// while loop ends here....................................
	}

	//HANDLING KEY EVENTS..............................................................
 	@Override
 	public void keyPressed(KeyEvent e) 
	{
  		int keyCode = e.getKeyCode();
  		if (keyCode == KeyEvent.VK_LEFT) 
		{
   			left = true;
  		}
		if (keyCode == KeyEvent.VK_RIGHT) 
		{
   			right = true;
  		}
 	}
	@Override
 	public void keyReleased(KeyEvent e) 
	{
  		int keyCode = e.getKeyCode();
  		if (keyCode == KeyEvent.VK_LEFT) 
		{
	  		left = false;
	  	}
	  	if (keyCode == KeyEvent.VK_RIGHT) 
		{
   			right = false;
  		}
 	}
	@Override
 	public void keyTyped(KeyEvent arg0) 
	{
	}
	@Override
 	public void actionPerformed(ActionEvent e) 
	{
  		String str = e.getActionCommand();
  		if (str.equals("RESTART GAME")) 
		{
   			this.restart();

  		}
 	}
	public void restart() 
	{
  		requestFocus(true);
 		initializeVariables();
  		createBricks();
  		repaint();
 	}
	public void initializeVariables()
	{
     		//variables declaration for ball.........................................
      		ballx = 750;
      		bally = 600;
      
		//variables declaration for bat...........................................
 	     	batx = 680;
  	    	baty = 680;
      		
		// variables declaration for brick.........................................
      		brickx = 80;
     		bricky = 100;
      		
		// declaring ball, paddle,bricks...........................................
      		Ball = new Rectangle(ballx, bally,20,20);
      		Bat = new Rectangle(batx, baty, 140,20);
     	 	Brick = new Rectangle[49];
      		movex = -1;
      		movey = -1;
      		ballFallDown = false;
      		bricksOver = false;
      		count = 0;
      		status = null;
	}
	//Creating bricks for the game.....................................................
 	public void createBricks()
	{
     	      	for (int i = 0; i < Brick.length; i++) 
		{
       			Brick[i] = new Rectangle(brickx, bricky, brickBreadth, brickHeight);
       			if (i == 9) 
			{
        			brickx = 80;
        			bricky = (bricky + brickHeight + 2);
      			}
       			if (i == 17) 
			{
        			brickx = 80-brickBreadth-1;
        			bricky = (bricky + brickHeight + 2);
			}
			if (i == 27) 
			{
        			brickx = 80;
        			bricky = (bricky + brickHeight + 2);
      			}
			if (i == 35) 
			{
        			brickx = 130+80+1;
        			bricky = (bricky + brickHeight + 2);
      			}
       	 		if (i == 41) 
			{
        			brickx = 80+260+2;
        			bricky = (bricky + brickHeight + 2);
      			}
			if (i == 45) 
			{
        			brickx = 80+390+3;
        			bricky = (bricky + brickHeight + 2);
      			}
			if (i == 47) 
			{
        			brickx = 80+455+4;
        			bricky = (bricky + brickHeight + 2);
      			}
       			brickx += (brickBreadth+1);
		}
 	}

}
