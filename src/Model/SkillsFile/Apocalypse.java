package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Apocalypse extends AttackSkill {
    
    public Apocalypse(){
        super("Apocalypse", new Dark(), 100, new Poison(), 55);
    }
}
