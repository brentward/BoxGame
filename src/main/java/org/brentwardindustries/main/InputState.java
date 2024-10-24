package org.brentwardindustries.main;

import org.brentwardindustries.entity.Direction;

public class InputState {
    GamePanel gp;
    public Direction inputDirection;
//    public boolean up;
//    public boolean down;
//    public boolean left;
//    public boolean right;
    public boolean jump;
    public boolean dash;
    public boolean pause;
    public boolean accept;
    public int commandNum;

    public InputState(GamePanel gp) {
        this.gp = gp;
        inputDirection = Direction.CENTER;
    }

    public void reset() {
        inputDirection = Direction.CENTER;
        jump = false;
        dash = false;
        pause = false;
        accept = false;
        commandNum = 0;
    }

    public void commandNumUp(int max) {
        commandNum++;
        if (commandNum > max) {
            commandNum = 0;
        }
    }

    public void commandNumDown(int max) {
        commandNum--;
        if (commandNum < 0) {
            commandNum = max;
        }
    }

    public void update() {
        if (gp.gameState == GamePanel.TITLE_STATE) {
            if (accept) {
                if (gp.inputState.commandNum == 0) {
                    gp.resetGame();
                    gp.gameState = GamePanel.PLAY_STATE;
                    gp.playMusic(0);
                }
                if (gp.inputState.commandNum == 2) {
                    System.exit(0);
                }
                accept = false;
            }
        }
        if (gp.gameState == GamePanel.PLAY_STATE) {
            if (pause) {
                gp.gameState = GamePanel.PAUSE_STATE;
                gp.stopMusic();
                gp.playSE(5);
            }
        }
        if (gp.gameState == GamePanel.PAUSE_STATE) {
            if (!pause) {
                gp.gameState = GamePanel.PLAY_STATE;
                gp.playSE(5);
                gp.resumeMusic();
            }
        }
        if (gp.gameState == GamePanel.GAME_OVER_STATE) {
            if (accept) {
                if (gp.inputState.commandNum == 0) {
                    gp.resetGame();
                    gp.gameState = GamePanel.PLAY_STATE;
                    gp.playMusic(0);
                }
                if (gp.inputState.commandNum == 1) {
                    System.exit(0);
                }
            }
            accept = false;
        }
    }

}
