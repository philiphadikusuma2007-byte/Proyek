package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class FlameBurst extends AttackSkill{
    
    public FlameBurst(){
        super( "Flame Burst", Element.Fire, 60, new Burn(), 45);
    }
}
