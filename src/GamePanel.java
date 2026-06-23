
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
    private BufferedImage[] tileAssets = new BufferedImage[4];

    private final int max_map_col = 15;
    private final int max_map_row = 10;

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
                BufferedImage image = tileAssets[tileType];
                if (image != null) {
                    int xPos=(col*TILE_SIZE)-playerX;
                    int yPos=(row*TILE_SIZE)-playerY;
                    g2.drawImage(image, xPos, yPos,TILE_SIZE,TILE_SIZE,null);
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
            tileAssets[0]= ImageIO.read(getClass().getResource("/Assets/Map/Tiles/Grass_Middle.png"));
            tileAssets[1]= ImageIO.read(getClass().getResource("/Assets/Map/Tiles/Water_Middle.png"));
            tileAssets[2]= ImageIO.read(getClass().getResource("/Assets/Map/Tiles/Path_Middle.png"));
            tileAssets[3]= ImageIO.read(getClass().getResource("/Assets/Map/Tiles/FarmLand_Tile.png"));
        } catch (Exception e) {
            System.out.println("Error loading Assets");
            e.printStackTrace();
        }
    }
}
