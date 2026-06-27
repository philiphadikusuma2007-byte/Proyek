package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class VineWhip extends AttackSkill{
    
    public VineWhip(){
        super("Vine Whip", new Grass(), 40, new Poison(), 40);
    }
}
