package World;
import java.io.Serializable;

public class Waypoints implements Serializable{
    String name; int x, y; boolean discovered = false;
    public Waypoints(String name, int x, int y) { 
        this.name = name; 
        this.x = x; 
        this.y = y; 
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public String getName() {
        return name;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
