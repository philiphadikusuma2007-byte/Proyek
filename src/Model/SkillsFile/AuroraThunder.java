package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class AuroraThunder extends AttackSkill{
    
    public AuroraThunder(){
        super("Aurora Thunder", Element.Electric, 82, new Stun(), 45);
    }
}
