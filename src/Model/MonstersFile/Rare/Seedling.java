package Model.MonstersFile.Rare;
import Model.*;
import Model.SkillsFile.*;

public class Seedling extends Monsters{
    public Seedling(){
        super("Seedling", Element.Grass, Rarity.Rare, 110, 22, 19, 15);
    }

    @Override
    protected void initDefaultSkills(){
        skills.clear();
        skills.add(new Tackle());
        skills.add(new RazorLeaf());
    }

    @Override
    public Monsters cloneMonster(){
        return new Seedling();
    }
}
