package Model.SkillsFile;
import Model.StatusEffectFile.*;
import Model.ElementsFile.*;

public class Ember extends AttackSkill{

    public Ember(){
        super("Ember", new Fire(), 40, new Burn(), 40 );
    }

}