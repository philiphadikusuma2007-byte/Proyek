package UI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

import Game.*;
import Model.*;
import Model.ItemsFIle.Potion;
import Model.ItemsFIle.ReviveItem;
import Model.ItemsFIle.SuperPotion;
import Util.AssetGenerator;
import World.Waypoints;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MenuManager {
    GamePanel gp;
    int sectionIndex = 0; // 0: Utama, 1: Inventory, 2: Fast Travel, 3: Koleksi Monster
    int selectIdx = 0;
    boolean gantiMonsters = false;
    boolean targetEquip = false;
    Items itemDiequip = null;
    
    public MenuManager(GamePanel gp) { 
        this.gp = gp; 
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(20, 20, 30));
        g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));

        if(sectionIndex == 0) {
            drawStroke(g2, "=== MENU PAUSE ===", 300, 80, Color.WHITE);
            drawStroke(g2, "Gold Kamu : " + gp.gold + " Gold", 300, 110, Color.YELLOW);
            drawStroke(g2, "Legendary Pity : " + gp.legendaryPity + "/30", 280, 135, Color.ORANGE);
            String[] items = {"1. Lanjutkan Game", 
                            "2. Buka Tas Inventory", 
                            "3. Fast Travel Map", 
                            "4. Koleksi EvomonMu", 
                            "5. Gacha (1x Pull) [-50 Gold]", 
                            "6. Gacha (10x Pull) [-500 Gold]",
                            "7. Shop", 
                            "8. Save Game", 
                            "9. Load Game",
                            "10. Exit Game"};
            for(int i=0; i<items.length; i++) {
                Color warnaOpsi = (selectIdx == i) ? Color.GREEN : Color.WHITE;
                drawStroke(g2, (selectIdx == i ? " > " : "   ") + items[i], 260, 185 + (i * 35), warnaOpsi);
            }
        } else if(sectionIndex == 1) {
            drawStroke(g2, "=== INVENTORY KAMU ===", 180, 60, Color.WHITE);
            int i = 0;
            int totalInventory = gp.inventory.size();
            for(i=0; i< totalInventory; i++) {
                Items it = gp.inventory.get(i);
                Color warnaItem = (selectIdx == i) ? Color.GREEN : Color.WHITE;
                String teksItem = (selectIdx == i ? " > " : "   ") + it.getName() + " ( " + it.getQty() + ")";
                drawStroke(g2, teksItem, 200, 160 + (i * 45), warnaItem);
            }

            String batal = (selectIdx == totalInventory ? " > " : "   ");
            Color warnaBatal = (selectIdx == totalInventory) ? Color.GREEN : Color.LIGHT_GRAY;
            drawStroke(g2, batal + "Kembali ke Menu Utama", 200, 160 + (i * 45), warnaBatal);

            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            drawStroke(g2, "[ W/S: Navigasi | ENTER: Cek Detail Item | ESC: Keluar ]", 180, 530, Color.GRAY);
        } else if(sectionIndex == 2) {
            drawStroke(g2, "=== FAST TRAVEL WAYPOINT ===", 240, 60, Color.WHITE);
            int i = 0;
            for(i=0; i<gp.waypoints.size(); i++) {
                Waypoints wp = gp.waypoints.get(i);
                String status = wp.isDiscovered() ? "[Terbuka - Enter]" : "[Terkunci]";
                Color warnaOpsi = (selectIdx == i) ? Color.GREEN : (wp.isDiscovered() ? Color.WHITE : Color.RED);
                drawStroke(g2, (selectIdx == i ? " > " : "   ") + wp.getName() + " -> " + status, 150, 160 + (i * 35), warnaOpsi);
            }
            Color warnaKeluar = (selectIdx == i) ? Color.GREEN : Color.LIGHT_GRAY;
            drawStroke(g2, (selectIdx == i ? " > " : "   ") + "Kembali ke Menu Utama", 150, 160 + (i * 35), warnaKeluar);
            
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            drawStroke(g2, "[ W/S: Navigasi | ENTER: Eksekusi Teleport | ESC: Keluar ]", 110, 530, Color.GRAY);
        } else if(sectionIndex == 3) { 
            drawStroke(g2, "=== EVOMON KAMU ===", 240, 60, Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            drawStroke(g2, "Pilih Evomon untuk dipasang (EQUIP) / Lepas dari Tim Aktif:", 180, 95, Color.CYAN);

            if (gp.storage.isEmpty()) {
                g2.setFont(new Font("SansSerif", Font.ITALIC, 16));
                drawStroke(g2, "Index kosong! Silakan lakukan Gacha terlebih dahulu.", 220, 200, Color.GRAY);
            } else {
                g2.setFont(new Font("Monospaced", Font.BOLD, 16));
                int totalMonster = gp.storage.size();
                int maksTampilan = 10; 
                int startIdx = (selectIdx >= maksTampilan) ? selectIdx - maksTampilan + 1 : 0;
                if (selectIdx == totalMonster) {
                    startIdx = Math.max(0, totalMonster - maksTampilan);
                }
                int endIdx = Math.min(startIdx + maksTampilan, totalMonster);
                int barisKe = 0; 
                for (int i = startIdx; i < endIdx; i++) {
                    Monsters m = gp.storage.get(i);
                    String penandaKursor = (selectIdx == i ? " > " : "   ");
                    String inisialRarity = "[C]"; 
                    if (m.getRarity() != null) {
                        inisialRarity = "[" + m.getRarity().name().substring(0, 1).toUpperCase() + "]";
                    }
                    String statusEquip = gp.team.contains(m) ? "[TIM AKTIF]" : "[DI INDEX]";
                    String infoMonster = String.format("%s %2d. %-5s %-12s Lv.%-2d  (HP: %3d/%3d)  %s", 
                        penandaKursor, (i + 1), inisialRarity, m.getName(), m.getLevel(), m.getHp(), m.getMaxHp(), statusEquip
                    );
                    Color warnaTeks = (selectIdx == i) ? Color.GREEN : (m.getHp() <= 0 ? Color.RED : Color.WHITE);
                    drawStroke(g2, infoMonster, 100, 140 + (barisKe * 35), warnaTeks);
                    barisKe++;
                }
                if (totalMonster > 0) {
                    String penandaBatal = (selectIdx == totalMonster ? " > " : "   ");
                    Color warnaBatal = (selectIdx == totalMonster) ? Color.GREEN : Color.LIGHT_GRAY;
                    drawStroke(g2, penandaBatal + "    " + "Kembali ke Menu Utama", 100, 140 + (barisKe * 35), warnaBatal);
                }
                g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
                drawStroke(g2, "Total Koleksi: " + totalMonster + " | Slot Terpilih: " + (selectIdx + 1) + " | Tim: " + gp.team.size() + "/6", 120, 540, Color.YELLOW);
            }
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            drawStroke(g2, "[ ENTER/SPACE: Pasang/Lepas Evomon | W/S: Navigasi | ESC: Keluar ]", 100, 565, Color.GRAY);
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
        if(code == KeyEvent.VK_ESCAPE) { 
            targetEquip = false;
            itemDiequip = null;
            sectionIndex = 0; 
            selectIdx = 0; 
            gp.currentState = GameState.World; 
            return; 
        }

        int limit = 1;
        if(sectionIndex == 0) limit = 10;
        else if(sectionIndex == 1) limit = gp.inventory.size() + 1;
        else if(sectionIndex == 2) limit = gp.waypoints.size() + 1;
        else if(sectionIndex == 3) limit = gp.storage.size() + 1;
        else if(sectionIndex == 4) limit = 4;
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) selectIdx = (selectIdx - 1 + limit) % limit;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) selectIdx = (selectIdx + 1) % limit;

        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if(sectionIndex == 0) {
                if(selectIdx == 0) gp.currentState = GameState.World;
                else if(selectIdx == 1) { sectionIndex = 1; selectIdx = 0; }
                else if(selectIdx == 2) { sectionIndex = 2; selectIdx = 0; }
                else if(selectIdx == 3) { sectionIndex = 3; selectIdx = 0; }
                else if(selectIdx == 4) doGacha(1);
                else if(selectIdx == 5) doGacha(10);
                else if(selectIdx == 6) { sectionIndex = 4; selectIdx = 0;}
                else if(selectIdx == 7) saveGame();
                else if(selectIdx == 8) loadGame();
                else if(selectIdx == 9){
                    int confirm = JOptionPane.showConfirmDialog(gp, "Apakah Anda yakin ingin keluar dari game?", "Keluar Game", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_NO_OPTION){
                        System.exit(0);
                    }
                }
            } else if (sectionIndex == 1) {
                if (selectIdx == gp.inventory.size()) {
                    sectionIndex = 0;
                    selectIdx = 1;
                    return;
                }
                if(gp.inventory.isEmpty()) return;
                Items itemTerpilih = gp.inventory.get(selectIdx);
                // Karena hanya ada Potion, Super Potion, & Revive, semuanya hanya bisa dipakai saat battle
                JOptionPane.showMessageDialog(gp, itemTerpilih.getName() + " adalah item pemulih! Gunakan item ini saat giliran bertarung (Battle) untuk memulihkan Evomon-mu.");
            } else if(sectionIndex == 2) { // Logic Teleport
                if (selectIdx == gp.waypoints.size()) {
                    sectionIndex = 0;
                    selectIdx = 2; // Sorot opsi Fast Travel kembali di main menu
                    return;
                }
                Waypoints wp = gp.waypoints.get(selectIdx);
                if(wp.isDiscovered()) {
                    gp.playerX = wp.getX() * Game.tileSize;
                    gp.playerY = wp.getY() * Game.tileSize;
                    sectionIndex = 0; gp.currentState = GameState.World;
                    JOptionPane.showMessageDialog(gp, "Teleport sukses menuju " + wp.getName());
                } else {
                    JOptionPane.showMessageDialog(gp, "Waypoint ini belum Anda temukan di World Map!");
                }
            } else if (sectionIndex == 3) {
                // PILIHAN SUBMENU
                limit = gp.storage.isEmpty() ? 1 : gp.storage.size() + 1;
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    selectIdx--;
                    if (selectIdx < 0) selectIdx = limit - 1; // Muter ke paling bawah
                } 
                else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    selectIdx++;
                    if (selectIdx >= limit) selectIdx = 0; // Muter ke paling atas
                }

                // EKSEKUSI TOMBOL ENTER / SPASI
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    if (selectIdx == gp.storage.size()) {
                        sectionIndex = 0; // Balik ke menu utama
                        selectIdx = 3;    // Sorot opsi nomor 4 (Koleksi Monster) di main menu biar rapi
                        return;           // Keluar fungsi, jangan eksekusi kode di bawah
                    }
                    if (gp.storage.isEmpty()) return;
                    // Proteksi darurat jika selectIdx keluar batas setelah pelepasan list
                    if (selectIdx >= gp.storage.size()) {
                        selectIdx = gp.storage.size() - 1;
                    }
                    if (selectIdx < 0) selectIdx = 0;

                    Monsters pokemonTerpilih = gp.storage.get(selectIdx);

                    // =============================================================
                    // LOGIKA TOGGLE EQUIP / UN-EQUIP EVOMON
                    // =============================================================
                    if (gp.team.contains(pokemonTerpilih)) {
                        // 1. Logika UN-EQUIP (Jika sudah ada di tim, maka dilepas)
                        if (gp.team.size() <= 1) {
                            JOptionPane.showMessageDialog(gp, "❌ Minimal harus ada 1 Evomon di dalam tim aktifmu!");
                        } else {
                            gp.team.remove(pokemonTerpilih);
                            JOptionPane.showMessageDialog(gp, "💼 " + pokemonTerpilih.getName() + " dilepas dari Tim Utama.");
                        }
                    } else {
                        // 2. Logika EQUIP (Jika belum ada di tim, maka dipasang)
                        if (gp.team.size() >= 6) { // Batasi tim maksimal 6 Evomon aktif
                            JOptionPane.showMessageDialog(gp, "❌ Tim penuh! Lepas Evomon lain terlebih dahulu (Maks 6).");
                        } else {
                            gp.team.add(pokemonTerpilih);
                            JOptionPane.showMessageDialog(gp, "⚔️ " + pokemonTerpilih.getName() + " berhasil dipasang ke Tim Utama!");
                        }
                    }
                }
            } else if (sectionIndex == 4){
                if (selectIdx == 0) beliItem("Potion", "POTION", 20);
                else if (selectIdx == 1) beliItem("Super Potion", "SUPER_POTION", 50);
                else if (selectIdx == 2) beliItem("Revive", "REVIVE_ITEM", 100);
                else if (selectIdx == 3) {sectionIndex = 0; selectIdx = 0;}
            } 
        }
    }

    private void saveGame() {
    File f = new File("assets/saves/save_game.dat");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            // 1. Status Dasar Player
            bw.write(gp.playerX + "\n");
            bw.write(gp.playerY + "\n");
            bw.write(gp.gold + "\n");
            // 2. Data Inventory
            bw.write(gp.inventory.size() + "\n");
            for (Items it : gp.inventory) {
                bw.write(it.getName() + "," + it.getType() + "," + it.getQty() + "\n");
            }
            // 3. Data Waypoint
            bw.write(gp.waypoints.size() + "\n");
            for (Waypoints wp : gp.waypoints) {
                bw.write(wp.getName() + "," + wp.isDiscovered() + "\n");
            }
            // 4. Data Koleksi Monster Gudang (Storage)
            bw.write(gp.storage.size() + "\n");
            for (Monsters m : gp.storage) {
                bw.write(m.getName() + "," + m.getLevel() + "," + m.getHp() + "," + m.getMaxHp() +  "," +  m.getAttack() + "," + m.getDefense() + "," + m.getSpeed() +  "," + m.getExp() + ","  + m.getMaxExp() + "\n");
            }
            // 5. Data Team Aktif
            bw.write(gp.team.size() + "\n");
            for (Monsters m : gp.team) {
                bw.write(gp.storage.indexOf(m) + "\n");
            }
            JOptionPane.showMessageDialog(gp, "💾 Semua data permainan berhasil disimpan!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gp, "❌ Gagal menyimpan game.");
        }
    }

    @SuppressWarnings("unchecked")
        private void loadGame() {
        File f = new File("assets/saves/save_game.dat");
        if (!f.exists()) {
            JOptionPane.showMessageDialog(gp, "❌ File save-an tidak ditemukan!");
            return;
        }try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            // 1. Status Dasar Player
            gp.playerX = Integer.parseInt(br.readLine());
            gp.playerY = Integer.parseInt(br.readLine());
            gp.gold = Integer.parseInt(br.readLine());
            // 2. Data Inventory
            gp.inventory.clear();
            int totalInventory = Integer.parseInt(br.readLine());
            for (int i = 0; i < totalInventory; i++) {
                String[] dataItem = br.readLine().split(",");
                String type = dataItem[1];
                int qty = Integer.parseInt(dataItem[2]);

                switch(type){
                    case "POTION":
                        gp.inventory.add(new Potion(qty));
                        break;

                    case "SUPER_POTION":
                        gp.inventory.add(new SuperPotion(qty));
                        break;

                    case "REVIVE_ITEM":
                        gp.inventory.add(new ReviveItem(qty));
                        break;
                }
                
            }
            // 3. Data Waypoint
            int totalWaypoints = Integer.parseInt(br.readLine());
            for (int i = 0; i < totalWaypoints; i++) {
                String[] dataWP = br.readLine().split(",");
                if (i < gp.waypoints.size()) {
                    gp.waypoints.get(i).setDiscovered(Boolean.parseBoolean(dataWP[1]));
                }
            }
            // 4. Data Koleksi Monster Gudang (Storage)
            gp.storage.clear();
            int totalStorage = Integer.parseInt(br.readLine());
            for (int i = 0; i < totalStorage; i++) {
                String[] dataMon = br.readLine().split(",");
                String name = dataMon[0];
                int level = Integer.parseInt(dataMon[1]);
                int hp = Integer.parseInt(dataMon[2]);
                int maxHp = Integer.parseInt(dataMon[3]);
                int attack = Integer.parseInt(dataMon[4]);
                int defense = Integer.parseInt(dataMon[5]);
                int speed = Integer.parseInt(dataMon[6]);
                int exp = Integer.parseInt(dataMon[7]);
                int maxExp = Integer.parseInt(dataMon[8]);
                Monsters m = AssetGenerator.getMonsterByName(name);
                if (m != null) {
                    m.setLevel(level);
                    m.setHp(hp);
                    m.setMaxHp(maxHp);
                    m.setAttack(attack);
                    m.setDefense(defense);
                    m.setSpeed(speed);
                    m.setExp(exp);
                    m.setMaxExp(maxExp);
                    gp.storage.add(m);
                }
            }
            // 5. Data Team Aktif
            gp.team.clear();
            int totalTeam = Integer.parseInt(br.readLine());
            for (int i = 0; i < totalTeam; i++) {
                int indeksStorage = Integer.parseInt(br.readLine());
                if (indeksStorage >= 0 && indeksStorage < gp.storage.size()) {
                    gp.team.add(gp.storage.get(indeksStorage));
                }
            }
            sectionIndex = 0;
            selectIdx = 0;
            gp.currentState = GameState.World; 
            JOptionPane.showMessageDialog(gp, "📂 Berhasil memuat data permainan dari save_game.dat!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gp, "❌ File save_game.dat lama tidak cocok atau rusak. Silakan main dan Save ulang!");
        }
    }

    private void beliItem(String nama, String type, int price){
        if (gp.gold < price){
            JOptionPane.showMessageDialog(gp, "Gold kamu tidak cukup untuk membeli " + nama);
            return;
        }
        gp.gold -= price;
        boolean ditemukan = false;
        
        // Sederhanakan string type target belanjaan untuk pengecekan (tanpa spasi, huruf kecil semua)
        String typeTarget = type.trim().replace(" ", "").toLowerCase();
        
        for(Items i : gp.inventory){
            // Sederhanakan juga string type item yang ada di inventory saat ini
            String typeDiTas = i.getType().trim().replace(" ", "").toLowerCase();
            String namaDiTas = i.getName().trim().replace(" ", "").toLowerCase();
            String namaTarget = nama.trim().replace(" ", "").toLowerCase();
            
            // Cek kesamaan tipe ATAU kesamaan nama secara mutlak tanpa peduli spasi & case
            if (typeDiTas.equals(typeTarget) || namaDiTas.equals(namaTarget)){
                i.setQty(i.getQty() + 1);
                ditemukan = true;
                break;
            }
        }
        
        // Baru dipasang pengaman: Slot baru HANYA dibuat jika benar-benar tidak ditemukan
        if (!ditemukan) {
            switch(type) {
                case "POTION":
                    gp.inventory.add(new Potion(1));
                    break;

                case "SUPER_POTION":
                    gp.inventory.add(new SuperPotion(1));
                    break;

                case "REVIVE":
                    gp.inventory.add(new ReviveItem(1));
                    break;
            }
        }
        
        JOptionPane.showMessageDialog(gp, "Berhasil membeli 1x " + nama + "!");
    }

    private void doGacha(int amount) {
        int cost = amount * 50;
        if(gp.gold < cost){
            JOptionPane.showMessageDialog(gp, "Gold tidak cukup! Anda butuh " + cost + " Gold.");
            return;
        }
        gp.gold -= cost;
        Random r = new Random();
        StringBuilder hasil = new StringBuilder("🎉 Hasil Gacha (" + amount + "x Pull):\n");

        for(int i = 0; i < amount; i++) {
            Rarity targetRarity;
            // HARD PITY
            if(gp.legendaryPity >= 29){
                targetRarity = Rarity.Legendary;
                gp.legendaryPity = 0;

            }
            else{
                double roll = r.nextDouble();
                targetRarity = Rarity.Common;
                if(roll < 0.02){
                    targetRarity = Rarity.Legendary;
                    gp.legendaryPity = 0;
                }
                else{
                    gp.legendaryPity++;
                    if(roll < 0.10)
                        targetRarity = Rarity.Epic;
                    else if(roll < 0.30)
                        targetRarity = Rarity.Rare;
                }

            }

            ArrayList<Monsters> pools = new ArrayList<>();
            for(Monsters m : AssetGenerator.allMonsters) {
                if(m.getRarity() == targetRarity) pools.add(m);
            }
            if(pools.isEmpty()) pools.addAll(AssetGenerator.allMonsters);

            Monsters rolled = pools.get(r.nextInt(pools.size())).cloneMonster();
            Monsters duplicate = null;
            // PAKSA MASUK KE STORAGE YANG DIBACA OLEH DRAW()
            for (Monsters m : gp.storage) {
                if (m.getName().equals(rolled.getName())) {
                    duplicate = m;
                    break;
                }
            }

            if (duplicate != null) {
                int expBonus = 30;
                if (duplicate.getRarity() == Rarity.Rare)
                    expBonus = 60;
                else if (duplicate.getRarity() == Rarity.Epic)
                    expBonus = 100;
                else if (duplicate.getRarity() == Rarity.Legendary)
                    expBonus = 200;

                duplicate.gainExp(expBonus, new ArrayList<>());

                hasil.append("- ")
                    .append(rolled.getName())
                    .append(" DUPLICATE → +")
                    .append(expBonus)
                    .append(" EXP\n");

            } else {

                gp.storage.add(rolled);

                if (gp.team.size() < 6) {
                    gp.team.add(rolled);
                }

                hasil.append("- ")
                    .append(rolled.getName())
                    .append(" [")
                    .append(rolled.getRarity())
                    .append("]\n");
            }
        }
        
        JOptionPane.showMessageDialog(gp, hasil.toString());
    }

    private void drawStroke(Graphics2D g2, String teks, int x, int y, Color warnaTeks) {
        // 1. Gambar outline/border hitam dengan menggeser koordinat ke 8 arah secara pas
        g2.setColor(Color.BLACK);
        g2.drawString(teks, x - 1, y - 1); // Atas-Kiri
        g2.drawString(teks, x + 1, y - 1); // Atas-Kanan
        g2.drawString(teks, x - 1, y + 1); // Bawah-Kiri
        g2.drawString(teks, x + 1, y + 1); // Bawah-Kanan
        g2.drawString(teks, x, y - 1);     // Atas
        g2.drawString(teks, x, y + 1);     // Bawah
        g2.drawString(teks, x - 1, y);     // Kiri
        g2.drawString(teks, x + 1, y);     // Kanan

        // 2. Gambar teks utama tepat di atas stroke hitam tadi
        g2.setColor(warnaTeks);
        g2.drawString(teks, x, y);
    }
}
