package Model.SkillsFile;
import Model.*;

public class FlameBurst extends AttackSkill{
    
    public FlameBurst(){
        super( "Flame Burst", Element.Fire, 60,StatusEffect.Burn, 45);
    }
}
