package net.server;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameServer {

    public static final int PORT = 54321;

    private final Game game;

    private final int port;
    private final List<ClientConnection> connections;

    public GameServer(Game game, int port) {
        this.game = game;
        this.port = port;
        this.connections = new ArrayList<>(game.getNumPlayers());
    }

    public GameServer(Game game) {
        this(game, PORT);
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (connections.size() < game.getNumPlayers()) {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(
                        socket,
                        game.getWidth() + "," +
                                game.getHeight() + "," +
                                game.getNumPlayers() + "," +
                                connections.size()
                );
                connections.add(connection);
                new Thread(connection).start();
            }

            System.out.println("All clients connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameLoop() throws InterruptedException {
        int tickCount = 0;
        while (true) {
            List<Point> updates = connections.stream()
                    .map(ClientConnection::getCurrentMove)
                    .collect(Collectors.toList());
            game.update(updates);

            // Update each tick and reset every 10 ticks
//            if (tickCount % 10 == 0) {
//                connections.forEach(x -> x.update(game));
//            } else {
                connections.forEach(x -> x.update(updates));
//            }

            tickCount++;
            Thread.sleep(2);
        }
    }



    public static void main(String[] args) throws InterruptedException {

        Game game = new Game(600, 500, 3);
        GameServer gameServer = new GameServer(game, PORT);

        gameServer.listen();
        System.out.println("Stopped listening and now starting the game");
        gameServer.gameLoop();
    }

}
