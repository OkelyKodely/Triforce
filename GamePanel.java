package triforce;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends javax.swing.JFrame implements KeyListener {

    int lives = 10500;
    
    JPanel jPanel;
    
    int  x=0,y=0;
    int xx=0, yy=0;

    List<Point> trees;
    List<Point> flags;
    Random rd = new Random();
    
    public void keyTyped(KeyEvent ke) {
        
    }
    public void keyReleased(KeyEvent ke) {
        
    }
    public void keyPressed(KeyEvent ke) {
        // move man
        
        //position with arrows
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            x-=10;
            xx-=20;
        }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            x+=10;
            xx+=20;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP){
            y+=10;
            yy+=20;
        }
        if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            y-=10;
            yy-=20;
        }
        //repaint jpanel
        jPanel.repaint();
    }
    
    public GamePanel() throws IOException {
        Image im = ImageIO.read(getClass().getResource("bird.png"));
        Image img = ImageIO.read(getClass().getResource("flag.png"));
        setTitle("centering");
        setLayout(null);
        setBounds(0, 0, 1000, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // generate random greens and flags for the map
        trees = new ArrayList<Point>();
        flags = new ArrayList<Point>();
        for(int i=0; i<1000; i++) {
            int vv = rd.nextInt(10000) - rd.nextInt(10000);
            int vy = rd.nextInt(7000) - rd.nextInt(7000);
            int v = rd.nextInt(56);
            Point pnt = new Point();
            pnt.x =vv;
            pnt.y = vy;
            pnt.color = v;
            trees.add(pnt);
            int xx = rd.nextInt(10);
            if(xx == 0)
                flags.add(pnt);
        }
        
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(10);
                        lives--;
                        setTitle("Lives Forever: "+lives);
                        
                        if(lives <= 0)
                            System.exit(0);
                
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }
        };
        t.start();

        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                //set graphics

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.red);

                
                //draw tiles
                if(x > 0 && y <= 0) {
                    for (int i = 0; i < 1000+x; i += 15) {
                        g2.drawLine(x-i,0, x-i, 700);
                    }
                    for (int j = y; j < 700; j += 15) {
                        g2.drawLine(0,-y+j,1000,-y+j);
                    }
                } else if(x > 0 && y > 0) {
                    for (int i = 0; i < 1000+x; i += 15) {
                        g2.drawLine(x-i,0, x-i, 700);
                    }
                    for (int j = 0; j < 700+y; j += 15) {
                        g2.drawLine(0,-y+j,1000,-y+j);
                    }
                } else if(x <= 0 && y <= 0) {
                    for (int i = 0; i < 1000-x; i += 15) {
                        g2.drawLine(x+i,0, x+i, 700);
                    }
                    for (int j = y; j < 700; j += 15) {
                        g2.drawLine(0,-y+j,1000,-y+j);
                    }
                } else {
                    if(x <= 0 && y > 0) {
                        for (int i = 0; i < 1000-x; i += 15) {
                            g2.drawLine(x+i,0, x+i, 700);
                        }
                        for (int j = 0; j < 700+y; j += 15) {
                            g2.drawLine(0,-y+j,1000,-y+j);
                        }
                    }
                }

                //draw random greens
                for(int i=0; i<trees.size(); i++) {
                    g2.setColor(new Color(0,trees.get(i).color+200,0));
                    g2.fillRect(trees.get(i).x+x, trees.get(i).y+y, 300, 300);
                }

                //draw random flags
                for(int i=0; i<flags.size(); i++) {
                    if(490 <= flags.get(i).x+x && 490 >= flags.get(i).x+x - 60 && 340 <= flags.get(i).y+y && 340 >= flags.get(i).y+y - 60) {
                        flags.remove(flags.get(i));
                        lives+=5000;
                        continue;
                    }
                    g2.drawImage(img, flags.get(i).x+x, flags.get(i).y+y, 60, 60, null);
                }
                
                //draw man
                g2.drawImage(im, 490, 340, 40, 40, null);

                //dispose component
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000,700);
            }
        };
        
        // set layout to nothing absolution
        jPanel.setLayout(null);

        //do some more shiet.
        jPanel.setBounds(0, 0, 1000, 700);
        jPanel.setBackground(new Color(50,50,255));
        add(jPanel);
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}