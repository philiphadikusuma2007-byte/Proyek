package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class WaterPulse extends AttackSkill{
    
    public WaterPulse(){
        super("Water Pulse", Element.Water, 60, new Freeze(), 35);
    }
}
