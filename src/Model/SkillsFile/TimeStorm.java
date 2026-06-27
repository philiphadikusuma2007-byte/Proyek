package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class TimeStorm extends AttackSkill{
    
    public TimeStorm(){
        super("Time Storm", new Wind(), 90, new Stun(), 50);
    }
}
