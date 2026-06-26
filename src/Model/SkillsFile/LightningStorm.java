package Model.SkillsFile;
import Model.*;

public class LightningStorm extends AttackSkill{
    
    public LightningStorm(){
        super("Lightning Storm", Element.Electric, 80, StatusEffect.Stun, 45);
    }
}
