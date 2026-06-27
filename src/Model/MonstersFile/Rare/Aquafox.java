package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Aquafox extends Monsters {

    public Aquafox() {
        super("Aquafox", new Water(), Rarity.Rare, 125, 20, 22, 14);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new SplashTail());
        skills.add(new WaterPulse());
    }

    @Override
    public Monsters cloneMonster() {
        return new Aquafox();
    }
}