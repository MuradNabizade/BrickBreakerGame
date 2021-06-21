package com.muradn;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play=false;
    private int score=0;
    private int totalBricks=21;

    private Timer time;
    private int delay= 8;

    private  int playerX=310;

    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir = -1;
    private int ballDirY=-2;

    private MapGenerator map;


    public GamePlay(){
        map=new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time=new Timer(delay,this);
        time.start();//

    }
    public void paint(Graphics g){

        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //draw map
        map.draw((Graphics2D)g);
        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //pause System for Game
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("Press Space to Pause",100,30);


        // the paddle
        g.setColor(Color.orange);
        g.fillRect(playerX,550,100,8);

        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks==0){
                play = false;
                ballXdir = 0;
                ballDirY = 0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif", Font.BOLD, 35));
                g.drawString("You won", 190, 300);

                g.setFont(new Font("serif", Font.BOLD, 35));
                g.drawString("Press R To Restart", 190, 35);
        }

        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballDirY=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Game OVER",190,300);

            g.setFont(new Font("serif",Font.BOLD,35));
            g.drawString("Press R To Restart",220,335);

        }

        g.dispose();


    }


    @Override
    public void actionPerformed(ActionEvent e){
        time.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballDirY=-ballDirY;
            }
           A: for(int i=0;i<map.map.length;i++ ){
                for(int j=0;j<map.map[0].length; j++){
                    if(map.map[i][j]>0){
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickHeight+50;
                        int brickWidth=map.brickwidth;
                        int brickHeight= map.brickHeight;

                        Rectangle rect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect =new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width){
                                ballXdir=-ballXdir;
                            }
                            else{
                                ballDirY=-ballDirY;
                            }
                            break A;
                        }


                    }

                }
            }




            ballposX+=ballXdir;
            ballposY+=ballDirY;
            if(ballposX<0){
                ballXdir=-ballXdir;
            }
            if(ballposY<0){
                ballDirY=-ballDirY;
            }
            if(ballposX>670){
                ballXdir=-ballXdir;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=600){
                playerX=600;
            }
            else{
                moveRight();
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX=10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_R){
            if(!play){
                play=true;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballDirY=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3,7);
                repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            if(play){
                play=false;
                ballposX=ballposX;
                ballposY=ballposY;
                ballXdir=ballXdir;
                ballDirY=ballDirY;
                playerX=playerX;
                score=score;
                totalBricks=totalBricks;
                repaint();
            }
        }

    }
    public void moveRight(){
        play=true;
        playerX+=40;
    }
    public void moveLeft(){
        play=true;
        playerX-=40;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
