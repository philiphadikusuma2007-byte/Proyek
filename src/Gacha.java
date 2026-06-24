import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;

public class Gacha {
    GamePanel gp;
    ArrayList<Monsters> pullResults = new ArrayList<>();
    int showIndex = -1;

    public Gacha(GamePanel gp) { this.gp = gp; }

    public void doPull(int amount) {
        int cost = amount * 50;
        if(gp.gold < cost){
            JOptionPane.showMessageDialog(gp, "Gold tidak cukup! Anda butuh " + cost + " Gold untuk " + amount + "x Pull.");
            gp.currentState = GameState.Menu;
            return;
        }
        gp.gold -= cost;
        pullResults.clear();
        Random r = new Random();
        for(int i=0; i<amount; i++) {
            double roll = r.nextDouble();
            Rarity targetRarity = Rarity.Common;
            if(roll < 0.02) targetRarity = Rarity.Legendary;
            else if(roll < 0.10) targetRarity = Rarity.Epic;
            else if(roll < 0.30) targetRarity = Rarity.Rare;

            // Cari list monster dengan rarity tersebut
            ArrayList<Monsters> pools = new ArrayList<>();
            for(Monsters m : AssetGenerator.allMonsters) {
                if(m.rarity == targetRarity) pools.add(m);
            }
            Monsters rolled = pools.get(r.nextInt(pools.size())).cloneMonster();
            gp.team.add(rolled);
            pullResults.add(rolled);
        }
        showIndex = 0;
        gp.currentState = GameState.Gacha;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);

        if(showIndex >= 0 && showIndex < pullResults.size()) {
            Monsters m = pullResults.get(showIndex);
            g2.setColor(m.rarity.color);
            g2.setFont(new Font("SansSerif", Font.BOLD, 28));
            g2.drawString("✨ PULL GET! ✨", 300, 80);

            g2.drawImage(AssetGenerator.getMonstersImage(m.name), 300, 150, 200, 200, null);
            g2.setFont(new Font("Monospaced", Font.BOLD, 22));
            g2.drawString(m.name, 320, 400);
            g2.setColor(Color.WHITE);
            g2.drawString("Rarity: " + m.rarity.name(), 320, 430);
            g2.drawString("Element: " + m.element.name(), 320, 460);
            
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2.drawString("[Tekan ENTER / SPASI untuk lanjut]", 280, 530);
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            showIndex++;
            if(showIndex >= pullResults.size()) {
                gp.currentState = GameState.Menu; // Kembali ke menu utama
            }
        }
    }
}
