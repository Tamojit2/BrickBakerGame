package MyBrickGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXdirection = -1;
	private int ballYdirection = -2;
	
	private MapGenerate map;
	
	public GamePlay() {     //constructor
		
		map = new MapGenerate(3, 7);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics gh) {
		//Background
		gh.setColor(Color.black);
		gh.fillRect(1,1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)gh);
		
		//Borders
		gh.setColor(Color.yellow);
		gh.fillRect(0, 0, 3, 592);
		gh.fillRect(0, 0, 692, 3);
		gh.fillRect(691, 0, 3, 592);
		
		//score
		gh.setColor(Color.green);
		gh.setFont(new Font("serif", Font.BOLD, 25));
		gh.drawString(" "+score, 590, 30);
		
		//paddle
		gh.setColor(Color.green);
		gh.fillRect(playerX, 550, 100, 8);
		
		//ball
		gh.setColor(Color.yellow);
		gh.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdirection = 0;
			ballYdirection = 0;
			gh.setColor(Color.red);
			gh.setFont(new Font("serif", Font.BOLD, 30));
			gh.drawString("  YOU WON: ", 260, 300);
			
			gh.setFont(new Font("serif", Font.BOLD, 20));
			gh.drawString("Press Enter to Restart ", 230, 350);
		}
		
		if(ballPosY > 570) {
			play = false;
			ballXdirection = 0;
			ballYdirection = 0;
			gh.setColor(Color.red);
			gh.setFont(new Font("serif", Font.BOLD, 30));
			gh.drawString("  GAME OVAR: ", 190, 300);
			
			gh.setFont(new Font("serif", Font.BOLD, 20));
			gh.drawString("Press Enter to Restart ", 230, 350);
		}
		
		gh.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdirection = -ballYdirection;
			}
			
			A: for(int i = 0;i<map.map.length;i++) {
				for(int j =0;j< map.map[0].length;j++) {
					
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth +80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks --;
							score +=5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXdirection = -ballXdirection;
							} else {
								ballYdirection = -ballYdirection;
							}
							
							break A;
						}
						
					}
					
				}
			}
			
			ballPosX += ballXdirection;
			ballPosY += ballYdirection;
			if(ballPosX < 0) {
				ballXdirection = -ballXdirection;
				
			}
			if(ballPosY < 0) {
				ballYdirection = -ballYdirection;
				
			}
			if(ballPosX > 670) {
				ballXdirection = -ballXdirection;
				
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdirection = -1;
				ballYdirection = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerate(3, 7);
				
				repaint();		
			}
		}
		
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	
}
