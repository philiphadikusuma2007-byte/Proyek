package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class TimeStorm extends AttackSkill{
    
    public TimeStorm(){
        super("Time Storm", Element.Wind, 90, new Stun(), 50);
    }
}
