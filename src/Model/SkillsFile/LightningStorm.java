package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class LightningStorm extends AttackSkill{
    
    public LightningStorm(){
        super("Lightning Storm", new Electric(), 80, new Stun(), 45);
    }
}
