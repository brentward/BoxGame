package org.brentwardindustries.entity;

import org.brentwardindustries.main.GamePanel;

import java.awt.Rectangle;

public class Entity {
    GamePanel gp;
    public Rectangle solidArea = new Rectangle(0, 0, 0, 0);;

    public float maxHSpeed;
    public float acceleration;
    public float breaks;
    public float currentXSpeed;
    public float currentYSpeed = 0f;
    final public float gravity = 2.5f;
    public float terminalVelocity = 60f;
    public Direction direction;
    public boolean xCollision;
    public boolean yCollision;
    Direction dashDirection;
    int dashCounter;
    float jumpSpeed;
    float dashSpeed;

//    public int worldX;
//    public int worldY;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void speedUp() {
        if (currentXSpeed < maxHSpeed) {
            currentXSpeed += acceleration;
        }
        if (currentXSpeed > maxHSpeed) {
            currentXSpeed = maxHSpeed;
        }
    }

    public void speedDown() {
        if (currentXSpeed > 0) {
            currentXSpeed -= breaks;
        }
        if (currentXSpeed < 0) {
            currentXSpeed = 0;
        }
    }

    public void setDashSpeed() {
        dashDirection = gp.inputState.inputDirection;
        if (dashDirection == Direction.CENTER) {
            dashDirection = direction;
        }
        switch (dashDirection) {
            case UP_LEFT -> {
                currentYSpeed = -(0.71f * dashSpeed);
                currentXSpeed = -(0.71f * dashSpeed);
            }
            case UP -> {
                currentYSpeed = -dashSpeed;
                currentXSpeed = 0;
            }
            case UP_RIGHT -> {
                currentYSpeed = -(0.71f * dashSpeed);
                currentXSpeed = 0.71f * dashSpeed;
            }
            case RIGHT -> {
                currentYSpeed = 0;
                currentXSpeed = dashSpeed;
            }
            case DOWN_RIGHT -> {
                currentYSpeed = 0.71f * dashSpeed;
                currentXSpeed = 0.71f * dashSpeed;
            }
            case DOWN -> {
                currentYSpeed = dashSpeed;
                currentXSpeed = 0;
            }
            case DOWN_LEFT -> {
                currentYSpeed = 0.71f * dashSpeed;
                currentXSpeed = -(0.71f * dashSpeed);
            }
            case LEFT -> {
                currentYSpeed = 0;
                currentXSpeed = -dashSpeed;
            }
        }
    }
}
