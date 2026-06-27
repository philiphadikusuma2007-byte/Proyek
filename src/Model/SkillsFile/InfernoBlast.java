package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class InfernoBlast extends AttackSkill{
    
    public InfernoBlast(){
        super("Inferno Blast", Element.Fire, 75, new Burn(), 50);
    }
}
