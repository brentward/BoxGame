package org.brentwardindustries.entity;

import org.brentwardindustries.main.GamePanel;
import org.brentwardindustries.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    public Rectangle solidArea = new Rectangle(0, 0, 0, 0);;
    public BufferedImage[] standing = new BufferedImage[2];
    public BufferedImage[] jumping = new BufferedImage[2];
    public BufferedImage[] move1 = new BufferedImage[2];
    public BufferedImage[] move2 = new BufferedImage[2];
    public BufferedImage[] dash = new BufferedImage[2];
    int solidAreaXOffset;
    int solidAreaYOffset;

    public float maxHSpeed;
    public float acceleration;
    public float breaks;
    float currentXSpeed;
    float currentYSpeed;
    public float tempXSpeed;
    public float tempYSpeed;
    final public float gravity = 1.85f;
    public float terminalVelocity = 60f;
    public Direction direction;
    public boolean xCollision;
    public boolean yCollision;
    Direction dashDirection;
    int dashDuration;
    int dashTimer;
    int dashMax;
    int dashCount;
    float dashPower;
    boolean dashing;
    float jumpPower;
    boolean grounded;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getXSpeed() {
        return (int) currentXSpeed;
    }

    public int getXSpeedSign() {
        return (int) Math.signum(currentXSpeed);
    }

    public void zeroXSpeed() {
        currentXSpeed = 0f;
    }

    public void setXSpeed(float speed) {
        currentXSpeed = speed;
    }

    public void setXSpeed(int speed) {
        currentXSpeed = (float) speed;
    }

    public int getYSpeed() {
        return (int) currentYSpeed;
    }

    public int getYSpeedSign() {
        return (int) Math.signum(currentYSpeed);
    }

    public void zeroYSpeed() {
        currentYSpeed = 0f;
    }

    public void setYSpeed(float speed) {
        currentYSpeed = speed;
    }

    public void setYSpeed(int speed) {
        currentYSpeed = (float) speed;
    }

    public void setDashSpeed() {
        if (!dashing) {
            tempXSpeed = currentXSpeed;
            tempYSpeed = currentYSpeed;
        }
        dashDirection = gp.inputState.inputDirection;
        if (dashDirection == Direction.CENTER) {
            dashDirection = direction;
        }
        switch (dashDirection) {
            case UP_LEFT -> {
                currentYSpeed = -(0.71f * dashPower);
                currentXSpeed = -(0.71f * dashPower);
            }
            case UP -> {
                currentYSpeed = -dashPower;
                currentXSpeed = 0;
            }
            case UP_RIGHT -> {
                currentYSpeed = -(0.71f * dashPower);
                currentXSpeed = 0.71f * dashPower;
            }
            case RIGHT -> {
                currentYSpeed = 0;
                currentXSpeed = dashPower;
            }
            case DOWN_RIGHT -> {
                currentYSpeed = 0.71f * dashPower;
                currentXSpeed = 0.71f * dashPower;
            }
            case DOWN -> {
                currentYSpeed = dashPower;
                currentXSpeed = 0;
            }
            case DOWN_LEFT -> {
                currentYSpeed = 0.71f * dashPower;
                currentXSpeed = -(0.71f * dashPower);
            }
            case LEFT -> {
                currentYSpeed = 0;
                currentXSpeed = -dashPower;
            }
        }
    }
}
