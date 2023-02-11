/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package brickbreakergame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author UJJWAL
 */
public class GamePanel extends JPanel implements ActionListener , KeyListener{  
    
    private boolean play = false; 
    private int totalBrick = 21;
    private int score = 0 ;
    private Timer timer ;
    private int delay = 8 ;
    private int ballposx =120 ;
    private int ballposy = 350 ;
    private int ballXdir = -1  ;
    private int ballYdir = -2 ;
    private int playerX = 350 ; 
    
    private MapGenerator map; 
    
    public GamePanel(){
        addKeyListener(this) ;
        setFocusable(true) ;
        setFocusTraversalKeysEnabled(true) ;
        
        timer = new Timer(delay,this) ;
        timer.start() ;
        
        map = new MapGenerator(3,7) ;
        
    }
    @Override
    public void paint(Graphics g) {
        // black canvas
        g.setColor(Color.black) ;
        g.fillRect(1, 1, 692, 592 );
        
        //border
        g.setColor(Color.YELLOW) ;
        g.fillRect(0,0 , 692, 3);
        g.fillRect(1,3 , 3, 592);
        g.fillRect(683,3 , 3, 592);
        
        //paddle
        g.setColor(Color.GREEN) ;
        g.fillRect(playerX,550, 100, 8 );
        
        //bricks
        map.draw((Graphics2D)g) ;
        
        
        //ball
        g.setColor(Color.RED) ;
        g.fillOval(ballposx , ballposy, 20, 20);
        
        
        //score
        g.setColor(Color.green);
        g.setFont(new Font("serif",Font.BOLD,16)) ;
        g.drawString("Score : "+score, 550, 19);
        
        //gameover 
        if(ballposy >= 570){
            play = false ;
            ballXdir = 0 ;
            ballYdir = 0 ;
            
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,40)) ;
            g.drawString("**GameOver**",200 , 300);
            
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,35)) ;
            g.drawString("       Score : "+score,200 , 350);
            
            //restart
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("Press Enter TO Restart",185 , 400);
        }
            
            //gamewin
            
            if(totalBrick<=0 ){
                play = false ;
            ballXdir = 0 ;
            ballYdir = 0 ;
            
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,40)) ;
            g.drawString("**You Won**",200 , 200);
            
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,35)) ;
            g.drawString("       Score : "+score,200 , 250);
            
            
            g.setColor(Color.green) ;
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("Press Enter TO Restart",185 , 300);
            }
            
        }
    
    
    
    private void moveleft(){
        play = true ;
        playerX-=20 ;
    }
    private void moveright(){
        play = true ;
        playerX+=20 ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            if(ballposx<=0){
                ballXdir =- ballXdir ;
            }
            if(ballposy<=0){
                ballYdir =- ballYdir ;
            }
            if(ballposx>=667){
                ballXdir =- ballXdir ;
            }
            
            Rectangle ballRect = new Rectangle(ballposx,ballposy,20,20) ;
            Rectangle paddleRect = new Rectangle(playerX,550,100,2) ;
            if(ballRect.intersects(paddleRect)){
                ballYdir=-ballYdir ;
            }
            
            
            A : for(int i = 0 ; i < map.map.length ; i++){
                for(int j = 0 ; j < map.map[0].length ; j++){
                    if(map.map[i][j]>0){
                       int width = map.brickwidth;
                       int height = map.brickHeight ;
                       int brickXpos = 75+j*width ;
                       int brickYpos = 50+i*height ;
                       
                       
                       Rectangle brickRect = new Rectangle(brickXpos,brickYpos,width,height) ;
                       
                       if(ballRect.intersects(brickRect)){
                           map.setBrick(0,i,j) ;
                           totalBrick-- ;
                           score+=5;
                           if(ballposx+19<= brickXpos || ballposx+1>=brickXpos+width) {
                               ballXdir=-ballXdir ;
                           }else{
                               ballYdir=-ballYdir ;
                           }
                           
                           break A ;
                       }
                    }
                    }
                }
            ballposx +=ballXdir; 
            ballposy +=ballYdir ;
            }
            
            
            repaint() ;
        }

    
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 3){
                playerX=3;
            }else{
        moveleft() ;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX>=580){
                playerX = 580 ;
            }else{
        moveright() ;
            }
        } 
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play ){
                score = 0 ;
                totalBrick = 21 ;
                ballposx = 560 ;
                ballposy = 450 ;
                ballXdir = -1 ;
                ballYdir = -2 ;
                playerX  = 320  ;
                
                map = new MapGenerator(3,7) ;
            }
       
    }
        repaint() ;
    }

   
    
    
    
    
    
    
    
    
    
    
    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
