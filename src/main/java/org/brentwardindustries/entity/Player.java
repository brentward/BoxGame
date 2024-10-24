package org.brentwardindustries.entity;

import org.brentwardindustries.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
        setImage();
        solidArea.x = gp.tileSize * 7;
        solidArea.y = gp.tileSize * 230;
        solidAreaXOffset = 12;
        solidAreaYOffset = 9;
        solidArea.width = 20;
        solidArea.height = 38;
        maxHSpeed = 28f;
        acceleration = 1.675f;
        breaks = 3.15f;
        dashDuration = 20;
        dashMax = 3;
        dashCount = dashMax;
        jumpPower = 24f;
        dashPower = 24f;
        direction = Direction.RIGHT;

    }

    public void setImage() {
        standing[0]  = setup("/player/standing_right", gp.tileSize, gp.tileSize);
        standing[1] = setup("/player/standing_left", gp.tileSize, gp.tileSize);
        jumping[0] = setup("/player/jump_right", gp.tileSize, gp.tileSize);
        jumping[1] = setup("/player/jump_left", gp.tileSize, gp.tileSize);
        move1[0] = setup("/player/walk_right_1", gp.tileSize, gp.tileSize);
        move1[1] = setup("/player/walk_left_1", gp.tileSize, gp.tileSize);
        move2[0] = setup("/player/walk_right_2", gp.tileSize, gp.tileSize);
        move2[1] = setup("/player/walk_left_2", gp.tileSize, gp.tileSize);
        dash[0] = setup("/player/dash", gp.tileSize, gp.tileSize);
        dash[1] = setup("/player/dash", gp.tileSize, gp.tileSize);
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
        BufferedImage image;
        int imageIndex = 0;
        if (direction.horizontalDirection() == Direction.LEFT) {
            imageIndex = 1;
        }
        if (dashing) {
            image = dash[imageIndex];
        } else if (!gp.collisionChecker.checkGrounded(this)) {
            image = jumping[imageIndex];
        } else if (getXSpeed() == 0) {
            image = standing[imageIndex];
        } else {
            if (solidArea.x % 200 < 100) {
                image = move1[imageIndex];
            } else {
                image = move2[imageIndex];
            }
        }

        g2D.setColor(Color.white);
        g2D.drawRect(screenX, screenY, solidArea.width, solidArea.height);

        g2D.drawImage(image, screenX - solidAreaXOffset, screenY - solidAreaYOffset, null);

    }
}
