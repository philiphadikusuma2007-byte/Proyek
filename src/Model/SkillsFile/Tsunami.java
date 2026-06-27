package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Tsunami extends AttackSkill{
    
    public Tsunami(){
        super("Tsunami", Element.Water, 75, new Freeze(), 40);
    }
}
