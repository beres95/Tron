package net.server;
import net.NetUtils;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientConnection implements Runnable {

    private final PrintWriter out;
    private final Scanner in;
    private final String startInfo;
    private Point currentMove;

    public ClientConnection(Socket socket, String startInfo) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream());
        this.in = new Scanner(socket.getInputStream());
        this.startInfo = startInfo;
    }

    @Override
    public void run() {
        while (in.hasNextLine()) {
            String message = in.nextLine();
            System.out.println("Received: " + message);
            String[] split = message.split(":");
            String tag = split[0];
            String data = split[1];
            switch(tag){
                case "START":
                    out.println("START:" + startInfo);
                    out.flush();
                    break;
                case "CHOICE":
                    currentMove = NetUtils.deserialisePoint(data);
                    break;
            }
        }
    }

    public Point getCurrentMove() {
        return currentMove;
    }

    public void update(game.Game game) {
        out.println("STATE:" + NetUtils.serialisePoints(game.getPlayerLocations()));
        out.flush();
    }

    public void update(List<Point> updates) {
        out.println("UPDATE:" + NetUtils.serialisePoints(updates));
        out.flush();
    }

}
