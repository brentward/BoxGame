package org.brentwardindustries.main;

import org.brentwardindustries.entity.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    HashSet<Integer> keysDown;
    boolean up, down, left, right;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
        keysDown = new HashSet<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!keysDown.contains(code)) {
            keysDown.add(code);
            inputPressed(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keysDown.remove(code);
        inputReleased(code);
    }

    public void inputPressed(int code) {
        if (code == KeyEvent.VK_W) {
            up = true;
        }
        if (code == KeyEvent.VK_S) {
            down = true;
        }
        if (code == KeyEvent.VK_A) {
            left = true;
        }
        if (code == KeyEvent.VK_D) {
            right = true;
        }
        setInputDirection();
        if (code == KeyEvent.VK_SPACE) {
            gp.inputState.jump = true;
        }
        if (code == KeyEvent.VK_J) {
            gp.inputState.dash = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.inputState.pause = true;
        }

    }

    public void inputReleased(int code) {
        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_A) {
            left = false;
        }
        if (code == KeyEvent.VK_D) {
            right = false;
        }
        setInputDirection();
        if (code == KeyEvent.VK_SPACE) {
            gp.inputState.jump = false;
        }
        if (code == KeyEvent.VK_J) {
            gp.inputState.dash = false;
        }
        if (code == KeyEvent.VK_P) {
            gp.inputState.pause = false;
        }
    }

    public void setInputDirection() {
        if (up && left) {
            gp.inputState.inputDirection = Direction.UP_LEFT;
        } else if (up && right) {
            gp.inputState.inputDirection = Direction.UP_RIGHT;
        } else if (down && right) {
            gp.inputState.inputDirection = Direction.DOWN_RIGHT;
        } else if (down && left) {
            gp.inputState.inputDirection = Direction.DOWN_LEFT;
        } else if (up) {
            gp.inputState.inputDirection = Direction.UP;
        } else if (right) {
            gp.inputState.inputDirection = Direction.RIGHT;
        } else if (down) {
            gp.inputState.inputDirection = Direction.DOWN;
        } else if (left) {
            gp.inputState.inputDirection = Direction.LEFT;
        } else {
            gp.inputState.inputDirection = Direction.CENTER;
        }
    }
}