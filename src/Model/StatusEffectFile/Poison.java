package Model.StatusEffectFile;
import Model.*;

public class Poison extends StatusEffect{

    public Poison(){
        super("Poison",4);
    }

    @Override
    public void applyEffect(Monsters monster){
        int damage = monster.getMaxHp()/8;
        monster.takeDamage(damage);
    }
}