package Model;
import java.awt.Color;

public enum Rarity{Common(0.70, Color.GRAY), Rare(0.20, Color.BLUE), Epic(0.08, Color.MAGENTA), Legendary(0.02, Color.ORANGE);
    double rate;
    Color color;
    Rarity(double rate, Color color){
        this.rate = rate;
        this.color = color;
    }

    public double getRate() {
        return rate;
    }

    public Color getColor() {
        return color;
    }
}