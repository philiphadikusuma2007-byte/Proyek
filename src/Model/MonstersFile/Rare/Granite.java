package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;

public class Granite extends Monsters {

    public Granite() {
        super("Granite", Element.Earth, Rarity.Rare, 140, 18, 26, 10);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new RockThrow());
        skills.add(new StoneEdge());
    }

    @Override
    public Monsters cloneMonster() {
        return new Granite();
    }
}