package Model.MonstersFile.Legendary;
import Model.*;
import Model.SkillsFile.*;
public class Chronos extends Monsters {

    public Chronos() {
        super("Chronos", Element.Wind, Rarity.Legendary, 220, 52, 45, 48);
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