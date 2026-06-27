package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class WaterPulse extends AttackSkill{
    
    public WaterPulse(){
        super("Water Pulse", new Water(), 60, new Freeze(), 35);
    }
}
