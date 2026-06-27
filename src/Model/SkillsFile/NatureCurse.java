package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class NatureCurse extends AttackSkill{
    
    public NatureCurse(){
        super( "Nature Curse", new Grass(), 78, new Poison(), 50);
    }
}
