package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;
public class Stormbird extends Monsters {

    public Stormbird() {
        super("Stormbird", Element.Electric, Rarity.Epic, 155, 38, 24, 40);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new WingAttack());
        skills.add(new LightningStorm());
    }

    @Override
    public Monsters cloneMonster() {
        return new Stormbird();
    }
}