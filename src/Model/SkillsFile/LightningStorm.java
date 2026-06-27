package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class LightningStorm extends AttackSkill{
    
    public LightningStorm(){
        super("Lightning Storm", Element.Electric, 80, new Stun(), 45);
    }
}
