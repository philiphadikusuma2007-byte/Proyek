package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class EarthPunch extends AttackSkill{
    
    public EarthPunch(){
                super( "Earth Punch", new Earth(), 50, new None(), 0);
    }
}
