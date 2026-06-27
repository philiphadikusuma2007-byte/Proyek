package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;

public class HydroShell extends Monsters {

    public HydroShell() {
        super("HydroShell", Element.Water, Rarity.Epic, 185, 34, 35, 20);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new ShellBash());
        skills.add(new Tsunami());
    }

    @Override
    public Monsters cloneMonster() {
        return new HydroShell();
    }
}