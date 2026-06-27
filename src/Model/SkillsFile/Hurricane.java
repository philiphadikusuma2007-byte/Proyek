package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Hurricane extends AttackSkill{
    
    public Hurricane(){
        super("Hurricane", new Wind(), 95, new Stun(), 45);
    }
}
