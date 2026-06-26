package Model.MonstersFile.Common;

import Model.*;
import Model.SkillsFile.*;

public class Pebblem extends Monsters {

    public Pebblem() {
        super("Pebblem", Element.Earth, Rarity.Common, 100, 12, 18, 5);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Tackle());
        skills.add(new RockSmash());
    }

    @Override
    public Monsters cloneMonster() {
        return new Pebblem();
    }
}