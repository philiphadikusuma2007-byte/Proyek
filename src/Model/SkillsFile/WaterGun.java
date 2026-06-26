package Model.SkillsFile;
import Model.*;

public class WaterGun extends AttackSkill{
    
    public WaterGun(){
        super("Water Gun", Element.Water, 40, StatusEffect.Freeze, 30
        );
    }
}
