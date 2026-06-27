package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class DivineJudgement extends AttackSkill{
    
    public DivineJudgement(){
        super( "Divine Judgment", new Light(), 95, new DeffBuff(), 100);
    }
}