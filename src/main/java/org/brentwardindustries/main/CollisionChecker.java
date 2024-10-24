package org.brentwardindustries.main;

import org.brentwardindustries.entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkGrounded(Entity entity) {
        boolean grounded = false;
        int entityBottomY = entity.solidArea.y + entity.solidArea.height - 1;
        int entityLeftWorldX = entity.solidArea.x;
        int entityRightWorldX = entity.solidArea.x + entity.solidArea.width - 1;

        int entityLeftCol = (entityLeftWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityRightCol = (entityRightWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityBottomRow = ((entityBottomY + 1) / gp.tileSize) % gp.maxWorldRow;
        int tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
        int tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            grounded = true;
        }
        return grounded;
    }

    public void checkTile(Entity entity) {

        int xOffset = 0;
        int yOffset = 0;
//        int xSign = entity.getXSpeedSign();
//        int ySign = entity.getYSpeedSign();
//        if (entity.getXSpeed() > 0) {
//            xSign = 1;
//        } else if (entity.getXSpeed() < 0) {
//            xSign = -1;
//        }
//        if (entity.getYSpeed() > 0) {
//            ySign = 1;
//        } else if (entity.getYSpeed() < 0) {
//            ySign = -1;
//        }

        while (Math.abs(xOffset) < Math.abs(entity.getXSpeed())
                || Math.abs(yOffset) < Math.abs(entity.getYSpeed())) {
            xOffset += entity.getXSpeedSign() * gp.tileSize;
            yOffset += entity.getYSpeedSign() * gp.tileSize;
            if (Math.abs(xOffset) > Math.abs(entity.getXSpeed())) {
                xOffset = entity.getXSpeed();
            }
            if (Math.abs(yOffset) > Math.abs(entity.getYSpeed())) {
                yOffset = entity.getYSpeed();
            }
            int entityLeftWorldX = entity.solidArea.x + xOffset;
            int entityRightWorldX = entityLeftWorldX + entity.solidArea.width - 1;
            int entityTopWorldY = entity.solidArea.y + yOffset;
            int entityBottomWorldY = entityTopWorldY + entity.solidArea.height - 1;

            int entityLeftCol = ((entityLeftWorldX / gp.tileSize) % gp.maxWorldCol + gp.maxWorldCol) % gp.maxWorldCol;
            int entityRightCol = ((entityRightWorldX / gp.tileSize) % gp.maxWorldCol + gp.maxWorldCol) % gp.maxWorldCol;
            int entityTopRow = ((entityTopWorldY / gp.tileSize) % gp.maxWorldRow + gp.maxWorldRow) % gp.maxWorldRow;
            int entityBottomRow = ((entityBottomWorldY / gp.tileSize) % gp.maxWorldRow + gp.maxWorldRow) % gp.maxWorldRow;

            int tileNum1, tileNum2;


            if (entity.getYSpeed() > 0) {
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].kill || gp.tileManager.tile[tileNum2].kill) {
                    gp.inputState.commandNum = -1;
                    gp.gameState = GamePanel.GAME_OVER_STATE;
                }
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.yCollision = true;
                    entity.zeroYSpeed();
                    entity.solidArea.y = entityBottomRow * gp.tileSize - entity.solidArea.height;
                }
            }

            if (entity.getYSpeed() < 0) {
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].kill || gp.tileManager.tile[tileNum2].kill) {
                    gp.inputState.commandNum = -1;
                    gp.gameState = GamePanel.GAME_OVER_STATE;
                }
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.yCollision = true;
                    entity.zeroYSpeed();
                    entity.solidArea.y = entityTopRow * gp.tileSize + gp.tileSize;
                }
            }
            if (entity.getXSpeed() > 0) {
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].kill || gp.tileManager.tile[tileNum2].kill) {
                    gp.inputState.commandNum = -1;
                    gp.gameState = GamePanel.GAME_OVER_STATE;
                }
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.xCollision = true;
                    entity.solidArea.x = entityRightCol * gp.tileSize - entity.solidArea.width;
                    if (!entity.yCollision) {
                        entity.zeroXSpeed();
                    }
                }
            }

            if (entity.getXSpeed() < 0) {
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].kill || gp.tileManager.tile[tileNum2].kill) {
                    gp.inputState.commandNum = -1;
                    gp.gameState = GamePanel.GAME_OVER_STATE;
                }
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.xCollision = true;
                    entity.solidArea.x = entityLeftCol * gp.tileSize + gp.tileSize;
                    if (!entity.yCollision) {
                        entity.zeroXSpeed();
                    }
                }
            }
            if (entity.xCollision || entity.yCollision) {
                return;
            }
        }
    }

    public boolean inTile(Entity entity) {
        boolean inTile = false;
        int entityLeftWorldX = entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width - 1;
        int entityTopWorldY = entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height - 1;

        int entityLeftCol = (entityLeftWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityRightCol = (entityRightWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityBottomRow = (entityBottomWorldY / gp.tileSize) % gp.maxWorldRow;

        int tileNum1, tileNum2;
        tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            inTile = true;
        }

        return inTile;
    }
}
