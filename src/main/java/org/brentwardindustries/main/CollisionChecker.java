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

//    public void ground(Entity entity) {
//        while (!checkGrounded(entity)) {
//            entity.solidArea.y += 1;
//            entity.solidArea.y = (entity.solidArea.y % gp.maxWorldY + gp.maxWorldY) % gp.maxWorldY;
//        }
//    }
//
//    public boolean checkLeft(Entity entity) {
//        boolean grounded = false;
//        int entityTopY = entity.solidArea.y;
//        int entityBottomY = entity.solidArea.y + entity.solidArea.height - 1;
//        int entityLeftWorldX = entity.solidArea.x;
//
//        int entityLeftCol = ((entityLeftWorldX - 1) / gp.tileSize) % gp.maxWorldCol;
//        int entityTopRow = (entityTopY / gp.tileSize) % gp.maxWorldRow;
//        int entityBottomRow = (entityBottomY / gp.tileSize) % gp.maxWorldRow;
//        int tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
//        int tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
//        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//            grounded = true;
//        }
//        return grounded;
//    }
//
//    public boolean checkRight(Entity entity) {
//        boolean grounded = false;
//        int entityTopY = entity.solidArea.y;
//        int entityBottomY = entity.solidArea.y + entity.solidArea.height - 1;
//        int entityRightWorldX = entity.solidArea.x + entity.solidArea.width - 1;
//
//        int entityRightCol = ((entityRightWorldX + 1) / gp.tileSize) % gp.maxWorldCol;
//        int entityTopRow = (entityTopY / gp.tileSize) % gp.maxWorldRow;
//        int entityBottomRow = (entityBottomY / gp.tileSize) % gp.maxWorldRow;
//        int tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
//        int tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
//        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//            grounded = true;
//        }
//        return grounded;
//    }
//
//    public void pushLeft(Entity entity) {
//        while (!checkLeft(entity)) {
//            entity.solidArea.x -= 1;
//            entity.solidArea.x = (entity.solidArea.x % gp.maxWorldX + gp.maxWorldX) % gp.maxWorldX;
//        }
//    }
//
//    public void pushRight(Entity entity) {
//        while (!checkRight(entity)) {
//            entity.solidArea.x += 1;
//            entity.solidArea.x = (entity.solidArea.x % gp.maxWorldX + gp.maxWorldX) % gp.maxWorldX;
//        }
//
//    }


    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.solidArea.x + (int) entity.currentXSpeed;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width - 1;
        int entityTopWorldY = entity.solidArea.y + (int) entity.currentYSpeed;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height - 1;

        int entityLeftCol = (entityLeftWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityRightCol = (entityRightWorldX / gp.tileSize) % gp.maxWorldCol;
        int entityTopRow = (entityTopWorldY / gp.tileSize) % gp.maxWorldRow;
        int entityBottomRow = (entityBottomWorldY / gp.tileSize) % gp.maxWorldRow;

        int tileNum1, tileNum2;


        if (entity.currentYSpeed > 0) {
            tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.yCollision = true;
                entity.currentYSpeed = 0;
                entity.solidArea.y = entityBottomRow * gp.tileSize - entity.solidArea.height;
                return;
//                ground(entity);
            }
        }

        if (entity.currentYSpeed < 0) {
            tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.yCollision = true;
                entity.currentYSpeed = 0;
                entity.solidArea.y = entityTopRow * gp.tileSize + gp.tileSize;
                return;
            }
        }
        if (entity.currentXSpeed > 0) {
            tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.xCollision = true;
                entity.solidArea.x = entityRightCol * gp.tileSize - entity.solidArea.width;
                entity.currentXSpeed = 0;
//                pushRight(entity);
            }
        }

        if (entity.currentXSpeed < 0) {
            tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                entity.xCollision = true;
                entity.solidArea.x = entityLeftCol * gp.tileSize + gp.tileSize;
                entity.currentXSpeed = 0;
//                pushLeft(entity);
            }
        }

//        switch (entity.direction) {
//            case UP -> {
//                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
//            }
//            case DOWN -> {
//                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
//                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
//            }
//            case LEFT -> {
//                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
//            }
//            case RIGHT -> {
//                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
//            }
//        }
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
