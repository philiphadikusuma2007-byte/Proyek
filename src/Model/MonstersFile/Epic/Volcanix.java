package Model.MonstersFile.Epic;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Volcanix extends Monsters {

    public Volcanix() {
        super("Volcanix", new Fire(), Rarity.Epic, 160, 40, 25, 30);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new FireClaw());
        skills.add(new InfernoBlast());
    }

    @Override
    public Monsters cloneMonster() {
        return new Volcanix();
    }
}