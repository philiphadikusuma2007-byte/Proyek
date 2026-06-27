package Model.MonstersFile.Common;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Droplet extends Monsters{
    public Droplet(){
        super("Droplet", new Water(), Rarity.Common, 90, 13, 15, 8);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new Tackle());
        skills.add(new WaterGun());
    }
    
    @Override
    public Monsters cloneMonster() {
        return new Droplet();
    }
}
