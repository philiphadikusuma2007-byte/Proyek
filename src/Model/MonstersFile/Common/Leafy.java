package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;

public class Leafy extends Monsters{
    public Leafy() {
        super("Leafy" ,Element.Grass, Rarity.Common, 80, 15, 12, 10);
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
