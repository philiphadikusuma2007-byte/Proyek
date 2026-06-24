import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuManager {
    GamePanel gp;
    int sectionIndex = 0; // 0: Utama, 1: Inventory, 2: Fast Travel, 3: Koleksi Monster
    int selectIdx = 0;

    public MenuManager(GamePanel gp) { this.gp = gp; }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(20, 20, 30));
        g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));

        if(sectionIndex == 0) {
            g2.drawString("=== MENU PAUSE ===", 300, 80);
            g2.setColor(Color.YELLOW);
            g2.drawString("Gold Kamu : " + gp.gold + " Gold", 300, 110);
            g2.setColor(Color.WHITE);
            String[] items = {"1. Lanjutkan Game", 
                            "2. Buka Tas Inventory", 
                            "3. Fast Travel Map", 
                            "4. Koleksi Monster Anda", 
                            "5. Gacha (1x Pull) [-50 Gold]", 
                            "6. Gacha (10x Pull) [-500 Gold]",
                            "7. Shop", 
                            "8. Save Game", 
                            "9. Load Game"};
            for(int i=0; i<items.length; i++) {
                g2.drawString((selectIdx == i ? " > " : "   ") + items[i], 260, 160 + (i * 35));
            }
        } else if(sectionIndex == 1) {
            g2.drawString("=== INVENTORY PACK ===", 280, 80);
            for(int i=0; i<gp.inventory.size(); i++) {
                Items it = gp.inventory.get(i);
                g2.drawString((selectIdx == i ? " > " : "   ") + it.name + " (Banyaknya: " + it.qty + ")", 200, 160 + (i * 35));
            }
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            g2.drawString("Tekan ESC untuk Kembali", 300, 500);
        } else if(sectionIndex == 2) {
            g2.drawString("=== FAST TRAVEL WAYPOINT ===", 240, 80);
            for(int i=0; i<gp.waypoints.size(); i++) {
                Waypoints wp = gp.waypoints.get(i);
                String status = wp.discovered ? "[Terbuka - Enter Tok]" : "[Terkunci - Jelajahi Peta]";
                g2.drawString((selectIdx == i ? " > " : "   ") + wp.name + " -> " + status, 150, 160 + (i * 35));
            }
        } else if(sectionIndex == 3) {
            g2.drawString("=== KOLEKSI MONSTER (" + gp.team.size() + ") ===", 240, 60);
            int start = Math.max(0, selectIdx - 5);
            int end = Math.min(gp.team.size(), start + 6);
            int drawY = 120;
            for(int i=start; i<end; i++) {
                Monsters m = gp.team.get(i);
                g2.drawString((selectIdx == i ? " > " : "   ") + m.name + " Lv." + m.level + " (" + m.element + ") HP:" + m.hp + "/" + m.maxHp, 100, drawY);
                drawY += 40;
            }
        } else if (sectionIndex == 4){
            g2.drawString("=== ITEM SHOP ADVENTURER ===", 240, 80);
            g2.setColor(Color.YELLOW);
            g2.drawString("Gold Kamu : " + gp.gold + " Gold", 300, 110);
            g2.setColor(Color.WHITE);

            String[] shopItems = {
                "1. Potion (+50 HP)         [-20 Gold]",
                "2. Super Potion (+150 HP)  [-50 Gold]",
                "3. Revive                  [-100 Gold]",
                "4. Keluar dari Shop"
            };

            for(int i = 0; i < shopItems.length; i++){
                g2.drawString((selectIdx == i ? " > " : "   ") + shopItems[i], 200, 180 + (i * 40));
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE) { sectionIndex = 0; selectIdx = 0; gp.currentState = GameState.World; return; }

        int limit = 9;
        if(sectionIndex == 1) limit = gp.inventory.size();
        if(sectionIndex == 2) limit = gp.waypoints.size();
        if(sectionIndex == 3) limit = gp.team.size();
        if(sectionIndex == 4) limit = 4;

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) selectIdx = (selectIdx - 1 + limit) % limit;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) selectIdx = (selectIdx + 1) % limit;

        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if(sectionIndex == 0) {
                if(selectIdx == 0) gp.currentState = GameState.World;
                else if(selectIdx == 1) { sectionIndex = 1; selectIdx = 0; }
                else if(selectIdx == 2) { sectionIndex = 2; selectIdx = 0; }
                else if(selectIdx == 3) { sectionIndex = 3; selectIdx = 0; }
                else if(selectIdx == 4) gp.gachaEngine.doPull(1);
                else if(selectIdx == 5) gp.gachaEngine.doPull(10);
                else if(selectIdx == 6) { sectionIndex = 4; selectIdx = 0;}
                else if(selectIdx == 7) saveGame();
                else if(selectIdx == 8) loadGame();
            } else if (sectionIndex == 4){
                if (selectIdx == 0) beliItem("Potion", "POTION", 20);
                else if (selectIdx == 1) beliItem("Super Potion", "SUPERPOTION", 50);
                else if (selectIdx == 2) beliItem("Revive", "REVIVE", 100);
                else if (selectIdx == 3) {sectionIndex = 0; selectIdx = 0;}
            }else if(sectionIndex == 2) { // Logic Teleport
                Waypoints wp = gp.waypoints.get(selectIdx);
                if(wp.discovered) {
                    gp.playerX = wp.x * Game.tileSize;
                    gp.playerY = wp.y * Game.tileSize;
                    sectionIndex = 0; gp.currentState = GameState.World;
                    JOptionPane.showMessageDialog(gp, "Teleport sukses menuju " + wp.name);
                } else {
                    JOptionPane.showMessageDialog(gp, "Waypoint ini belum Anda temukan di World Map!");
                }
            }
        }
    }

    private void saveGame() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            oos.writeInt(gp.playerX); oos.writeInt(gp.playerY);
            oos.writeObject(gp.team); oos.writeObject(gp.inventory);
            oos.writeObject(gp.waypoints);
            JOptionPane.showMessageDialog(gp, "Game Berhasil Disimpan!");
        } catch(Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void loadGame() {
        File f = new File("savegame.dat");
        if(!f.exists()) { JOptionPane.showMessageDialog(gp, "Data Save tidak ditemukan."); return; }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            gp.playerX = ois.readInt(); gp.playerY = ois.readInt();
            gp.team = (ArrayList<Monsters>) ois.readObject();
            gp.inventory = (ArrayList<Items>) ois.readObject();
            gp.waypoints = (ArrayList<Waypoints>) ois.readObject();
            JOptionPane.showMessageDialog(gp, "Game Berhasil Di-Load!");
            gp.currentState = GameState.World;
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void beliItem(String nama, String type, int price){
        if (gp.gold < price){
            JOptionPane.showMessageDialog(gp, "Gold kamu tidak cukup untuk membeli " + nama);
            return;
        }
        gp.gold -= price;
        boolean ditemukan = false;
        for(Items i : gp.inventory){
            if (i.type.equals(type)){
                i.qty++;
                ditemukan = true;
                break;
            }
        }
        JOptionPane.showMessageDialog(gp, "Berhasil membeli 1x " + nama + "!");
    }
}
