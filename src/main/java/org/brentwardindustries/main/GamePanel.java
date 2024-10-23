package org.brentwardindustries.main;

import org.brentwardindustries.entity.Player;
import org.brentwardindustries.tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    public final int tileSize = 48;

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;

    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public int maxWorldX;
    public int maxWorldY;
    public final int world0Area0Map = 0;
    public int currentMap = world0Area0Map;
    public final int maxMap = 10;

    int FPS = 60;

    public InputState inputState = new InputState();
    public ControllerHandler controllerHandler = new ControllerHandler(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;

    public Player player = new Player(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / (double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (double) (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
//                System.out.println("Alive");
            }
        }
    }

    public void update() {
        controllerHandler.pollController();
        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        tileManager.draw(g2D);
        player.draw(g2D);
        g2D.dispose();
    }
}
