package org.brentwardindustries.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    GamePanel gp;
    Graphics2D g2D;
    public Font font;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream inputStream = Objects.requireNonNull(
                    getClass().getResourceAsStream("/fonts/maru_monica.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }
    public void draw(Graphics2D g2D) {
        this.g2D = g2D;

        g2D.setFont(font);
        g2D.setColor(Color.WHITE);

        if (gp.gameState == GamePanel.TITLE_STATE) {
            drawTitleScreen();
        }
        if (gp.gameState == GamePanel.PLAY_STATE) {
        }
        if (gp.gameState == GamePanel.PAUSE_STATE) {
            drawPauseScreen();
        }
        if (gp.gameState == GamePanel.GAME_OVER_STATE) {
            drawGameOverScreen();
        }
    }

    public void drawTitleScreen() {
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Box Game";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2D.setColor(Color.GRAY);
        g2D.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x, y);

        // BLUE BOY IMAGE
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize * 2;
        g2D.fillRect(x, y, gp.player.solidArea.width * 2, gp.player.solidArea.height * 2);

        // MENU
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48f));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3 + gp.halfTileSize;
        g2D.drawString(text, x, y);
        if (gp.inputState.commandNum == 0) {
            g2D.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2D.drawString(text, x, y);
        if (gp.inputState.commandNum == 1) {
            g2D.drawString(">", x - gp.tileSize, y);
        }


        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2D.drawString(text, x, y);
        if (gp.inputState.commandNum == 2) {
            g2D.drawString(">", x - gp.tileSize, y);
        }

    }

    public void drawPauseScreen() {
        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 80f));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2D.drawString(text, x, y);
    }

    public void drawGameOverScreen() {
        g2D.setColor(new Color(0, 0 , 0, 150));
        g2D.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        // SHADOW
        g2D.setColor(Color.BLACK);
        x = getXForCenteredText(text);
        y = gp.tileSize * 5;
        g2D.drawString(text, x, y);
        // MAIN
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x - 4, y - 4);

        // RETRY
        g2D.setFont(g2D.getFont().deriveFont(50f));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2D.drawString(text, x, y);
        if (gp.inputState.commandNum == 0) {
            g2D.drawString(">", x - 25, y);
        }

        // BACK
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2D.drawString(text, x, y);
        if (gp.inputState.commandNum == 1) {
            g2D.drawString(">", x - 25, y);
        }
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
