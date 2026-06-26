package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;

public class Phantomur extends Monsters {

    public Phantomur() {
        super("Phantomur", Element.Grass, Rarity.Epic, 165, 37, 28, 34);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new ShadowVine());
        skills.add(new NatureCurse());
    }

    @Override
    public Monsters cloneMonster() {
        return new Phantomur();
    }
}