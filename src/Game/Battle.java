package Game;
import Model.*;
import Model.BattleActions.AttackAction;
import Model.BattleActions.BattleAction;
import Model.BattleActions.ItemAction;
import Model.BattleActions.RunAction;
import Model.BattleActions.SkillAction;
import Model.StatusEffectFile.*;
import UI.Audio.*;
import UI.GamePanel;
import Util.AssetGenerator;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Battle {
    GamePanel gp;
    Monsters playerActive, enemy;
    ArrayList<String> logs = new ArrayList<>();
    boolean selesai = false;
    int earnedGold = 0;
    int earnedXP = 0;
    int menuIndex = 0, subMenu = 0; // 0: Main, 1: Skill, 2: Item
    boolean isPlayerTurn = true;
    boolean escaped = false;
    private BufferedImage battleBackground;

    public Battle(GamePanel gp) { 
        this.gp = gp;
        try {
            battleBackground = ImageIO.read(new File("assets/background/background.png"));
        } catch (IOException e) {
            System.err.println("⚠️ Gagal memuat background battle: " + e.getMessage());
            battleBackground = null;
        }
    }

    public void startBattle() {
        try {
            
        boolean hidup = false;
        for(Monsters m : gp.team) {
            if(m.getHp() > 0) {
                hidup = true;
                break;
            }
        }
        if (!hidup){
            JOptionPane.showMessageDialog(gp, "❌ Semua Evomon kamu mati, pulihkan di Evomon Centre terdekat!", "Peringatan Medis", JOptionPane.WARNING_MESSAGE);
            gp.currentState = GameState.World;
            return;
        }
        selesai = false;
        escaped = false;
        earnedGold = 0;
        earnedXP = 0;
        logs.clear();
        menuIndex = 0; 
        subMenu = 0;
        BGM.setVolume(-40f);                                         
        Sound.playSound("assets/sounds/enemy-hit.wav");
        // Ambil monster pertama tim player yang masih hidup
        playerActive = null;
        for(Monsters m : gp.team) { 
            if(m.getHp() > 0) { 
                playerActive = m; 
                break; 
            } 
        }
        
        if(playerActive == null && !gp.team.isEmpty()) { 
            playerActive = gp.team.get(0); 
        }

        // Ambil random monster dari database aset
        Random r = new Random();
        enemy = AssetGenerator.allMonsters.get(r.nextInt(AssetGenerator.allMonsters.size())).cloneMonster();
        enemy.setLevel(playerActive.getLevel() + r.nextInt(3) - 1); // Level scaling dinamis
        if(enemy.getLevel() < 1) enemy.setLevel(1);

        logs.add("⚔️ Evomon Liar " + enemy.getName() + " (Lv." + enemy.getName() + ") Muncul!");
        determineTurnOrder();
        gp.currentState = GameState.Battle;
        } catch (Exception e) {
            e.printStackTrace(); // Tetap cetak di terminal VS Code
            JOptionPane.showMessageDialog(gp, "🚨 GAME CRASH DI STARTBATTLE!\nJenis Error: " + e.toString() + "\n\nCek terminal VS Code untuk melihat baris nomor berapa yang rusak.", "Sistem Error", JOptionPane.ERROR_MESSAGE);
            gp.currentState = GameState.World;
        }
    }

    private void determineTurnOrder() {
        isPlayerTurn = playerActive.getSpeed() >= enemy.getSpeed();
        if(!isPlayerTurn) executeEnemyTurn();
    }

    public void update() {}

    public void draw(Graphics2D g2) {
        // Background temp arena
        if (battleBackground != null) {
            g2.drawImage(battleBackground, 0, 0, Game.screenWidth, Game.screenHeight, null);
        } else {
            // Fallback warna jika gambar tidak ditemukan
            g2.setColor(new Color(30, 40, 60));
            g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
        }
        // Render Showcase Musuh (Kanan Atas)
        g2.drawImage(AssetGenerator.getMonstersImage(enemy.getName()), 500, 80, 160, 160, null);
        drawStatusBar(g2, enemy, 500, 40);

        // Render Player Monster (Kiri Bawah)
        g2.drawImage(AssetGenerator.getMonstersImage(playerActive.getName()), 100, 240, 160, 160, null);
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
            for(int i=0; i<playerActive.getSkills().size(); i++) {
                g2.drawString((menuIndex == i ? "> " : "  ") + playerActive.getSkills().get(i).getName(), 540, 455 + (i * 22));
            }
        } else if(subMenu == 2) { // Menu Bag/Item
            for(int i=0; i<gp.inventory.size(); i++) {
                Items it = gp.inventory.get(i);
                g2.drawString((menuIndex == i ? "> " : "  ") + it.getName() + " x" + it.getQty(), 540, 455 + (i * 22));
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
            if (escaped) {

                g2.setColor(Color.CYAN);
                g2.drawString("🏃 ESCAPED 🏃", 300, 150);

                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(Color.WHITE);
                g2.drawString("Berhasil kabur dari pertarungan!", 200, 240);

            }
            else if (enemy.getHp() <= 0) {
                g2.drawString("⚔️ VICTORY ⚔️", 310, 150);
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(Color.WHITE);
                g2.drawString("Evomon dikalahkan: " + enemy.getName(), 180, 210);
                g2.drawString("⭐ EXP Didapat      : +" + earnedXP + " EXP", 180, 250);
                g2.drawString("💰 Gold Diperoleh  : +" + earnedGold + " Gold", 180, 290);
                g2.drawString("📈 Status " + playerActive.getName() + "   : Lv." + playerActive.getLevel() + " (HP: " + playerActive.getHp() + "/" + playerActive.getMaxHp() + ")", 180, 330);
            } else {
                g2.setColor(Color.RED);
                g2.drawString("💀 DEFEAT 💀", 320, 150);
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                g2.setColor(Color.WHITE);
                g2.drawString("Semua Evomon aktifmu pingsan!", 210, 240);
            }
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
            g2.setColor(Color.GRAY);
            g2.drawString("[ Tekan ENTER / SPASI untuk kembali ke Map ]", 250, 410);
        }
    }

    private void drawStatusBar(Graphics2D g2, Monsters m, int x, int y) {
        g2.setColor(Color.WHITE);
        g2.drawString(m.getName() + " Lv." + m.getLevel() + " [" + m.getRarity().name() + "]", x, y);
        g2.setColor(Color.RED);
        g2.fillRect(x, y+8, 150, 10);
        g2.setColor(Color.GREEN);
        double hpRatio = (double)m.getHp() / m.getMaxHp();
        g2.fillRect(x, y+8, (int)(150 * hpRatio), 10);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y+8, 150, 10);
        g2.drawString(m.getHp() + "/" + m.getMaxHp() + " STATUS: " + m.getCurrentStatus().getName(), x, y+32);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (selesai){
            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                selesai = false;
                earnedGold = 0;
                earnedXP = 0;
                BGM.setVolume(-32f);
                gp.currentState = GameState.World;                  
            }
            return;
        }
        int limit = 1;
        if (subMenu == 0) limit = 4;
        else if (subMenu == 1) limit = playerActive.getSkills().size();
        else if (subMenu == 2) limit = gp.inventory.isEmpty() ? 1 : gp.inventory.size();
        else if (subMenu == 3) limit = gp.team.size();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) menuIndex = (menuIndex - 1 + limit) % limit;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) menuIndex = (menuIndex + 1) % limit;

        if(code == KeyEvent.VK_ESCAPE && subMenu != 0) { subMenu = 0; menuIndex = 0; return; }
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if(subMenu == 0) {
                BattleAction action = null;
                switch(menuIndex){
                    case 0:
                        action = new AttackAction();
                        break;
                    case 1:
                        action = new SkillAction();
                        break;
                    case 2:
                        action = new ItemAction();
                        break;
                    case 3:
                        action = new RunAction();
                        break;
                }

                if(action != null){
                    action.execute(this);
                }
            } else if(subMenu == 1) {
                executeSkillAttack(playerActive.getSkills().get(menuIndex));
            } else if(subMenu == 2) {
                useBattleItem(gp.inventory.get(menuIndex));
            } else if(subMenu == 3) {
                Monsters monsterDipilih = gp.team.get(menuIndex);
                if(monsterDipilih.getHp() <= 0) {
                    JOptionPane.showMessageDialog(gp, "❌ Evomon tersebut sudah pingsan! Pilih yang lain.");
                } else {
                    playerActive = monsterDipilih;
                    logs.add("⚔️ Maju, " + playerActive.getName() + "! Balaskan dendam temanmu!");
                    subMenu = 0;
                    menuIndex = 0;
                    executeEnemyTurn();
                }
            }
        }
    }

    public void executeBasicAttack() {
        int damage = Math.max(1, playerActive.getAttack() - enemy.getDefense()/2);
        enemy.setHp(enemy.getHp() - damage);
        if(enemy.getHp() < 0) enemy.setHp(0);
        logs.add("⚔️ " + playerActive.getName() + " menyerang " + enemy.getName() + " sebesar " + damage + " DMG!");
        checkBattleStatus();
        if(enemy.getHp() > 0) executeEnemyTurn();
    }

    public void executeSkillAttack(Skills skill) {
        int baseDmg = Math.max(1, (playerActive.getAttack() + skill.getPower()) - enemy.getDefense()/2);
        double multiplier = skill.getElement().getMultiplier(enemy.getElement());
        if(multiplier > 1.0){
            logs.add("🔥 It's Super Effective!");
        }
        else if(multiplier < 1.0){
            logs.add("😓 It's Not Very Effective...");
        }
        baseDmg = (int)(baseDmg * multiplier);
        enemy.takeDamage(baseDmg);
        logs.add("🔥 " + playerActive.getName() + " melancarkan " + skill.getName() + "! " + baseDmg + " DMG.");
        
        if(new Random().nextInt(100) < skill.getEffectChance()) {
            enemy.setCurrentStatus(skill.getEffect()); 
            enemy.setStatusDuration(3);
            logs.add("💥 Efek Status Terinfeksi: " + skill.getEffect());
        }
        subMenu = 0; menuIndex = 0;
        checkBattleStatus();
        if(enemy.getHp() > 0) executeEnemyTurn();
    }

    public void useBattleItem(Items it) {
        if(it.getQty() <= 0) { 
            logs.add("Item habis!"); 
            return; 
        }
        it.use(playerActive);
        logs.add("🎒 Menggunakan " + it.getName() + " pada " + playerActive.getName());
        subMenu = 0; 
        menuIndex = 0;
        executeEnemyTurn();
    }

    private void executeEnemyTurn() {
        if(enemy.getHp() <= 0) return;
        StatusEffect status = enemy.getCurrentStatus();
        status.applyEffect(enemy);
        // Efek Status Check Musuh
        if(!status.canMove()) {
            logs.add("❄️ " + enemy.getName() + " tidak bisa bergerak karena status " + enemy.getCurrentStatus());
            status.decreaseDuration();
            if(status.isFinished()){
                enemy.setCurrentStatus(new None());
            }
            checkBattleStatus();
            return;
        }

        int edmg = Math.max(1, enemy.getAttack() - playerActive.getDefense()/2);
        playerActive.takeDamage(edmg);
        logs.add("💥 " + enemy.getName() + " membalas menyerang, memberi " + edmg + " DMG!");
        checkBattleStatus();
    }

    private void checkBattleStatus() {
        if(enemy.getHp() <= 0) {
            selesai = true;
            earnedXP = 50 + (enemy.getLevel() * 10);
            earnedGold = 30 + new Random().nextInt(41) + (enemy.getLevel() * 5);
            playerActive.gainExp(earnedXP, logs);
            gp.gold += earnedGold;
            logs.add("🏆 Musuh " + enemy.getName() + " Kalah!");
            logs.add("💰 Anda mendapatkan " + earnedGold + " Gold!");
            endBattleLater();
        } else if(playerActive.getHp() <= 0) {
            boolean hidup = false;
            for(Monsters m : gp.team) {
                if(m.getHp() > 0) {
                    hidup = true;
                    break;
                }
            }
            if (!hidup) {
                selesai = true;
                earnedXP = 0;
                earnedGold = 0;
                Sound.playSound("assets/sounds/lose.wav");
                logs.add("💀 Semua Evomon aktifmu pingsan!");
            } else {
                logs.add("💀 " + playerActive.getName() + " pingsan! Pilih Evomonmu yang lain!");
                subMenu = 3;
                menuIndex = 0;
            }
        }
    }

    private void endBattleLater() {
        Sound.playSound("assets/sounds/victory.wav");
        Timer timer = new Timer(2000, e -> {
            BGM.setVolume(-32f);   // kembalikan volume
            gp.currentState = GameState.World;
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void openSkillMenu() {
        subMenu = 1;
        menuIndex = 0;
    }

    public void openItemMenu() {
        subMenu = 2;
        menuIndex = 0;
    }

    public void runBattle() {
        logs.add("🏃 Berhasil kabur dari pertarungan!");
        escaped = true;
        selesai = true;
    }

    public void mouseClicked(MouseEvent e){

    }
}
