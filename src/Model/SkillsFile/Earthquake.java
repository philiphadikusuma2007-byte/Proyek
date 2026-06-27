package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Earthquake extends AttackSkill{
    
    public Earthquake(){
        super("Earthquake", Element.Earth, 80, new DeffBuff(), 100);
    }
}
