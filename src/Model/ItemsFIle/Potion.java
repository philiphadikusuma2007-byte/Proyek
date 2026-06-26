package Model.ItemsFIle;
import Model.*;

public class Potion extends Items{

    public Potion(int qty){
        super("Potion","POTION", qty);
    }

    @Override
    public void use(Monsters target){
        if(qty <= 0) return;
        target.setHp(
            Math.min(
                target.getMaxHp(),
                target.getHp()+50
            )
        );
        reduceQty();
    }

}