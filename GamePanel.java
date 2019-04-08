package triforce;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends javax.swing.JFrame implements KeyListener {

    JPanel jPanel;
    
    int  x,y;

    public void keyTyped(KeyEvent ke) {
        
    }
    public void keyReleased(KeyEvent ke) {
        
    }
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
            x-=10;
        if(ke.getKeyCode() == KeyEvent.VK_LEFT)
            x+=10;
        if(ke.getKeyCode() == KeyEvent.VK_UP)
            y-=10;
        if(ke.getKeyCode() == KeyEvent.VK_DOWN)
            y+=10;
        jPanel.repaint();
    }
    
    public GamePanel() {
        setTitle("centering");
        setLayout(null);
        setBounds(0, 0, 1000, 700);
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.red);
                int        xx;
                int yy;
                if(x > 0 && y > 0) {
                    for (int i = 0; i < 1000+x; i += 50) {
                        g2.drawLine(x+i,y+ 0, x+i, y+700);
                    }
                    for (int j = 0; j < 700+y; j += 50) {
                        g2.drawLine(x+0,y+ j,x+ 1000,y+ j);
                    }
                } else if(x > 0 && y < 0) {
                    for (int i = 0; i < 1000+x; i += 50) {
                        g2.drawLine(x+i,y+ 0, x+i, y+700);
                    }
                    for (int j = 0; j < 700+y; j += 50) {
                        g2.drawLine(x+0,y+ j,x+ 1000,y+ j);
                    }
                } else if(x < 0 && y < 0) {
                    for (int i = x; i < 1000; i += 50) {
                        g2.drawLine(x+i,y+ 0, x+i, y+700);
                    }
                    for (int j = y; j < 700; j += 50) {
                        g2.drawLine(x+0,y+ j,x+ 1000,y+ j);
                    }
                } else if(x < 0 && y > 0) {
                    for (int i = x; i < 1000; i += 50) {
                        g2.drawLine(x+i,y+ 0, x+i, y+700);
                    }
                    for (int j = 0; j < 700+y; j += 50) {
                        g2.drawLine(x+0,y+ j,x+ 1000,y+ j);
                    }
                }
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000,700);
            }
        };
        jPanel.setLayout(null);
        jPanel.setBounds(0, 0, 1000, 700);
        add(jPanel);
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        jPanel.repaint();
    }
}
