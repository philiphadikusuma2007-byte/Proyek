package Model.SkillsFile;
import Model.*;

public class Thunderbolt extends AttackSkill {
    
    public Thunderbolt(){
        super("Thunderbolt", Element.Electric, 65, StatusEffect.Stun, 40);
    }
}
