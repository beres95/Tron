package gui;

import net.client.GameClient;
import net.server.GameServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LanPreScreen {

    public JPanel menuPanel, buttonPanel;
    public JFrame frame, b2menu;
    JButton menu, server, clientHost, ClientClient, Go;
    JTextField compNoText;


    public static double p1Scores=10;
    public static double p2Scores=10;



    public static String compNoValue;



    public LanPreScreen(){



        frame = new JFrame();

        menuPanel=new JPanel();
        buttonPanel=new JPanel();

        compNoText =new JTextField("Host Computer" ,10);

        menuPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setResizable(false);
        frame.setVisible(true);

        menu = new JButton("Menu");
        server = new JButton("Start Server");


        clientHost = new JButton("Player 1");
        clientHost.setForeground(Color.cyan);
        ClientClient = new JButton("Player 2");
        ClientClient.setForeground(Color.magenta);
        Go = new JButton("Start Game");










        menuPanel.add(menu);
        menuPanel.setBackground(Color.black);


        buttonPanel.add(compNoText);
        buttonPanel.add(server);

        buttonPanel.add(clientHost);
        buttonPanel.add(ClientClient);


        buttonPanel.add(Go);





        buttonPanel.setBackground(Color.black);

        ButtonHandler bh=new ButtonHandler(this);
        menu.addActionListener(bh);
        server.addActionListener(bh);
        clientHost.addActionListener(bh);
        ClientClient.addActionListener(bh);
        Go.addActionListener(bh);




        frame.getContentPane().add(menuPanel, BorderLayout.NORTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);



    }


    private class ButtonHandler implements ActionListener {
        LanPreScreen theApp;

        ButtonHandler(LanPreScreen app) {
            theApp = app;

        }

        public void actionPerformed(ActionEvent e) {


            if (e.getSource() == theApp.menu) {
                frame.dispose();
                b2menu=new GUI();
                b2menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                b2menu.setSize(900, 700);
                b2menu.setVisible(true);
                b2menu.setResizable(false);


            }

            if (e.getSource() == theApp.server) {
                Thread thread = new Thread (() -> {
                    try {
                        GameServer.main(new String[0]);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.start();


            }

            if (e.getSource() == theApp.clientHost) {
                compNoValue= compNoText.getText();

                Thread thread = new Thread (() -> {
                    try {
                        GameClient.main(new String[0]);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.start();


            }



            if (e.getSource() == theApp.ClientClient) {
                compNoValue = compNoText.getText();
                Thread thread = new Thread(() -> {
                    try {
                        GameClient.main(new String[0]);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.start();


            }



            if (e.getSource() == theApp.Go) {
                Thread thread = new Thread(() -> {
                    GameClient.RunGame();
                });
                thread.start();

            }


        }
    }


}
