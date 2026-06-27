package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Thunderbolt extends AttackSkill {
    
    public Thunderbolt(){
        super("Thunderbolt", Element.Electric, 65, new Stun(), 40);
    }
}
