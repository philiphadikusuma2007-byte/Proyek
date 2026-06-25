import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Battle {
    GamePanel gp;
    Monsters playerActive, enemy;
    ArrayList<String> logs = new ArrayList<>();
    boolean selesai = false;
    int earnedGold = 0;
    int earnedXP = 0;
    int menuIndex = 0, subMenu = 0; // 0: Main, 1: Skill, 2: Item
    boolean isPlayerTurn = true;

    public Battle(GamePanel gp) { this.gp = gp; }

    public void startBattle() {
        selesai = false;
        earnedGold = 0;
        earnedXP = 0;
        logs.clear();
        menuIndex = 0; 
        subMenu = 0;
        // Ambil monster pertama tim player yang masih hidup
        playerActive = null;
        for(Monsters m : gp.team) { 
            if(m.hp > 0) { 
                playerActive = m; 
                break; 
            } 
        }
        if(playerActive == null) { 
            playerActive = gp.team.get(0); // Ambil monster pertama
            playerActive.hp = 1;          // Berikan pertolongan pertama 1 HP agar game TIDAK CRASH/HITAM
            logs.add("⚠️ " + playerActive.name + " bangkit kembali dengan 1 HP untuk bertahan hidup!");
        }

        // Ambil random monster dari database aset
        Random r = new Random();
        enemy = AssetGenerator.allMonsters.get(r.nextInt(AssetGenerator.allMonsters.size())).cloneMonster();
        enemy.level = playerActive.level + r.nextInt(3) - 1; // Level scaling dinamis
        if(enemy.level < 1) enemy.level = 1;

        logs.add("⚔️ Monster Liar " + enemy.name + " (Lv." + enemy.level + ") Muncul!");
        determineTurnOrder();
        gp.currentState = GameState.Battle;
    }

    private void determineTurnOrder() {
        isPlayerTurn = playerActive.speed >= enemy.speed;
        if(!isPlayerTurn) executeEnemyTurn();
    }

    public void update() {}

    public void draw(Graphics2D g2) {
        // Background temp arena
        g2.setColor(new Color(30, 40, 60));
        g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);

        // Render Showcase Musuh (Kanan Atas)
        g2.drawImage(AssetGenerator.getMonstersImage(enemy.name), 500, 80, 160, 160, null);
        drawStatusBar(g2, enemy, 500, 40);

        // Render Player Monster (Kiri Bawah)
        g2.drawImage(AssetGenerator.getMonstersImage(playerActive.name), 100, 240, 160, 160, null);
        drawStatusBar(g2, playerActive, 100, 200);

        // Render Kotak Dialog Log & Menu Kendali
        g2.setColor(Color.BLACK);
        g2.fillRect(10, 420, 780, 140);
        g2.setColor(Color.WHITE);
        g2.drawRect(10, 420, 780, 140);

        // Render Logs Terkini (Maksimal 3 Baris)
        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        int logY = 445;
        int startIdx = Math.max(0, logs.size() - 3);
        for(int i=startIdx; i<logs.size(); i++) {
            g2.drawString(logs.get(i), 30, logY); logY += 20;
        }

        // Render Menu Navigasi Sisi Kanan Kotak Dialog
        g2.drawRect(520, 425, 260, 130);
        if(subMenu == 0) {
            String[] options = {"[1] Attack", "[2] Skill", "[3] Item", "[4] Run"};
            for(int i=0; i<4; i++) {
                g2.drawString((menuIndex == i ? "> " : "  ") + options[i], 540, 455 + (i * 22));
            }
        } else if(subMenu == 1) { // Menu Skill
            for(int i=0; i<playerActive.skills.size(); i++) {
                g2.drawString((menuIndex == i ? "> " : "  ") + playerActive.skills.get(i).name, 540, 455 + (i * 22));
            }
        } else if(subMenu == 2) { // Menu Bag/Item
            for(int i=0; i<gp.inventory.size(); i++) {
                Items it = gp.inventory.get(i);
                g2.drawString((menuIndex == i ? "> " : "  ") + it.name + " x" + it.qty, 540, 455 + (i * 22));
            }
        }
        // ---- POP-UP RINGKASAN SETELAH BATTLE SELESAI ----
        if (selesai) {
            // Gambar kotak background semi-transparan di tengah layar
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fillRect(150, 100, 500, 350);
            g2.setColor(Color.YELLOW);
            g2.drawRect(150, 100, 500, 350);

            g2.setFont(new Font("SansSerif", Font.BOLD, 26));
            if (enemy.hp <= 0) {
                g2.drawString("⚔️ VICTORY ⚔️", 310, 150);
                
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(Color.WHITE);
                g2.drawString("Monster dikalahkan: " + enemy.name, 180, 210);
                g2.drawString("⭐ EXP Didapat      : +" + earnedXP + " EXP", 180, 250);
                g2.drawString("💰 Gold Diperoleh  : +" + earnedGold + " Gold", 180, 290);
                g2.drawString("📈 Status " + playerActive.name + "   : Lv." + playerActive.level + " (HP: " + playerActive.hp + "/" + playerActive.maxHp + ")", 180, 330);
            } else {
                g2.setColor(Color.RED);
                g2.drawString("💀 DEFEAT 💀", 320, 150);
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(Color.WHITE);
                g2.drawString("Semua monster aktifmu pingsan!", 210, 240);
            }

            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            g2.setColor(Color.GRAY);
            g2.drawString("[ Tekan ENTER / SPASI untuk kembali ke Map ]", 250, 410);
        }
    }

    private void drawStatusBar(Graphics2D g2, Monsters m, int x, int y) {
        g2.setColor(Color.WHITE);
        g2.drawString(m.name + " Lv." + m.level + " [" + m.rarity.name() + "]", x, y);
        g2.setColor(Color.RED);
        g2.fillRect(x, y+8, 150, 10);
        g2.setColor(Color.GREEN);
        double hpRatio = (double)m.hp / m.maxHp;
        g2.fillRect(x, y+8, (int)(150 * hpRatio), 10);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y+8, 150, 10);
        g2.drawString(m.hp + "/" + m.maxHp + " STATUS: " + m.currentStatus.name(), x, y+32);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (selesai){
            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                selesai = false;
                earnedGold = 0;
                earnedXP = 0;
                gp.currentState = GameState.World;
            }
            return;
        }
        int limit = (subMenu == 0) ? 4 : (subMenu == 1 ? playerActive.skills.size() : gp.inventory.size());
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) menuIndex = (menuIndex - 1 + limit) % limit;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) menuIndex = (menuIndex + 1) % limit;
        if(code == KeyEvent.VK_ESCAPE && subMenu != 0) { subMenu = 0; menuIndex = 0; return; }
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if(subMenu == 0) {
                if(menuIndex == 0) executeBasicAttack();
                else if(menuIndex == 1) {
                    subMenu = 1; 
                    menuIndex = 0; 
                }
                else if(menuIndex == 2) { 
                    subMenu = 2; 
                    menuIndex = 0; 
                }
                else if(menuIndex == 3) { 
                    logs.add("🏃 Berhasil kabur dari pertarungan!"); 
                    selesai = true;
                }
            } else if(subMenu == 1) {
                executeSkillAttack(playerActive.skills.get(menuIndex));
            } else if(subMenu == 2) {
                useBattleItem(gp.inventory.get(menuIndex));
            }
        }
    }

    private void executeBasicAttack() {
        int damage = Math.max(1, playerActive.attack - enemy.defense/2);
        enemy.hp -= damage; if(enemy.hp < 0) enemy.hp = 0;
        logs.add("⚔️ " + playerActive.name + " menyerang " + enemy.name + " sebesar " + damage + " DMG!");
        checkBattleStatus();
        if(enemy.hp > 0) executeEnemyTurn();
    }

    private void executeSkillAttack(Skills sk) {
        int baseDmg = Math.max(1, (playerActive.attack + sk.power) - enemy.defense/2);
        enemy.hp -= baseDmg; if(enemy.hp < 0) enemy.hp = 0;
        logs.add("🔥 " + playerActive.name + " melancarkan " + sk.name + "! " + baseDmg + " DMG terkoyak.");
        
        if(new Random().nextInt(100) < sk.effectChance) {
            enemy.currentStatus = sk.effect; enemy.statusDuration = 3;
            logs.add("💥 Efek Status Terinfeksi: " + sk.effect.name());
        }
        subMenu = 0; menuIndex = 0;
        checkBattleStatus();
        if(enemy.hp > 0) executeEnemyTurn();
    }

    private void useBattleItem(Items it) {
        if(it.qty <= 0) { logs.add("Item habis!"); return; }
        if(it.type.equals("POTION")) { playerActive.hp = Math.min(playerActive.maxHp, playerActive.hp + 50); it.qty--; }
        if(it.type.equals("SUPER_POTION")) { playerActive.hp = Math.min(playerActive.maxHp, playerActive.hp + 150); it.qty--; }
        if(it.type.equals("REVIVE")) { if(playerActive.hp <= 0) { playerActive.hp = playerActive.maxHp/2; it.qty--; } }
        
        logs.add("🎒 Menggunakan " + it.name + " pada " + playerActive.name);
        subMenu = 0; menuIndex = 0;
        executeEnemyTurn();
    }

    private void executeEnemyTurn() {
        if(enemy.hp <= 0) return;
        // Efek Status Check Musuh
        if(enemy.currentStatus == StatusEffect.Stun || enemy.currentStatus == StatusEffect.Freeze) {
            logs.add("❄️ " + enemy.name + " tidak bisa bergerak karena status " + enemy.currentStatus.name());
            enemy.statusDuration--; if(enemy.statusDuration <= 0) enemy.currentStatus = StatusEffect.None;
            return;
        }

        int edmg = Math.max(1, enemy.attack - playerActive.defense/2);
        playerActive.hp -= edmg; if(playerActive.hp < 0) playerActive.hp = 0;
        logs.add("💥 " + enemy.name + " membalas menyerang, memberi " + edmg + " DMG!");

        checkBattleStatus();
    }

    private void checkBattleStatus() {
        if(enemy.hp <= 0) {
            selesai = true;
            earnedXP = 50 + (enemy.level * 10);
            earnedGold = 30 + new Random().nextInt(41) + (enemy.level * 5);
            playerActive.gainExp(earnedXP, logs);
            gp.gold += earnedGold;
            logs.add("🏆 Musuh " + enemy.name + " Kalah!");
            playerActive.gainExp(50 + (enemy.level * 10), logs);
            Random r = new Random();
            int goldDrop = 30 + r.nextInt(41) + (enemy.level * 5); // Dapat antara 30-70 Gold + Bonus Level
            gp.gold += goldDrop; // Tambahkan ke data pemain
            logs.add("💰 Anda mendapatkan " + goldDrop + " Gold!"); // Tampilkan di log pertarungan
        } else if(playerActive.hp <= 0) {
            boolean hidup = false;
            for(Monsters m : gp.team) {
                if(m.hp > 0) {
                    hidup = true;
                    break;
                }
            }
            if (!hidup) {
                selesai = true;
                earnedXP = 0;
                earnedGold = 0;
                logs.add("💀 Semua monster aktifmu pingsan!");
            } else {
                logs.add("💀 " + playerActive.name + " pingsan! Pilih monstermu yang lain!");
                for (Monsters m : gp.team){
                    if(m.hp > 0){
                        playerActive = m;
                        break;
                    }
                }
            }
        }
    }

    private void endBattleLater() {
        Timer timer = new Timer(2000, e -> gp.currentState = GameState.World);
        timer.setRepeats(false);
        timer.start();
    }
}
