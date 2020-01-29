package local;

import gui.GUI;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;



public class Multiplayer extends JPanel implements KeyListener { //implements ActionListener {

    public Timer t= new Timer(3,this::animate);
    public static ArrayList<Point> bikeParts = new ArrayList<>();
    public static ArrayList<Point> bikeParts2 = new ArrayList<>();
    public static int ticks = 0;
    Directions direction = Directions.RIGHT, direction2=Directions.LEFT;
    public enum Directions {UP, DOWN, LEFT, RIGHT};

    public static final int SCALE=6;


    public static int p1Wins=0, p2Wins=0;

    public static Point bike;
    public static Point bike2;
    public boolean over=false, end=false;
    public static JFrame mFrame;
    public static JFrame b2menu;
    public MultiplayerArena rp;
    public JPanel gamePanel, buttonPanel;

    JButton menu;




    public Multiplayer() {

        p1Wins=0;
        p2Wins=0;
        rp = new MultiplayerArena();
        mFrame = new JFrame();
        gamePanel=new JPanel();
        buttonPanel=new JPanel();

        gamePanel.setLayout(new BorderLayout());


        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.setSize(1500, 700);
        mFrame.setResizable(false);
        mFrame.addKeyListener(this);



        menu = new JButton("Menu");
        menu.setFocusable(false);

        gamePanel.add(rp);

        buttonPanel.add(menu);
        buttonPanel.setBackground(Color.black);

        ButtonHandler bh=new ButtonHandler(this);
        menu.addActionListener(bh);

        mFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        mFrame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        

        newRound();
        mFrame.setVisible(true);
    }

    public void newRound(){
        if (!end) {
            bikeParts.clear();
            bikeParts2.clear();
            direction = Directions.RIGHT;
            direction2 = Directions.LEFT;
            over = false;
            t.start();
            bike = new Point(10, 50);
            bike2 = new Point(140, 50);

        }



    }






    public void animate(ActionEvent ef) {
        rp.repaint();
        ticks++;

        if (bike.x == bike2.x && bike.y == bike2.y) {
            over=true;
        }


        if (ticks % 10 == 0 && bike != null && !over) {
            bikeParts.add(new Point(bike.x, bike.y));
            if (direction == Directions.UP) {
                if (bike.y - 1 > 0 && noTrailAt(bike.x, bike.y - 1) && noTrailAt2(bike.x, bike.y - 1)) {
                    bike = new Point(bike.x, bike.y - 1);

                } else {
                    over = true;
                    p2Wins++;


                }
            }

            if (direction == Directions.DOWN) {
                if (bike.y + 1 < 105 && noTrailAt(bike.x, bike.y + 1) && noTrailAt2(bike.x, bike.y + 1)) {
                    bike = new Point(bike.x, bike.y + 1);
                } else {
                    over = true;
                    p2Wins++;


                }
            }

            if (direction == Directions.LEFT) {
                if (bike.x - 1 > 0 && noTrailAt(bike.x - 1, bike.y) && noTrailAt2(bike.x - 1, bike.y)) {
                    bike = new Point(bike.x - 1, bike.y);
                } else {
                    over = true;
                    p2Wins++;


                }
            }


            if (direction == Directions.RIGHT) {
                if (bike.x + 1 < 148 && noTrailAt(bike.x + 1, bike.y) && noTrailAt2(bike.x + 1, bike.y)) {
                    bike = new Point(bike.x + 1, bike.y);
                } else {
                    over = true;
                    p2Wins++;


                }
            }


        }

        ////////////////////////////////////////////// second bike

        if (ticks % 10 == 0 && bike2 != null && !over) {
            bikeParts2.add(new Point(bike2.x, bike2.y));
            if (direction2 == Directions.UP) {
                if (bike2.y - 1 > 0 && noTrailAt(bike2.x, bike2.y - 1) && noTrailAt2(bike2.x, bike2.y - 1)) {
                    bike2 = new Point(bike2.x, bike2.y - 1);

                } else {
                    over = true;
                    p1Wins++;


                }
            }

            if (direction2 == Directions.DOWN) {
                if (bike2.y + 1 < 105 && noTrailAt(bike2.x, bike2.y + 1) && noTrailAt2(bike2.x, bike2.y + 1)) {
                    bike2 = new Point(bike2.x, bike2.y + 1);
                } else {
                    over = true;
                    p1Wins++;


                }
            }

            if (direction2 == Directions.LEFT) {
                if (bike2.x - 1 > 0 && noTrailAt(bike2.x - 1, bike2.y) && noTrailAt2(bike2.x - 1, bike2.y)) {
                    bike2 = new Point(bike2.x - 1, bike2.y);
                } else {
                    over = true;
                    p1Wins++;


                }
            }


            if (direction2 == Directions.RIGHT) {
                if (bike2.x + 1 < 148 && noTrailAt(bike2.x + 1, bike2.y) && noTrailAt2(bike2.x + 1, bike2.y)) {
                    bike2 = new Point(bike2.x + 1, bike2.y);
                } else {
                    over = true;
                    p1Wins++;


                }
            }
        }
    }





    public boolean noTrailAt(int x, int y)  //checks if first bike
    {
        for (Point point : bikeParts)
        {
            if (point.equals(new Point(x, y)))
            {

                return false;

            }
        }
        return true;
    }

    public boolean noTrailAt2(int x, int y)  //checks if second bike
    {
        for (Point point : bikeParts2)
        {
            if (point.equals(new Point(x, y)))
            {

                return false;

            }
        }
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i=e.getKeyCode();
        int x=e.getKeyCode();

        if(i==KeyEvent.VK_D && direction!=Directions.LEFT){
            direction=Directions.RIGHT;
        }

        if(i==KeyEvent.VK_A && direction!=Directions.RIGHT){
            direction=Directions.LEFT;
        }

        if(i==KeyEvent.VK_S && direction!=Directions.UP){
            direction=Directions.DOWN;
        }
        if(i==KeyEvent.VK_W && direction!=Directions.DOWN){
            direction=Directions.UP;
        }


        if(i==KeyEvent.VK_SPACE && over){
            if(!end){
                newRound();
            }


        }

        ///////////////player 2 controls

        if(x==KeyEvent.VK_RIGHT && direction2!=Directions.LEFT){
            direction2=Directions.RIGHT;
        }

        if(x==KeyEvent.VK_LEFT && direction2!=Directions.RIGHT){
            direction2=Directions.LEFT;
        }

        if(x==KeyEvent.VK_DOWN && direction2!=Directions.UP){
            direction2=Directions.DOWN;
        }
        if(x==KeyEvent.VK_UP && direction2!=Directions.DOWN){
            direction2=Directions.UP;
        }



    }

    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class ButtonHandler implements ActionListener {
        Multiplayer theApp;

        ButtonHandler(Multiplayer app) {
            theApp = app;

        }

        public void actionPerformed(ActionEvent e) {


            if (e.getSource() == theApp.menu) {
                mFrame.dispose();
                b2menu=new GUI();
                b2menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                b2menu.setSize(900, 700);
                b2menu.setVisible(true);
                b2menu.setResizable(false);


            }


        }
    }
}
