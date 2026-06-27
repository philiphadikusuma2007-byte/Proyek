package World;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

import Game.*;
import Model.*;
import Model.StatusEffectFile.*;
import UI.GamePanel;
import UI.Audio.Sound;

public class WorldManager {
    GamePanel gp;
    int[][] mapData = new int[40][60];
    BufferedImage[] tileImages = new BufferedImage[15]; // 0:Grass, 1:Road, 2:Water, 3:Tree, 4:Rock, 5:House, 6:Waypoint
    BufferedImage[][] playerFrames = new BufferedImage[4][4];
    int animFrame = 0, animTimer = 0;
    String alertMessage = "";

    public WorldManager(GamePanel gp) {
        this.gp = gp;
        loadTiles();
        generateProceduralMapFile();
        loadMapFile();
    }

    private void loadTiles() {
        try {
            tileImages[0] = ImageIO.read(new File("assets/tiles/grass.png"));
            tileImages[1] = ImageIO.read(new File("assets/tiles/road.png"));
            tileImages[2] = ImageIO.read(new File("assets/tiles/water.png"));
            tileImages[3] = ImageIO.read(new File("assets/tiles/tree.png"));
            tileImages[4] = ImageIO.read(new File("assets/tiles/rock.png"));
            tileImages[5] = ImageIO.read(new File("assets/tiles/house.png"));
            tileImages[6] = ImageIO.read(new File("assets/tiles/waypoint.png"));
            tileImages[7] = ImageIO.read(new File("assets/tiles/pokemoncentre.png"));
            String[] dirFolders = {"down", "up", "left", "right"};
            for (int dir = 0; dir < 4; dir++) {
                for (int f = 0; f < 4; f++) {
                    playerFrames[dir][f] = ImageIO.read(new File("assets/player/" + dirFolders[dir] + "/" + f + ".png"));
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void generateProceduralMapFile() {
        File f = new File("assets/world_map.txt");
        if(f.exists()) return;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            Random r = new Random();
            for(int y=0; y<40; y++) {
                StringBuilder line = new StringBuilder();
                for(int x=0; x<60; x++) {
                    // Penataan world sederhana luar peta batu/pohon, tengah rumput/jalan
                    if(x == 0 || y == 0 || x == 59 || y == 39) line.append("4 "); // Rock barrier
                    else if((x==5 && y==6) || (x==30 && y==20) || (x==54 && y==30)) line.append("6 "); // Waypoint
                    else if(x > 10 && x < 15 && y > 10 && y < 20) line.append("2 "); // Danau kecil air
                    else if(x % 20 == 0 || y % 20 == 0) line.append("1 "); // Jalur jalan raya
                    else if(r.nextDouble() < 0.15) line.append("3 "); // Pohon acak
                    else line.append("0 "); // Rerumputan liar
                }
                bw.write(line.toString().trim() + "\n");
            }
        } catch(IOException e) { e.printStackTrace(); }
    }

    private void loadMapFile() {
        try(BufferedReader br = new BufferedReader(new FileReader("assets/world_map.txt"))) {
            for(int y=0; y<40; y++) {
                String[] tokens = br.readLine().split(" ");
                for(int x=0; x<60; x++) {
                    mapData[y][x] = Integer.parseInt(tokens[x]);
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void update() {
        if(gp.isMoving) {
            int speed = 4;
            int nextX = gp.playerX, nextY = gp.playerY;
            if(gp.playerDirection == 0) nextY += speed;
            if(gp.playerDirection == 1) nextY -= speed;
            if(gp.playerDirection == 2) nextX -= speed;
            if(gp.playerDirection == 3) nextX += speed;

            // Collision check dengan tile solid (2:Water, 3:Tree, 4:Rock, 5:House)
            int tileX = nextX / Game.tileSize;
            int tileY = nextY / Game.tileSize;
            if (tileY >= 0 && tileY < 40 && tileX >= 0 && tileX < 60) {
                int tileSign = mapData[tileY][tileX];
                // ID 7 (Pokemon Centre) tidak dibuat solid agar player bisa menginjaknya/menyentuhnya
                if(tileSign != 2 && tileSign != 3 && tileSign != 4 && tileSign != 5) {
                    gp.playerX = nextX; gp.playerY = nextY;
                    // Cek Trigger ketika menginjak Tile ID 7 (Pokemon Centre)
                    if(tileSign == 7) {
                        gp.isMoving = false; // Hentikan pergerakan player sejenak
                        // Loop semua monster di tim player untuk memulihkan HP
                        for (Monsters m : gp.team) {
                            m.setHp(m.getMaxHp());
                            m.setCurrentStatus(new None()); // Sembuhkan juga efek status buruk jika ada
                        }
                        Sound.playSound("assets/sounds/healing.wav");

                        // Tampilkan pesan dialog sesuai permintaan
                        JOptionPane.showMessageDialog(gp, "Semua Evomon-mu sudah kembali pulih!");
                        // Opsional: Mundurkan player 1 tile atau geser sedikit agar tidak terus-terusan men-trigger dialog
                        if(gp.playerDirection == 0) gp.playerY -= Game.tileSize;
                        if(gp.playerDirection == 1) gp.playerY += Game.tileSize;
                        if(gp.playerDirection == 2) gp.playerX += Game.tileSize;
                        if(gp.playerDirection == 3) gp.playerX -= Game.tileSize;
                        return;
                    }
                    // Cek Trigger Random Encounter di Rumput (ID 0)
                    if(tileSign == 0 && new Random().nextDouble() < 0.005) {
                        boolean hidup = false;
                        for (Monsters m : gp.team){
                            if (m.getHp() > 0){
                                hidup = true;
                                break;
                            }
                        }
                        if (!hidup) {
                            gp.isMoving = false;
                            alertMessage = "❌ Semua Evomon-mu pingsan!\nPergilah ke Evomon Centre untuk memulihkan mereka!";
                            gp.currentState = GameState.Alert;
                            return;
                        }
                        gp.isMoving = false;
                        gp.battleEngine.startBattle();
                        return;
                    }
                }
            }
            // Cek Temuan Waypoint
            for(Waypoints wp : gp.waypoints) {
                if(tileX == wp.x && tileY == wp.y && !wp.discovered) {
                    gp.isMoving = false;
                    gp.playerX = wp.x * Game.tileSize;
                    gp.playerY = wp.y * Game.tileSize;
                    wp.discovered = true;
                    Sound.playSound("assets/sounds/waypoint-paid.wav");
                    JOptionPane.showMessageDialog(gp, "Waypoint Berhasil Dibuka: " + wp.name);
                    break;
                }
            }

            animTimer++;
            if(animTimer > 10) { animFrame = (animFrame + 1) % 4; animTimer = 0; }
        } else { animFrame = 0; }
    }

    public void draw(Graphics2D g2) {
        // Logika Hitung Kamera Center di Player
        int camX = -gp.playerX + Game.screenWidth/2 - Game.tileSize/2;
        int camY = -gp.playerY + Game.screenHeight/2 - Game.tileSize/2;

        // Render Map terbatas area pandang kamera (Culling)
        for(int y=0; y<40; y++) {
            for(int x=0; x<60; x++) {
                int worldX = x * Game.tileSize;
                int worldY = y * Game.tileSize;
                if(worldX + camX > -Game.tileSize && worldX + camX < Game.screenWidth &&
                   worldY + camY > -Game.tileSize && worldY + camY < Game.screenHeight) {
                    int tile = mapData[y][x];
                    if(tile == 3) { // TREE
                        // gambar grass dulu
                        g2.drawImage(tileImages[0],
                                    worldX + camX,
                                    worldY + camY,
                                    null);

                        // lalu gambar tree transparan
                        g2.drawImage(tileImages[3],
                                    worldX + camX,
                                    worldY + camY,
                                    null);
                    }

                    else if(tile == 4) { // ROCK
                        g2.drawImage(tileImages[2],
                                    worldX + camX, 
                                    worldY + camY, 
                                    null); // water
                        g2.drawImage(tileImages[4], 
                                    worldX + camX, 
                                    worldY + camY, 
                                    null); // rock
                    }

                    else {
                        g2.drawImage(tileImages[tile],
                                    worldX + camX,
                                    worldY + camY,
                                    null);
                    }
                }
            }
        }

        // Render Player sub-image sprite sheet (Row: arah, Col: frame)
        // Render Player dari frame-frame terpisah (per arah & per frame)
        BufferedImage currentFrame = playerFrames[gp.playerDirection][animFrame];
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        int drawSize = 48; 
        int screenX = Game.screenWidth / 2;
        int selisihTinggi = drawSize - Game.tileSize;
        int screenY = (Game.screenHeight / 2) - selisihTinggi;
        g2.drawImage(currentFrame,  screenX - (drawSize/2), screenY - (drawSize/2), drawSize, drawSize, null);

        // GUI Overlay Ringkas petunjuk
        Font fontBesar = new Font("Sans Serif", Font.BOLD, 18);
        g2.setFont(fontBesar);
        g2.setColor(Color.WHITE);
        String teksPetunjuk1 =  "Gunakan WASD/Arah Panah untuk Jalan";
        String teksPetunjuk2 = "Tekan ESC: Menu Utama & Gacha";
        String teksPetunjuk3 = "Tekan M: Mini Map";
        String teksPosisi = "Posisi: " + gp.playerX/32 + ", " + gp.playerY/32;
        String teksGold = "💰 Gold: " + gp.gold;
        String teksCheat = "[CHEAT] Tekan G : +500 Gold";
        drawStroke(g2, teksPetunjuk1, 20, 35, Color.WHITE);
        drawStroke(g2, teksPetunjuk2, 20, 65, Color.WHITE);
        drawStroke(g2, teksPetunjuk3, 20, 95, Color.WHITE);
        drawStroke(g2, teksPosisi, 20, 125, Color.WHITE);
        drawStroke(g2, teksGold, 20, 155, Color.YELLOW);
        drawStroke(g2, teksCheat, 20, 185, Color.CYAN);

        if (gp.currentState == GameState.Alert) {
            // 1. Kotak Background Hitam Transparan di Tengah Layar
            g2.setColor(new Color(0, 0, 0, 230));
            g2.fillRect(150, 180, 500, 200);
            
            // 2. Border/Garis Tepi Kotak Warna Merah Peringatan
            g2.setColor(Color.RED);
            g2.drawRect(150, 180, 500, 200);

            // 3. Cetak Tulisan Peringatan (Pakai fungsi stroke biar jelas)
            g2.setFont(new Font("SansSerif", Font.BOLD, 22));
            drawStroke(g2, "⚠️ PERINGATAN ⚠️", 310, 230, Color.RED);

            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            String title = "❌ Semua Evomon-mu pingsan!";
            FontMetrics fm = g2.getFontMetrics();
            int x = 150 + (500 - fm.stringWidth(title)) / 2;
            drawStroke(g2, title, x, 280, Color.WHITE);
            drawStroke(g2, "Pergilah ke Evomon Centre untuk memulihkan mereka!", 180, 310, Color.WHITE);

            // 4. Petunjuk Tombol Tutup
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            drawStroke(g2, "[ Tekan ENTER / SPASI untuk Melanjutkan ]", 260, 340, Color.GRAY);
        }
    }

    private void drawStroke(Graphics2D g2, String teks, int x, int y, Color warnaTeks){
        g2.setColor(Color.BLACK);
        g2.drawString(teks, x-1, y-1);
        g2.drawString(teks, x+1, y-1);
        g2.drawString(teks, x-1, y+1);
        g2.drawString(teks, x+1, y+1);
        g2.drawString(teks, x, y-1);
        g2.drawString(teks, x, y+1);
        g2.drawString(teks, x-1, y);
        g2.drawString(teks, x+1, y);

        g2.setColor(warnaTeks);
        g2.drawString(teks, x, y);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.currentState == GameState.Minimap) {
            if (code == KeyEvent.VK_M || code == KeyEvent.VK_ESCAPE) {
                gp.currentState = GameState.World;
            }
            return;
        }
        if (gp.currentState == GameState.Alert) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.currentState = GameState.World; // Kembalikan game ke normal
                alertMessage = ""; // Reset pesan
            }
            return; // Kunci agar player tidak bisa jalan selama alert aktif
        }
        if (code == KeyEvent.VK_G) {
            gp.gold += 500; // Tambah gold langsung ke GamePanel
            JOptionPane.showMessageDialog(gp, "CHEAT ACTIVATED GOLD +500");
            return;
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.playerDirection = 1; 
            gp.isMoving = true; 
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { 
            gp.playerDirection = 0;
            gp.isMoving = true; 
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) { 
            gp.playerDirection = 2; 
            gp.isMoving = true; 
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) { 
            gp.playerDirection = 3; 
            gp.isMoving = true;
         }
        if(code == KeyEvent.VK_ESCAPE){
            gp.currentState = GameState.Menu;
            return;
        }
        if(code == KeyEvent.VK_M) {
            gp.currentState = GameState.Minimap;
            return;
        }
    }

    public void drawMinimap(Graphics2D g2){
        int size = 10;

        int mapWidth = 60 * size;
        int mapHeight = 40 * size;

        int boxWidth = mapWidth + 20;
        int boxHeight = mapHeight + 20;

        // Kotak di tengah layar
        int boxX = (Game.screenWidth - boxWidth) / 2;
        int boxY = (Game.screenHeight - boxHeight) / 2;

        // Posisi map di dalam kotak
        int mapX = boxX + 10;
        int mapY = boxY + 10;

        g2.setColor(new Color(0,0,0,220));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        for(int y=0;y<40;y++){
            for(int x=0;x<60;x++){
                switch(mapData[y][x]){
                    case 0: g2.setColor(new Color(40,180,40)); break; // rumput
                    case 1: g2.setColor(Color.GRAY); break;          // jalan
                    case 2: g2.setColor(Color.BLUE); break;          // air
                    case 3: g2.setColor(new Color(20,100,20)); break;// pohon
                    case 4: g2.setColor(Color.DARK_GRAY); break;     // batu
                    case 5: g2.setColor(Color.ORANGE); break;        // rumah
                    case 6: g2.setColor(Color.YELLOW); break;        // waypoint
                    case 7: g2.setColor(Color.RED); break;           // centre
                }

                g2.fillRect(mapX+x*size,mapY+y*size,size,size);
            }
        }

        // Player
        g2.setColor(Color.WHITE);
        g2.fillOval( mapX+(gp.playerX/Game.tileSize)*size, mapY+(gp.playerY/Game.tileSize)*size, size,size);
        drawStroke(g2,"Mini Map (M / ESC untuk keluar)",170,470,Color.WHITE);
    }

    public int[][] getMapData() {
        return mapData;
    }

    public void keyReleased(KeyEvent e) { gp.isMoving = false; }
}
