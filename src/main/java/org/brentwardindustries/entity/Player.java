package org.brentwardindustries.entity;

import org.brentwardindustries.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity{
    public int screenX;
    public int screenY;

    public Player(GamePanel gp) {
        super(gp);

        screenX = gp.screenWidth / 2 - (solidArea.width / 2);
        screenY = gp.screenHeight / 2 - (solidArea.height / 2);
        setDefaultValues();
    }

    public void setDefaultValues() {
        solidArea.x = gp.tileSize * 7;
        solidArea.y = gp.tileSize * 230;
        solidArea.width = 24;
        solidArea.height = 32;
        maxHSpeed = 24f;
        acceleration = 1.5f;
        breaks = 4.15f;
        dashDuration = 10;
        dashMax = 3;
        dashCount = dashMax;
        jumpPower = 30f;
        dashPower = 56f;
        direction = Direction.RIGHT;

    }

    public void reset() {
        setDefaultValues();
    }

    public void update() {
        xCollision = false;
        yCollision = false;

        if (gp.inputState.dash) {
            if (dashCount > 0) {
                dashCount--;
                dashTimer = dashDuration;
                setDashSpeed();
                dashing = true;
                gp.inputState.dash = false;
            }
        }
        if (dashTimer > 0) {
            dashTimer--;
            if (dashTimer == 0) {
                dashing = false;
                currentXSpeed = tempXSpeed;
                currentYSpeed = tempYSpeed;
            }
        } else {
            if (gp.inputState.inputDirection.horizontalDirection() == Direction.LEFT) {
                if (direction == Direction.LEFT) {
                    if (currentXSpeed > -maxHSpeed) {
                        currentXSpeed -= acceleration;
                    }
                    if (currentXSpeed < -maxHSpeed) {
                        currentXSpeed = -maxHSpeed;
                    }
                } else if (direction == Direction.RIGHT) {
                    if (currentXSpeed == 0) {
                        direction = Direction.LEFT;
                    } else {
                        if (currentXSpeed < 0) {
                            currentXSpeed += breaks;
                        }
                        if (currentXSpeed > 0) {
                            currentXSpeed = 0;
                        }
                    }
                }
            } else if (gp.inputState.inputDirection.horizontalDirection() == Direction.RIGHT) {
                if (direction == Direction.RIGHT) {
                    if (currentXSpeed < maxHSpeed) {
                        currentXSpeed += acceleration;
                    }
                    if (currentXSpeed > maxHSpeed) {
                        currentXSpeed = maxHSpeed;
                    }
                } else if (direction == Direction.LEFT) {
                    if (currentXSpeed == 0) {
                        direction = Direction.RIGHT;
                    } else {
                        if (currentXSpeed > 0) {
                            currentXSpeed -= breaks;
                        }
                        if (currentXSpeed < 0) {
                            currentXSpeed = 0;
                        }
                    }
                }
            } else {
                if (currentXSpeed > 0) {
                    currentXSpeed -= breaks;
                    if (currentXSpeed < 0) {
                        currentXSpeed = 0;
                    }
                }
                if (currentXSpeed < 0) {
                    currentXSpeed += breaks;
                    if (currentXSpeed > 0) {
                        currentXSpeed = 0;
                    }
                }
            }

            if (gp.collisionChecker.checkGrounded(this)) {
                currentYSpeed = 0;
                dashCount = dashMax;
//                if (gp.collisionChecker.inTile(this)) {
//                    solidArea.y -= 1;
//                }
                if (gp.inputState.jump) {
                    currentYSpeed = -jumpPower;
                    gp.inputState.jump = false;
                }
            } else {
                currentYSpeed += gravity;
                if (currentYSpeed > terminalVelocity) {
                    currentYSpeed = terminalVelocity;
                }
            }
        }

        gp.collisionChecker.checkTile(this);

        if (!xCollision) {
            solidArea.x += (int) currentXSpeed;
            solidArea.x = (solidArea.x % gp.maxWorldX + gp.maxWorldX) % gp.maxWorldX;
        }
        if (!yCollision) {
            solidArea.y += (int) currentYSpeed;
            solidArea.y = (solidArea.y % gp.maxWorldY + gp.maxWorldY) % gp.maxWorldY;
        }
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.white);
        g2D.fillRect(screenX, screenY, solidArea.width, solidArea.height);
    }
}
