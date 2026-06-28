package Util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

import Model.*;
import Model.MonstersFile.Common.*;
import Model.MonstersFile.Rare.*;
import Model.MonstersFile.Epic.*;
import Model.MonstersFile.Legendary.*;

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
            String path = "assets/monsters/" + m.getName().toLowerCase() + ".png";
            File imgFile = new File(path);

            if (imgFile.exists()) {
                // Kalau gambar custom kamu sudah ada di folder, pakai itu, jangan generate ulang
                try {
                    BufferedImage customImg = ImageIO.read(imgFile);
                    if (customImg != null) {
                        imgCache.put(m.getName(), customImg);
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Kalau belum ada gambar custom, baru buat placeholder
            BufferedImage mImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D mg = mImg.createGraphics();
            mg.setColor(m.getRarity().getColor());
            mg.fillOval(8, 8, 48, 48);
            mg.setColor(Color.WHITE);
            mg.setFont(new Font("Arial", Font.BOLD, 10));
            mg.drawString(m.getName().substring(0, Math.min(m.getName().length(), 5)), 12, 36);
            mg.dispose();

            saveImage(path, mImg);
            imgCache.put(m.getName(), mImg);
        }
    }

    private static void initMonstersDatabase() {
        allMonsters.clear();
        // Common
        allMonsters.add(new Leafy());
        allMonsters.add(new Pyropup());
        allMonsters.add(new Droplet());
        allMonsters.add(new Sparky());
        allMonsters.add(new Pebblem());
        // Rare
        allMonsters.add(new Seedling());
        allMonsters.add(new Embercat());
        allMonsters.add(new Aquafox());
        allMonsters.add(new Voltmice());
        allMonsters.add(new Granite());
        // Epic
        allMonsters.add(new Volcanix());
        allMonsters.add(new HydroShell());
        allMonsters.add(new Stormbird());
        allMonsters.add(new Terraking());
        allMonsters.add(new Phantomur());
        allMonsters.add(new Aurorion());
        // Legendary
        allMonsters.add(new Chronos());
        allMonsters.add(new Zephyrus());
        allMonsters.add(new Ragnarok());
        allMonsters.add(new Genesis());
    }

    public static Monsters getMonsterByName(String name) {
    for (Monsters m : allMonsters) {
        if (m.getName().equalsIgnoreCase(name)) {
            return m.cloneMonster();
        }
    }
    return null;
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
