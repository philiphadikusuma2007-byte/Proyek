package Model.StatusEffectFile;
import Model.*;

public class DeffBuff extends StatusEffect{

    public DeffBuff(){
        super("Defense Buff",3);
    }

    @Override
    public void applyEffect(Monsters monster){
        monster.setDefense(
            monster.getDefense()+5
        );
    }
}