package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class NatureCurse extends AttackSkill{
    
    public NatureCurse(){
        super( "Nature Curse", Element.Grass, 78, new Poison(), 50);
    }
}
