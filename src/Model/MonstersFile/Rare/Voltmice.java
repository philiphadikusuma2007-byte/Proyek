package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Voltmice extends Monsters {

    public Voltmice() {
        super("Voltmice", new Electric(), Rarity.Rare, 100, 24, 15, 25);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new QuickAttack());
        skills.add(new Thunderbolt());
    }

    @Override
    public Monsters cloneMonster() {
        return new Voltmice();
    }
}