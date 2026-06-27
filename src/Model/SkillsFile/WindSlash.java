package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class WindSlash extends AttackSkill{
    
    public WindSlash(){
        super("Wind Slash", new Wind(), 60, new None(), 0);
    }
}
