package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;

public class Embercat extends Monsters {

    public Embercat() {
        super("Embercat", Element.Fire, Rarity.Rare, 105, 26, 16, 20);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Scratch());
        skills.add(new FlameBurst());
    }

    @Override
    public Monsters cloneMonster() {
        return new Embercat();
    }
}