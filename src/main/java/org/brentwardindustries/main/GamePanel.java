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
    public final int halfTileSize = tileSize / 2;

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

    public InputState inputState = new InputState(this);
    public ControllerHandler controllerHandler = new ControllerHandler(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public KeyHandler keyHandler = new KeyHandler(this);
    public TileManager tileManager = new TileManager(this);
    public UI ui = new UI(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;

    public Player player = new Player(this);

    public int gameState;
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int GAME_OVER_STATE = 3;

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

    public void setupGame() {
        gameState = TITLE_STATE;
    }

    public void resetGame() {
        player.reset();
        inputState.reset();
    }

    public void killPlayer() {
        inputState.commandNum = -1;
        gameState = GAME_OVER_STATE;
        stopMusic();
        playSE(6);
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
        inputState.update();

        if (gameState == PLAY_STATE) {
            player.update();
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.playLoop();
    }

    public void resumeMusic() {
        music.playLoop();
    }

    public void stopMusic() {
        music.stop();
    }

    public  void endMusic() {
        music.end();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (gameState == TITLE_STATE) {
            ui.draw(g2D);
        } else {
            tileManager.draw(g2D);
            player.draw(g2D);
            ui.draw(g2D);
        }
        g2D.dispose();
    }
}
