package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class StoneEdge extends AttackSkill{
    
    public StoneEdge(){
        super("Stone Edge", Element.Earth, 65, new DeffBuff(), 100);
    }
}
