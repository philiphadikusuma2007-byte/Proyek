package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;
public class Terraking extends Monsters {

    public Terraking() {
        super("Terraking", Element.Earth, Rarity.Epic, 200, 35, 40, 18);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new EarthPunch());
        skills.add(new Earthquake());
    }

    @Override
    public Monsters cloneMonster() {
        return new Terraking();
    }
}