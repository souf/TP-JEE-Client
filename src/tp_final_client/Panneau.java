package tp_final_client;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panneau extends JPanel {

    ImageIcon img ;
    URL path;
    int x, y, t = 0;

    // le constructeur	 
    public Panneau(String image, int x, int y, int t) {
        this.y = y;  // y hauteur
        this.x = x;  // x largeur
        this.t = t;
        img = new ImageIcon(image);
    }

    @Override
    public void paintComponent(Graphics g) {

        try {
            if (t == 0) {
                g.drawImage(img.getImage(), 0, 0, x, y, this);       // panneau image seulement
            } else if (t == 1) {                               // pang  remplissage seulement
                g.setColor(Color.darkGray);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            } else {                                // panh image + remplissage
                g.setColor(Color.darkGray);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.drawImage(img.getImage(), 0, 0, x, y, this);
            }
        } catch (Exception e) {
        }
    }
}
