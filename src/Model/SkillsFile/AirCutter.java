package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class AirCutter extends AttackSkill{
    
    public AirCutter(){
        super("Air Cutter", new Wind(), 65, new None(), 0);
    }
}
