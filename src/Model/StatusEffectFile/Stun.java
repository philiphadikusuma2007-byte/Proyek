package Model.StatusEffectFile;
import Model.*;

public class Stun extends StatusEffect{

    public Stun(){
        super("Stun",1);
    }

    @Override
    public void applyEffect(Monsters monster){
        // Monster skip turn
    }

    @Override
    public boolean canMove(){
        return false;
    }
}