package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Tsunami extends AttackSkill{
    
    public Tsunami(){
        super("Tsunami", new Water(), 75, new Freeze(), 40);
    }
}
