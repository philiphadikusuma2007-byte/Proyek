package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Leafy extends Monsters{
    public Leafy() {
        super("Leafy" ,new Grass(), Rarity.Common, 80, 15, 12, 10);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Tackle());
        skills.add(new VineWhip());
    }

    @Override
    public Monsters cloneMonster() {
        return new Leafy();
    }
}
