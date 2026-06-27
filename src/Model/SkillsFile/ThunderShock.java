package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class ThunderShock extends AttackSkill{
    
    public ThunderShock(){
         super("Thunder Shock", Element.Electric, 45, new Stun(), 30);
    }
}
