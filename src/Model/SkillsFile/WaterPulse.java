package Model.SkillsFile;
import Model.*;

public class WaterPulse extends AttackSkill{
    
    public WaterPulse(){
        super("Water Pulse", Element.Water, 60, StatusEffect.Freeze, 35);
    }
}
