package Model.StatusEffectFile;
import Model.*;

public class None extends StatusEffect {
    
    public None() {
        super("None", 0);
    }

    @Override
    public void applyEffect(Monsters monster) {
        // No effect
    }
}
