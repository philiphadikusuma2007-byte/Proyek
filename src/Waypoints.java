import java.io.Serializable;

public class Waypoints implements Serializable{
    String name; int x, y; boolean discovered = false;
    public Waypoints(String name, int x, int y) { this.name = name; this.x = x; this.y = y; }
}
