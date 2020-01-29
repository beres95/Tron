package net.client;

import game.Game;
import net.NetUtils;
import gui.LanPreScreen;



import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static net.NetUtils.deserialisePoints;

public class GameClient implements Runnable {



    private final PrintWriter out;
    private final Scanner in;

    private Game game;
    private static LanPreScreen lps;






    public GameClient(String url, int port) throws IOException {
        Socket socket = new Socket(url, port);
        this.out = new PrintWriter(socket.getOutputStream());
        this.in = new Scanner(socket.getInputStream());
    }





    @Override
    public void run() {
        System.out.println("Client starting");
        out.println("START:SOMETHING");
        out.flush();
        while (in.hasNextLine()) {
            String message = in.nextLine();
            System.out.println(message);
            processMessage(message);
        }
        System.out.println("Client closed");
    }

    private void processMessage(String message) {
        String[] split = message.split(":");
        String tag = split[0];
        String data = split[1];


        switch (tag) {
            case "START":
                String[] dataParts = data.split(",");
                System.out.println("Connected as player: " + Integer.parseInt(dataParts[3]));
                game = new Game(
                        Integer.parseInt(dataParts[0]),
                        Integer.parseInt(dataParts[1]),
                        Integer.parseInt(dataParts[2])
                );
                break;
            case "UPDATE":
                game.update(deserialisePoints(data));
                break;
            case "STATE":
                game.setPlayerLocations(deserialisePoints(data));
                break;
        }
    }

    public void update(Point choice) {
        out.println("CHOICE:" + NetUtils.serialisePoint(choice));
        out.flush();
    }

    private Game getGame() {
        return game;
    }



    public static void RunGame(){
        try {
            GameClient client = new GameClient(lps.compNoValue, 54321);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        lps.p1Scores=10;
        lps.p2Scores=10;

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        GameClient client = new GameClient(lps.compNoValue, 54321);

        Thread thread = new Thread(client);
        thread.start();

        while (client.isWaiting()) {
            Thread.sleep(15);
        }

        frame.add(client.getGame());



        frame.addKeyListener(new KeyController(client));
        frame.pack();
        frame.setVisible(true);


        while(true){
            Thread.sleep(5);
            frame.repaint();
        }
    }

    private boolean isWaiting() {
        return game == null;
    }
}
