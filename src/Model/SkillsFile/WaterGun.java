package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class WaterGun extends AttackSkill{
    
    public WaterGun(){
        super("Water Gun", new Water(), 40, new Freeze(), 30
        );
    }
}
