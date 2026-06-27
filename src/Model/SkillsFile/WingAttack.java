package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class WingAttack extends AttackSkill{
    
    public WingAttack(){
        super("Wing Attack", new Electric(), 45, new None(), 0);
    }
}
