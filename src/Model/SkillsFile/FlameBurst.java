package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class FlameBurst extends AttackSkill{
    
    public FlameBurst(){
        super( "Flame Burst", new Fire(), 60, new Burn(), 45);
    }
}
