package Model.ItemsFIle;
import Model.*;

public class SuperPotion extends Items{

    public SuperPotion(int qty){
        super("Super Potion","SUPER_POTION",qty);
    }

    @Override
    public void use(Monsters target){
        if(qty <= 0) return;
        target.setHp(
            Math.min(
                target.getMaxHp(),
                target.getHp()+150
            )
        );
        reduceQty();
    }

}