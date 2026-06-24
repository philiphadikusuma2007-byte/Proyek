import java.io.Serializable;

public class Items implements Serializable{
    String name;
    String type;
    int qty;
    public Items(String name, String type, int qty) { this.name = name; this.type = type; this.qty = qty; }
}
