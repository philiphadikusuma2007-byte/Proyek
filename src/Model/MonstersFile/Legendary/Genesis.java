package Model.MonstersFile.Legendary;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Genesis extends Monsters {

    public Genesis() {
        super("Genesis", new Light(), Rarity.Legendary, 240, 50, 50, 50);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new HolyStrike());
        skills.add(new DivineJudgement());
    }

    @Override
    public Monsters cloneMonster() {
        return new Genesis();
    }
}