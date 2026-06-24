import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Game game = new Game();
            game.setVisible(true); 
        });
    }
}