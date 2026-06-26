package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;

public class Pyropup extends Monsters {

    public Pyropup() {
        super("Pyroup", Element.Fire, Rarity.Common, 75, 18, 10, 12 );
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Tackle());
        skills.add(new Ember());
    }

    @Override
    public Monsters cloneMonster() {
        return new Pyropup();
    }
}