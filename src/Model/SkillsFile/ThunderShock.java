package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class ThunderShock extends AttackSkill{
    
    public ThunderShock(){
         super("Thunder Shock", new Electric(), 45, new Stun(), 30);
    }
}
