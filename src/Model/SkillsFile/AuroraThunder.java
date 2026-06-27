package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class AuroraThunder extends AttackSkill{
    
    public AuroraThunder(){
        super("Aurora Thunder", new Electric(), 82, new Stun(), 45);
    }
}
