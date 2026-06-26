package Game;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import UI.GamePanel;
import UI.Audio.BGM;
import Util.AssetGenerator;

public class Game extends JFrame{
    public static final int tileSize = 32;
    public static final int screenWidth = 800;
    public static final int screenHeight = 600;

    public Game(){
        setTitle("Monster Collection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        AssetGenerator.generateAssets();
        GamePanel panel = new GamePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
        panel.startGame();
        BGM.playBGM("assets/bgm/sonic-hedgehog.wav");

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Game game = new Game();
            game.setVisible(true); 
        });
    }
}