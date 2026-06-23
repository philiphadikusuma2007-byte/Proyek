
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    private BufferedImage tileImage;
    private int playerX = 0;
    private int playerY = 0;
    private final int TILE_SIZE = 64;
    private final int NUM_TILES = 60;
    private BufferedImage[] tileAssets = new BufferedImage[NUM_TILES];
    private String[] tileNames = {
        "grass", "tall_grass", "grass", "sand_light", "water", "water_edge", "rock", "water", "stump", "sign",
        "tree", "pine_tree", "small_tree", "bush", "flower_red", "flower", "log", "signboard", "mailbox", "rock",
        "fence", "gate", "wood_fence", "wood_fence_corner", "signboard", "rock_small", "rock_large", "rock_medium", "stepping_stone", "ladder",
        "house_red", "house_blue", "pokecenter", "pokecenter_alt", "shop", "house_green", "house_purple", "gym", "gate", "statue",
        "wood_bridge", "fence", "stone_bridge", "boulder", "gate", "cave_entrance", "rock", "mountain_large", "mountain_small", "rock_small",
        "flower_patch_pink", "flower_patch_orange", "flower_patch_blue", "flower_patch_yellow", "statue", "statue_2", "sandy_ground", "snow", "dark_cave_floor", "whirlpool"
    };

    private final int max_map_col = 60;
    private final int max_map_row = 40;

    private int[][] worldMap = new int[max_map_row][max_map_col];

    public void loadmap(String filepath){
        try {
            InputStream a = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(a));

            for (int row = 0; row < max_map_row; row++) {
                String line = br.readLine();

                String[] angka = line.split("\\s+");

                for (int col = 0; col < max_map_col; col++) {
                    worldMap[row][col] = Integer.parseInt(angka[col]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error Load file map");
            e.printStackTrace();
        }
    }

    public GamePanel(){
        loadTileAssets();

        loadmap("/assets/Map/Tiles/map.txt");

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g ;

        for (int row = 0; row < worldMap.length; row++) {
            for (int col = 0; col < worldMap[0].length; col++) {
                int tileType = worldMap[row][col];
                if (tileType >= 0 && tileType < NUM_TILES) {
                    BufferedImage image = tileAssets[tileType];
                    if (image != null) {
                        int xPos=(col*TILE_SIZE)-playerX;
                        int yPos=(row*TILE_SIZE)-playerY;
                        g2.drawImage(image, xPos, yPos,TILE_SIZE,TILE_SIZE,null);
                    }
                }
            }
        }
        g2.setColor(Color.red);
        g2.fillRect(300, 300, 32, 32);
    }

    @Override
    public void run(){
        while (true) { 
            // playerX+=1;
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
            }
        }
    }
    private void loadTileAssets(){
        try {
            for (int i = 0; i < NUM_TILES; i++) {
                String path = "/assets/Map/Tiles/" + tileNames[i] + ".png";
                InputStream is = getClass().getResourceAsStream(path);
                if (is != null) {
                    tileAssets[i] = ImageIO.read(is);
                } else {
                    path = "/assets/Map/Tiles/" + tileNames[i] + ".png";
                    is = getClass().getResourceAsStream(path);
                    if (is != null) {
                        tileAssets[i] = ImageIO.read(is);
                    } else {
                        System.out.println("Warning: Could not find tile asset: " + tileNames[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading Assets");
            e.printStackTrace();
        }
    }
}
