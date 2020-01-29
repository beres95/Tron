package gui;

import local.Multiplayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GUI extends JFrame {

    static JFrame frame;
    JButton multiPlayer, LAN;


    public GUI() {

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.black);
        add(panel1, BorderLayout.NORTH);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.black);
        add(panel2, BorderLayout.CENTER);


        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("/Users/stevenberesford/Documents/uni/Final Year/TRON/src/extras/logo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panel2.add(picLabel);


        multiPlayer = new JButton("1v1");
        LAN = new JButton("Lan");



        panel1.add(multiPlayer);
        panel1.add(LAN);

        ButtonHandler bh=new ButtonHandler(this);
        multiPlayer.addActionListener(bh);
        LAN.addActionListener(bh);



    }




    class ButtonHandler implements ActionListener {
        GUI theApp;


        ButtonHandler(GUI app) {
            theApp = app;

        }

        public void actionPerformed(ActionEvent e) {


            if (e.getSource() == theApp.multiPlayer) {
                frame.dispose();
                new Multiplayer();


            }

            if (e.getSource() == theApp.LAN) {
                frame.dispose();
                new LanPreScreen();



            }


        }
    }

        public static void main(String[] args) {
        frame = new GUI();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setVisible(true);
        frame.setResizable(false);



    }


    }
