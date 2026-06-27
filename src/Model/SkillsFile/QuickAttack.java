package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class QuickAttack extends AttackSkill{
    
    public QuickAttack(){
        super("Quick Attack", new Earth(), 40, new None(), 0);
    }
}
