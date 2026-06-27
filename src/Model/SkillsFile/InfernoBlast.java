package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class InfernoBlast extends AttackSkill{
    
    public InfernoBlast(){
        super("Inferno Blast", new Fire(), 75, new Burn(), 50);
    }
}
