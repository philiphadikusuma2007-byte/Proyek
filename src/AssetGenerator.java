import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class AssetGenerator {
    public static ArrayList<Monsters> allMonsters = new ArrayList<>();
    private static java.util.HashMap<String, BufferedImage> imgCache = new java.util.HashMap<>();

    public static void generateAssets() {
        new File("assets/tiles").mkdirs();
        new File("assets/player").mkdirs();
        new File("assets/monsters").mkdirs();

        // Buat Placeholder Gambar Tile (32x32)
        if (!new File("assets/tiles/grass.png").exists()){
            saveImage("assets/tiles/grass.png", createProceduralTile(Color.GREEN, 0));
        }
        if (!new File("assets/tiles/road.png").exists()){
            saveImage("assets/tiles/road.png", createProceduralTile(Color.LIGHT_GRAY, 1));
        }
        if (!new File("assets/tiles/water.png").exists()){
            saveImage("assets/tiles/water.png", createProceduralTile(Color.BLUE, 2));
        }
        if (!new File("assets/tiles/tree.png").exists()){
            saveImage("assets/tiles/tree.png", createProceduralTile(new Color(34,139,34), 3));
        }
        if (!new File("assets/tiles/rock.png").exists()){
            saveImage("assets/tiles/rock.png", createProceduralTile(Color.GRAY, 4));
        }
        if (!new File("assets/tiles/house.png").exists()){
            saveImage("assets/tiles/house.png", createProceduralTile(Color.RED, 5));
        }
        if (!new File("assets/tiles/waypoint.png").exists()){
            saveImage("assets/tiles/waypoint.png", createProceduralTile(Color.CYAN, 6));
        }
        if (!new File("assets/tiles/pokemoncentre.png").exists()){
            saveImage("assets/tiles/pokemoncentre.png", createProceduralTile(Color.WHITE, 6));
        }

        // Buat Player Spritesheet (4 Frame Walk x 4 Arah = 128x128 px)
        File playerFile = new File("assets/player/player.png");
        if (!playerFile.exists()) {
            // Jika gambar custom kamu belum ada di folder, baru program membuatkan placeholder
            BufferedImage pSheet = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = pSheet.createGraphics();
            Color[] dirColors = {Color.YELLOW, Color.ORANGE, Color.PINK, Color.MAGENTA};
            for(int d=0; d<4; d++) {
                for(int f=0; f<4; f++) {
                    g.setColor(dirColors[d]);
                    g.fillRect(f*32 + 4, d*32 + 4, 24, 24);
                    g.setColor(Color.BLACK);
                    g.drawString("P"+f, f*32+8, d*32+20);
                }
            }
            g.dispose();
            saveImage("assets/player/player.png", pSheet);
        }

        // Daftarkan & Buat Database Gambar 20 Monsters Berbeda
        initMonstersDatabase();
        for(Monsters m : allMonsters) {
            BufferedImage mImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D mg = mImg.createGraphics();
            mg.setColor(m.rarity.color);
            mg.fillOval(8, 8, 48, 48);
            mg.setColor(Color.WHITE);
            mg.setFont(new Font("Arial", Font.BOLD, 10));
            mg.drawString(m.name.substring(0, Math.min(m.name.length(), 5)), 12, 36);
            mg.dispose();
            
            String path = "assets/monsters/" + m.name.toLowerCase() + ".png";
            saveImage(path, mImg);
            imgCache.put(m.name, mImg);
        }
    }

    private static void initMonstersDatabase() {
        allMonsters.clear();
        // Common
        allMonsters.add(new Monsters("Leafy", Element.Grass, Rarity.Common, 80, 15, 12, 10));
        allMonsters.add(new Monsters("Pyropup", Element.Fire, Rarity.Common, 75, 18, 10, 12));
        allMonsters.add(new Monsters("Droplet", Element.Water, Rarity.Common, 90, 13, 15, 8));
        allMonsters.add(new Monsters("Sparky", Element.Electric, Rarity.Common, 70, 16, 9, 16));
        allMonsters.add(new Monsters("Pebblem", Element.Earth, Rarity.Common, 100, 12, 18, 5));
        // Rare
        allMonsters.add(new Monsters("Seedling", Element.Grass, Rarity.Rare, 110, 22, 19, 15));
        allMonsters.add(new Monsters("Embercat", Element.Fire, Rarity.Rare, 105, 26, 16, 20));
        allMonsters.add(new Monsters("Aquafox", Element.Water, Rarity.Rare, 125, 20, 22, 14));
        allMonsters.add(new Monsters("Voltmice", Element.Electric, Rarity.Rare, 100, 24, 15, 25));
        allMonsters.add(new Monsters("Granite", Element.Earth, Rarity.Rare, 140, 18, 26, 10));
        // Epic
        allMonsters.add(new Monsters("Volcanix", Element.Fire, Rarity.Epic, 150, 38, 25, 28));
        allMonsters.add(new Monsters("Hydroshell", Element.Water, Rarity.Epic, 180, 30, 35, 20));
        allMonsters.add(new Monsters("Stormbird", Element.Electric, Rarity.Epic, 140, 36, 22, 40));
        allMonsters.add(new Monsters("Terraking", Element.Earth, Rarity.Epic, 200, 28, 42, 15));
        allMonsters.add(new Monsters("Phantomur", Element.Shadow, Rarity.Epic, 130, 42, 20, 35));
        allMonsters.add(new Monsters("Aurorion", Element.Holy, Rarity.Epic, 160, 34, 30, 30));
        // Legendary
        allMonsters.add(new Monsters("Chronos", Element.Wind, Rarity.Legendary, 220, 52, 45, 48));
        allMonsters.add(new Monsters("Zephyrus", Element.Wind, Rarity.Legendary, 210, 55, 40, 55));
        allMonsters.add(new Monsters("Ragnarok", Element.Dark, Rarity.Legendary, 250, 60, 38, 42));
        allMonsters.add(new Monsters("Genesis", Element.Light, Rarity.Legendary, 240, 50, 50, 50));
    }

    public static BufferedImage getMonstersImage(String name) {
        return imgCache.get(name);
    }

    private static BufferedImage createProceduralTile(Color c, int patternType) {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, 32, 32);
        g.setColor(c.darker());
        if(patternType == 0) { g.drawLine(8, 8, 8, 12); g.drawLine(20, 20, 20, 24); } // Grass
        else if(patternType == 3) { g.fillOval(4, 4, 24, 24); } // Tree
        else if(patternType == 4) { g.fillRoundRect(6, 6, 20, 20, 5, 5); } // Rock
        else if(patternType == 6) { g.drawOval(2, 2, 28, 28); g.drawOval(8, 8, 16, 16); } // Waypoint
        g.dispose();
        return img;
    }

    private static void saveImage(String path, BufferedImage img) {
        try { ImageIO.write(img, "png", new File(path)); } catch(IOException e) { e.printStackTrace(); }
    }
}
