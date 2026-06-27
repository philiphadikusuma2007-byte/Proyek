package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Hurricane extends AttackSkill{
    
    public Hurricane(){
        super("Hurricane", Element.Wind, 95, new Stun(), 45);
    }
}
