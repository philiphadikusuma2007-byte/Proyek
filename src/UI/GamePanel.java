package UI;
import java.util.ArrayList;
import javax.swing.JPanel;

import Model.*;
import Model.ItemsFIle.*;
import Util.AssetGenerator;
import World.*;
import Game.*;

import java.awt.event.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running = false;

    //state
    public GameState currentState = GameState.Title; // <-- starts at Title now
    public WorldManager worldManager;
    public Battle battleEngine;
    public Gacha gachaEngine;
    public MenuManager menuManager;
    public TitleScreen titleScreen; // <-- added
    
    // Player Data
    public int gold = 100;
    public int playerX = 51 * 32, playerY = 26 * 32;
    public int playerDirection = 0;
    public boolean isMoving = false;
    public ArrayList<Monsters> team = new ArrayList<>();
    public ArrayList<Monsters> storage = new ArrayList<>();
    public ArrayList<Items> inventory = new ArrayList<>();
    public ArrayList<Waypoints> waypoints = new ArrayList<>();
    public int legendaryPity = 0;
    
    public GamePanel() {
        setPreferredSize(new Dimension(Game.screenWidth, Game.screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        titleScreen = new TitleScreen(this); // <-- added
        initGameData();
    }
    
    private void initGameData() {
        worldManager = new WorldManager(this);
        battleEngine = new Battle(this);
        gachaEngine = new Gacha(this);
        menuManager = new MenuManager(this);
        Monsters starter = AssetGenerator.allMonsters.get(0).cloneMonster();

        // Berikan starter monster
        team.add(starter);
        storage.add(starter);
        
        // Item bawaan
        inventory.add(new Potion(5));
        inventory.add(new SuperPotion(2));
        inventory.add(new ReviveItem(1));

        // Waypoints resmi map
        waypoints.add(new Waypoints("Hutan", 30, 19));
        waypoints.add(new Waypoints("Danau", 10, 25));
        waypoints.add(new Waypoints("Desa", 54, 30));
    }
    
    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000 / 60.0; // 60 FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (currentState == GameState.Title) titleScreen.update(); // <-- added
        else if (currentState == GameState.World) worldManager.update();
        else if (currentState == GameState.Battle) battleEngine.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if (currentState == GameState.Title) titleScreen.draw(g2); // <-- added
        else if (currentState == GameState.World) worldManager.draw(g2);
        else if (currentState == GameState.Alert) worldManager.draw(g2);
        else if (currentState == GameState.Battle) {
            battleEngine.draw(g2);
            if (battleEngine != null){
                battleEngine.draw(g2);
            } else{
                currentState = GameState.World;
            }
        }
        else if (currentState == GameState.Gacha) gachaEngine.draw(g2);
        else if (currentState == GameState.Menu) menuManager.draw(g2);
        else if(currentState == GameState.Minimap) {
            worldManager.draw(g2);
            worldManager.drawMinimap(g2);
        }
        
        g2.dispose();
    }

    // Key Listener delegation
    @Override public void keyPressed(KeyEvent e) {
        if(currentState == GameState.Title) titleScreen.keyPressed(e); // <-- added
        else if(currentState == GameState.World) worldManager.keyPressed(e);
        else if(currentState == GameState.Alert) worldManager.keyPressed(e);
        else if(currentState == GameState.Battle) battleEngine.keyPressed(e);
        else if(currentState == GameState.Gacha) gachaEngine.keyPressed(e);
        else if(currentState == GameState.Menu) menuManager.keyPressed(e);
        else if(currentState == GameState.Minimap) worldManager.keyPressed(e);
    }
    @Override public void keyReleased(KeyEvent e) { if(currentState == GameState.World) worldManager.keyReleased(e); }
    @Override public void keyTyped(KeyEvent e) {}
}
