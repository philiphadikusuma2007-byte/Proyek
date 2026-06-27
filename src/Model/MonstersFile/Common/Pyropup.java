package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Pyropup extends Monsters {

    public Pyropup() {
        super("Pyroup", new Fire(), Rarity.Common, 75, 18, 10, 12 );
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