package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class StoneEdge extends AttackSkill{
    
    public StoneEdge(){
        super("Stone Edge", new Earth(), 65, new DeffBuff(), 100);
    }
}
