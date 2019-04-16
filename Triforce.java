package triforce;

import java.io.IOException;

public class Triforce {
    
    GamePanel gamePanel;
    
    public Triforce() {
        try {
            gamePanel = new GamePanel();
        } catch (IOException iOException) {
        }
    }

    public static void main(String[] args) {
        Triforce triForce = new Triforce();
    }    
}