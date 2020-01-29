package net.client;

import game.Game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {


    private GameClient gameClient;
    private Game game;

    private static final Point DOWN = new Point(0, 1);
    public static final Point UP= new Point(0, -1);
    private static final Point LEFT = new Point(-1, 0);
    private static final Point RIGHT = new Point(1, 0);
    private enum directions{UP, RIGHT, DOWN, LEFT};
    private directions lastDirection=directions.UP;







    public KeyController(GameClient gameClient) {
        this.gameClient = gameClient;
        gameClient.update(UP);




    }



    @Override
    public void keyPressed(KeyEvent e) {

        int i=e.getKeyCode();


        if(i==KeyEvent.VK_W) {
            if(lastDirection != directions.DOWN){
                gameClient.update(UP);
                lastDirection=directions.UP;

            }
        }

        if(i==KeyEvent.VK_A) {
            if (lastDirection != directions.RIGHT) {
                gameClient.update(LEFT);
                lastDirection=directions.LEFT;

            }
        }

        if(i==KeyEvent.VK_S) {
            if (lastDirection != directions.UP) {
                gameClient.update(DOWN);
                lastDirection=directions.DOWN;

            }
        }

        if(i==KeyEvent.VK_D) {
            if (lastDirection != directions.LEFT) {
                gameClient.update(RIGHT);
                lastDirection=directions.RIGHT;

            }
        }



    }
}
