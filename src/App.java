import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ajajaaj");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();       
        frame.add(gamePanel);
        frame.setVisible(true);
    }
}