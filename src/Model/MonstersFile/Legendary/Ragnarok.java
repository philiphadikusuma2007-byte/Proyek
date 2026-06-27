package Model.MonstersFile.Legendary;
import Model.*;
import Model.SkillsFile.*;
import Model.ElementsFile.*;

public class Ragnarok extends Monsters {

    public Ragnarok() {
        super("Ragnarok", new Dark(), Rarity.Legendary, 250, 60, 38, 42);
    }

    @Override
    protected void initDefaultSkills() {
        skills.clear();
        skills.add(new DarkClaw());
        skills.add(new Apocalypse());
    }

    @Override
    public Monsters cloneMonster() {
        return new Ragnarok();
    }
}