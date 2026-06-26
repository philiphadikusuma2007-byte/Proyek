package Model.SkillsFile;
import Model.*;
public class Tsunami extends AttackSkill{
    
    public Tsunami(){
        super("Tsunami", Element.Water, 75, StatusEffect.Freeze, 40);
    }
}
