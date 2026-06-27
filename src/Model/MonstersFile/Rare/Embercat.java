package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Embercat extends Monsters {

    public Embercat() {
        super("Embercat", new Fire(), Rarity.Rare, 105, 26, 16, 20);
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