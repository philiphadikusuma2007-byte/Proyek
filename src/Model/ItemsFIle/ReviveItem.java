package Model.ItemsFIle;
import Model.*;

public class ReviveItem extends Items{

    public ReviveItem(int qty){
        super("Revive", "REVIVE_ITEM", qty);
    }

    @Override
    public void use(Monsters target){
        if(qty <= 0) return;
        if(target.isDead()){
            target.setHp(target.getMaxHp()/2);
            reduceQty();
        }

    }

}