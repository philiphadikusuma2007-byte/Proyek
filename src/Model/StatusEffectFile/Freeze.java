package Model.StatusEffectFile;
import Model.*;

public class Freeze extends StatusEffect{

    public Freeze(){
        super("Freeze",2);
    }

    @Override
    public void applyEffect(Monsters monster){
    }

    @Override
    public boolean canMove(){
        return false;
    }
}