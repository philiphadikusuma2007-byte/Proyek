package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class VineWhip extends AttackSkill{
    
    public VineWhip(){
        super("Vine Whip", Element.Grass, 40, new Poison(), 40);
    }
}
