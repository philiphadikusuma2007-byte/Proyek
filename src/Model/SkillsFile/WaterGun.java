package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class WaterGun extends AttackSkill{
    
    public WaterGun(){
        super("Water Gun", Element.Water, 40, new Freeze(), 30
        );
    }
}
