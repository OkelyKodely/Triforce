package triforce;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GamePanel extends javax.swing.JFrame implements KeyListener {

    Graphics2D g2 = null;
    
    float gain = 10;
    Clip audioClip;
    
    int lives = 1050000;
    
    boolean in = false;
                        
    JPanel jPanel;
    
    int  x=0,y=0,xxx=0,xxy=0;

    List<Point> clouds;
    List<Point> trees;
    List<Point> falgs;
    List<Point> flags;
    Random rd = new Random();
    
    public void keyPressed(KeyEvent ke) {
        //move man
        //position with arrows
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            x-=10;
        }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            x+=10;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP){
            y+=10;
        }
        if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            y-=10;
        }
        //repaint jpanel
        jPanel.repaint();
    }
    public void keyTyped(KeyEvent ke) {}
    public void keyReleased(KeyEvent ke) {}
    
    public void createClouds() {
        clouds = new ArrayList<Point>();
        for(int i=0; i<1000; i++) {
            int vv = rd.nextInt(10000) - rd.nextInt(10000);
            int vy = rd.nextInt(7000) - rd.nextInt(7000);
            Point pnt = new Point();
            pnt.x =vv;
            pnt.y = vy;
            clouds.add(pnt);
        }
    }
    
    private void createShiets() {
        // generate random greens and flags for the map
        falgs = new ArrayList<Point>();
        trees = new ArrayList<Point>();
        flags = new ArrayList<Point>();
        for(int i=0; i<1000; i++) {
            int vv = rd.nextInt(10000) - rd.nextInt(10000);
            int vy = rd.nextInt(7000) - rd.nextInt(7000);
            int v = rd.nextInt(56);
            Point pnt = new Point();
            pnt.x =vv;
            pnt.y = vy;
            trees.add(pnt);
            int xx = rd.nextInt(10);
            if(xx == 0)
                flags.add(pnt);
            int xx2 = rd.nextInt(10);
            if(xx2 == 0)
                falgs.add(pnt);
        }
    }
    
    public GamePanel() throws IOException {
        Image im = ImageIO.read(getClass().getResource("bird.png"));
        Image img = ImageIO.read(getClass().getResource("flag.png"));
        Image m = ImageIO.read(getClass().getResource("falgs.png"));

        setLayout(null);
        setBounds(0, 0, 1000, 800);
        
        //
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        //play sound track of the game
        playSoundTrack();
                                               
        JSlider volumeCtrl =new JSlider();
        volumeCtrl.setMinimum(-36);
        volumeCtrl.setMaximum(6);
        volumeCtrl.setBounds(50,10,800,60);
        volumeCtrl.setValue(-36);
        setVolume(volumeCtrl.getValue());
        JPanel   p =new JPanel();
        p.setBackground(Color.red);
        p.setBounds(0,700,1000,100);
        JLabel l = new  JLabel("vol. level");
        l.setBounds(3,10,100,20);
        JLabel t = new  JLabel("Triforce");
        t.setFont(new Font("arial",Font.ITALIC,22));
        t.setBounds(800,10,100,20);
        p.add(volumeCtrl);
        p.add(l);
        p.add(t);
        add(p);
        this.setBackground(Color.yellow);
        this.setFocusable(true);
        volumeCtrl.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int v = volumeCtrl.getValue();
                setVolume(v);
                requestFocus(true);
            }
            @Override
            public void mouseClicked(MouseEvent e) {                requestFocus(true);
}
            @Override
            public void mousePressed(MouseEvent e) {                requestFocus(true);
}
            @Override
            public void mouseEntered(MouseEvent e) {                requestFocus(true);
}
            @Override
            public void mouseExited(MouseEvent e) {                requestFocus(true);
}
                                                                    });
        
        requestFocus(true);

        createShiets();
        
        Thread thrd = new Thread() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(20);
                        xxx-=1;
                        xxy-=0;
                        for(int i=0; i<clouds.size(); i++) {
                            clouds.get(i).x = clouds.get(i).x-5;
                        }
                        lives--;
                        setTitle("Lives Forever: "+lives);
                        in = false;
                        for(int i=0; i<trees.size(); i++) {
                            int xbegin = trees.get(i).x+x;
                            int ybegin = trees.get(i).y+y;
                            int xend = trees.get(i).x+x+300;
                            int yend = trees.get(i).y+y+300;
                            if(490 >= xbegin && 490 <= xend && 340 >= ybegin && 340 <= yend) {
                                in = true;
                            }
                        }
                        if(!in) {
                            lives-=6;
                        }
                        if(lives <= 0)
                            System.exit(-1);
                        repaint();
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }
        };

        thrd.start();

        createClouds();
        
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                //set graphics
                g2 = (Graphics2D) g.create();
                g2.setColor(Color.white);
                
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
                    g2.setColor(new Color(0,255,0));
                    g2.fillRect(trees.get(i).x+x, trees.get(i).y+y, 300, 300);
                }

                //draw random flags
                for(int i=0; i<flags.size(); i++) {
                    if(490 <= flags.get(i).x+x && 490 >= flags.get(i).x+x - 60 && 340 <= flags.get(i).y+y && 340 >= flags.get(i).y+y - 60) {
                        flags.remove(flags.get(i));
                        lives+=5000;
                        makeBeepSound();
                        continue;
                    }
                    g2.drawImage(img, flags.get(i).x+x, flags.get(i).y+y, 60, 60, null);
                }
                
                //draw random falgs
                for(int i=0; i<falgs.size(); i++) {
                    if(490 <= falgs.get(i).x+x && 490 >= falgs.get(i).x+x - 60 && 340 <= falgs.get(i).y+y && 340 >= falgs.get(i).y+y - 60) {
                        falgs.remove(falgs.get(i));
                        lives+=1000;
                        makeBeepSound();
                        continue;
                    }
                    g2.drawImage(m, falgs.get(i).x+x, falgs.get(i).y+y, 60, 60, null);
                }
                
                //draw man
                g2.drawImage(im, 490, 340, 40, 40, null);

                //draw clouds
                for(int i=0; i<clouds.size(); i++) {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillOval(clouds.get(i).x, clouds.get(i).y, 150, 100);
                }

                boolean b = false;
                for(int i=0; i<clouds.size(); i++) {
                    if(!(clouds.get(i).x < -10000)) {
                        b = true;
                    }
                }
                if(!b) {
                    createClouds();
                }
                
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
        jPanel.setBackground(new Color(172,215,255));

        add(jPanel);

        addKeyListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void makeBeepSound() {
        try {
            makeSound("beep.wav");
        } catch(Exception e1) {
            e1.printStackTrace();
        }
    }
    
    private void playSoundTrack() {
        try {
            playSound("raiden2lvl1.wav");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void makeSound(String file) throws Exception {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(file));

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip audioClip = (Clip) AudioSystem.getLine(info);

        audioClip.open(audioStream);
        audioClip.start();
    }

    private void playSound(String file) throws Exception {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(file));

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);

        audioClip.open(audioStream);
        audioClip.loop(100000000);
    }
    
    private void setVolume(int level) throws NullPointerException {
        FloatControl gainControl = 
            (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(level);
    }
}