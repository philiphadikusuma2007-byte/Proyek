package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Apocalypse extends AttackSkill {
    
    public Apocalypse(){
        super("Apocalypse", Element.Dark, 100, new Poison(), 55);
    }
}
