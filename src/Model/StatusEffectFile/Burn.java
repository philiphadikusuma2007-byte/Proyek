package Model.StatusEffectFile;
import Model.*;

public class Burn extends StatusEffect{

    public Burn(){
        super("Burn",3);
    }

    @Override
    public void applyEffect(Monsters monster){
        int damage = monster.getMaxHp()/10;
        monster.takeDamage(damage);
    }
}