package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Thunderbolt extends AttackSkill {
    
    public Thunderbolt(){
        super("Thunderbolt", new Electric(), 65, new Stun(), 40);
    }
}
