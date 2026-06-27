package Model.MonstersFile.Legendary;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Chronos extends Monsters {

    public Chronos() {
        super("Chronos", new Wind(), Rarity.Legendary, 220, 52, 45, 48);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new WindSlash());
        skills.add(new TimeStorm());
    }

    @Override
    public Monsters cloneMonster() {
        return new Chronos();
    }
}