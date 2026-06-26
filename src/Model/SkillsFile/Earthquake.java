package Model.SkillsFile;
import Model.*;

public class Earthquake extends AttackSkill{
    
    public Earthquake(){
        super("Earthquake", Element.Earth, 80, StatusEffect.Def_Buff, 100);
    }
}
