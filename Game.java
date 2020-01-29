package game;



import gui.LanPreScreen;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;




public class Game extends JComponent {

    private LanPreScreen lps;




    private final int width;
    private int ticks=0;
    private final int height;
    private final int numPlayers;
    private List<Point> playerLocations;
    private final static Color[] COLORS = {Color.CYAN, Color.MAGENTA,Color.black};
    private Map<Integer, List<Point>> trails = new HashMap<>();

    public boolean gameOver=false;


    public Game(final int width, final int height, final int numPlayers) {
        this.width = width;
        this.height = height;
        this.numPlayers = numPlayers;
        this.playerLocations = new ArrayList<>();
        calculateStartLocations();

    }
    public void calculateStartLocations() {
        for (int i = 1; i <= numPlayers; i++) {
            Point startLocation = new Point((width / (numPlayers)) * i, height/ 2);
            playerLocations.add(startLocation);


        }

    }



    public void update(final List<Point> updates) {
        ticks++;

        if(lps.p1Scores >0 && lps.p2Scores>0) {

            if (!gameOver && ticks % 4 == 0) {

                for (int i = 0; i < numPlayers - 1; i++) {

                    trails.putIfAbsent(i, new ArrayList<>());

                    if (playerLocations.get(0).x == playerLocations.get(1).x && playerLocations.get(0).y == playerLocations.get(1).y) {
                        gameOver = true;
                    }

                    if (!noTrailAt(playerLocations.get(i).x, playerLocations.get(i).y)) {
                        gameOver = true;

                        if (i == 0) {
                            lps.p1Scores = lps.p1Scores-0.333;

                        }
                        if (i == 1) {
                            lps.p2Scores = lps.p2Scores-0.333;
                        }


                    }


                    if (playerLocations.get(i).x == width - 10) {
                        gameOver = true;
                        if (i == 0) {
                            lps.p1Scores = lps.p1Scores-0.333;

                        }
                        if (i == 1) {
                            lps.p2Scores = lps.p2Scores-0.333;
                        }
                    }

                    if (playerLocations.get(i).x == 5) {
                        gameOver = true;
                        if (i == 0) {
                            lps.p1Scores = lps.p1Scores-0.333;

                        }
                        if (i == 1) {
                            lps.p2Scores = lps.p2Scores-0.333;
                        }
                    }

                    if (playerLocations.get(i).y == height - 50) {
                        gameOver = true;
                        if (i == 0) {
                            lps.p1Scores = lps.p1Scores-0.333;

                        }
                        if (i == 1) {
                            lps.p2Scores = lps.p2Scores-0.333;
                        }
                    }

                    if (playerLocations.get(i).y == 5) {
                        gameOver = true;
                        if (i == 0) {
                            lps.p1Scores = lps.p1Scores-0.333;

                        }
                        if (i == 1) {
                            lps.p2Scores = lps.p2Scores-0.333;
                        }
                    }


                    if (playerLocations.size() != 0) {
                        trails.get(i).add(new Point(playerLocations.get(i)));

                    }

                    applyUpdate(playerLocations.get(i), updates.get(i));
                }
            }

            if (gameOver == true) {
                playerLocations.clear();
                trails.clear();
                calculateStartLocations();
                setPlayerLocations(playerLocations);
                gameOver = false;
            }
        }

    }


    private void applyUpdate(Point location, Point update) {
        if(update == null) return;
        location.x += update.x;
        location.y += update.y;
        location.x = (location.x + width) % width;
        location.y = (location.y + height) % height;
    }


    public boolean noTrailAt(int x, int y)
    {
        for(Map.Entry<Integer, List<Point>> entry : trails.entrySet()){
            for (Point point : entry.getValue()) {
                if (point.equals(new Point(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }



    public void LeaveTrail(Graphics2D g){
        for(Map.Entry<Integer, List<Point>> entry : trails.entrySet()){

            g.setColor(COLORS[entry.getKey()]);
            for (Point r : entry.getValue()) {
                g.fillRect(r.x, r.y, 6, 6);
            }

        }
    }
    @Override
    protected void paintComponent(Graphics render) {

        Graphics2D g = (Graphics2D) render.create();

        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.black);
        g.fillRect(5, 5, width-10, height-50);

        g.setColor(Color.black);
        g.fillRect(5, height-40, width-10, 35);

        g.setColor(Color.CYAN);
        g.drawString("P1 Lives Left: "+ (int) Math.round(lps.p1Scores),100, height-15);

        g.setColor(Color.MAGENTA);
        g.drawString("P2 Lives Left: "+ (int) Math.round(lps.p2Scores),300, height-15);





        for (int i = 0; i < numPlayers; i++) {
            g.setColor(COLORS[i]);
            Point location = playerLocations.get(i);
            g.fillRect(location.x, location.y, 6, 6);
        }
        LeaveTrail(g);


    }

    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public List<Point> getPlayerLocations() {
        return playerLocations;
    }

    public void setPlayerLocations(List<Point> playerLocations) {
        this.playerLocations = playerLocations;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Graphics Test");
        frame.setLayout(new FlowLayout());
        List<Game> games = new ArrayList<>();
        for(int i = 2; i <= COLORS.length; i++){
            games.add(new Game(400, 400, i));
        }
        games.forEach(frame::add);



        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        while(true){
            Thread.sleep(10);
            for(Game game : games) {
                List<Point> updates = IntStream
                        .range(0, game.getNumPlayers())
                        .boxed()
                        .map(x -> new Point(1, 0))
                        .collect(Collectors.toList());
                game.update(updates);
                game.repaint();
            }

        }
    }
}
