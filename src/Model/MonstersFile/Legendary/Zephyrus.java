package Model.MonstersFile.Legendary;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Zephyrus extends Monsters {

    public Zephyrus() {
        super("Zephyrus", new Wind(), Rarity.Legendary, 210, 55, 40, 55);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new AirCutter());
        skills.add(new Hurricane());
    }

    @Override
    public Monsters cloneMonster() {
        return new Zephyrus();
    }
}