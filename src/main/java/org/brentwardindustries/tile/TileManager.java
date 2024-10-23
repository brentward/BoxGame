package org.brentwardindustries.tile;

import org.brentwardindustries.main.GamePanel;
import org.brentwardindustries.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    ArrayList<String> damageStatus = new ArrayList<>();
    ArrayList<String> killStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // READ TILE DATA FILE
        InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("/maps/tiledata.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // GET TILE NAMES AND COLLISION INFO FROM THE FILE
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
                damageStatus.add(br.readLine());
                killStatus.add(br.readLine());
            };
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        // GET THE maxWorldCol
        is = Objects.requireNonNull(getClass().getResourceAsStream("/maps/world0area0.txt"));
        br = new BufferedReader(new InputStreamReader(is));


        try {
            line = br.readLine();
            String[] maxTile = line.split(" ");
            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            gp.maxWorldX = gp.maxWorldCol * gp.tileSize;
            gp.maxWorldY = gp.maxWorldRow * gp.tileSize;
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        loadMap("/maps/world0area0.txt", gp.world0Area0Map);
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;
            boolean damage;
            boolean kill;
            fileName = fileNames.get(i);
            collision = collisionStatus.get(i).equals("true");
            damage = damageStatus.get(i).equals("true");
            kill = killStatus.get(i).equals("true");
            setup(i, fileName, collision, damage, kill);
        }
    }

    public void setup(int index, String imageName, boolean collision, boolean damage, boolean kill) {
        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"
                    + imageName)));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            tile[index].damage = damage;
            tile[index].kill = kill;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2D) {
        int worldCol = -gp.maxScreenCol;
        int worldRow = -gp.maxScreenRow;

        while (worldCol < (gp.maxWorldCol + gp.maxScreenCol)
                && worldRow < (gp.maxWorldRow + gp.maxScreenRow)) {
            int colIndex = (worldCol % gp.maxWorldCol + gp.maxWorldCol) % gp.maxWorldCol;
            int rowIndex = (worldRow % gp.maxWorldRow + gp.maxWorldRow) % gp.maxWorldRow;

            int tileNum = mapTileNum[gp.currentMap][colIndex][rowIndex];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.solidArea.x + gp.player.screenX;
            int screenY = worldY - gp.player.solidArea.y + gp.player.screenY;

            if (worldX > gp.player.solidArea.x - gp.player.screenX - gp.tileSize
                    && worldX < gp.player.solidArea.x + gp.player.screenX + gp.tileSize
                    && worldY > gp.player.solidArea.y - gp.player.screenY - gp.tileSize
                    && worldY < gp.player.solidArea.y + gp.player.screenY+ gp.tileSize) {
                g2D.drawImage(tile[tileNum].image, screenX, screenY,null);
            }
            worldCol++;
            if (worldCol == gp.maxWorldCol + gp.maxScreenCol) {
                worldCol = -gp.maxScreenCol;
                worldRow++;

            }
        }
    }
}
