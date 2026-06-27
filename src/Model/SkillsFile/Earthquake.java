package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Earthquake extends AttackSkill{
    
    public Earthquake(){
        super("Earthquake", new Earth(), 80, new DeffBuff(), 100);
    }
}
