package Model.SkillsFile;
import Model.*;

public class ThunderShock extends AttackSkill{
    
    public ThunderShock(){
         super("Thunder Shock", Element.Electric, 45, StatusEffect.Stun, 30);
    }
}
