package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;

public class Sparky extends Monsters {

    public Sparky() {
        super("Sparky", Element.Electric, Rarity.Common, 70, 16, 9, 16);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Tackle());
        skills.add(new ThunderShock());
    }

    @Override
    public Monsters cloneMonster() {
        return new Sparky();
    }
}