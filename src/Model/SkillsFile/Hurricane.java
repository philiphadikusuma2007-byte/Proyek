package Model.SkillsFile;
import Model.*;

public class Hurricane extends AttackSkill{
    
    public Hurricane(){
        super("Hurricane", Element.Wind, 95, StatusEffect.Stun, 45);
    }
}
