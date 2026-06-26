package Model;
import java.io.Serializable;

public abstract class Items implements Serializable{
    protected String name;
    protected String type;
    protected int qty;

    public Items(String name, String type, int qty) { 
        this.name = name; 
        this.type = type; 
        this.qty = qty; 
    }
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void addQty(int amount){
        qty += amount;
    }

    public void reduceQty(){
        if(qty > 0)
            qty--;
    }

    public abstract void use(Monsters target);

}
