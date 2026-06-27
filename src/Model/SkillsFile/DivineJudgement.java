package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class DivineJudgement extends AttackSkill{
    
    public DivineJudgement(){
        super( "Divine Judgment", Element.Light, 95, new DeffBuff(), 100);
    }
}