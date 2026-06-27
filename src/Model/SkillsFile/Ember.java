package Model.SkillsFile;
import Model.*;
import Model.StatusEffectFile.*;

public class Ember extends AttackSkill{

    public Ember(){
        super("Ember", Element.Fire, 40, new Burn(), 40 );
    }

}