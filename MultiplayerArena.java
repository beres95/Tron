package local;

import javax.swing.*;
import java.awt.*;


public class MultiplayerArena extends JComponent {



    @Override
    public void paintComponent(Graphics g) {










        g.setColor(Color.white);
        g.drawRect(0,0,1300,595);
        g.fillRect(0, 0, 1300, 595);  //arena border

        g.setColor(Color.black);
        g.drawRect(6,6,881,622);
        g.fillRect(6, 6, 881, 622);  //arena

        g.setColor(Color.black);
        g.drawRect(895,6,375,622);
        g.fillRect(895, 6, 375, 622);  //score



        g.setColor(Color.CYAN);
        for (Point point: Multiplayer.bikeParts){
            g.fillRect(point.x*Multiplayer.SCALE,point.y*Multiplayer.SCALE, Multiplayer.SCALE, Multiplayer.SCALE);
        }
        g.fillRect(Multiplayer.bike.x*Multiplayer.SCALE, Multiplayer.bike.y*Multiplayer.SCALE,Multiplayer.SCALE,Multiplayer.SCALE);


        g.setColor(Color.MAGENTA);
        for (Point point: Multiplayer.bikeParts2){
            g.fillRect(point.x*Multiplayer.SCALE,point.y*Multiplayer.SCALE, Multiplayer.SCALE, Multiplayer.SCALE);
        }
        g.fillRect(Multiplayer.bike2.x*Multiplayer.SCALE, Multiplayer.bike2.y*Multiplayer.SCALE,Multiplayer.SCALE,Multiplayer.SCALE);


        g.setColor(Color.CYAN);
        g.drawString("Player 1 no.Wins: "+Multiplayer.p1Wins,925, 30);

        g.setColor(Color.MAGENTA);
        g.drawString("Player 2 no.Wins: "+Multiplayer.p2Wins,925, 50);







    }
}









