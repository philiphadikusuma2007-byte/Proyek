package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Aurorion extends Monsters {

    public Aurorion() {
        super("Aurorion", new Electric(), Rarity.Epic, 170, 36, 30, 36);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new SparkBlade());
        skills.add(new AuroraThunder());
    }

    @Override
    public Monsters cloneMonster() {
        return new Aurorion();
    }
}