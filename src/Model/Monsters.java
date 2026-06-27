package Model;
import java.io.Serializable;
import java.util.*;

import Model.ElementsFile.Dark;
import Model.ElementsFile.Earth;
import Model.ElementsFile.Electric;
import Model.ElementsFile.Fire;
import Model.ElementsFile.Grass;
import Model.ElementsFile.Light;
import Model.ElementsFile.Water;
import Model.ElementsFile.Wind;
import Model.SkillsFile.*;
import Model.StatusEffectFile.*;

public class Monsters extends Character implements Serializable{
    Rarity rarity;
    private StatusEffect currentStatus = new None();
    int statusDuration = 0;
    public Items equip = null;
    boolean targetEquip = false;
    Items itemDiequip = null;

    public Monsters(String name, Element element, Rarity rarity, int hp, int attack, int defense, int speed) {
        super(name, element, hp, attack, defense, speed);
        this.rarity = rarity;
        initDefaultSkills();
    }

    public Rarity getRarity() {
        return rarity;
    }

    public StatusEffect getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(StatusEffect status) {
        currentStatus = status;
    }

    public int getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(int duration) {
        statusDuration = duration;
    }
    
    @Override
    public int attack() {
        return attack;
    }

    protected void initDefaultSkills() {
        skills.add(new Tackle());
        if (element instanceof Fire) {
            skills.add(new Ember());
        } else if (element instanceof Water) {
            skills.add(new WaterGun());
        } else if (element instanceof Grass) {
            skills.add(new VineWhip());
        } else if (element instanceof Electric) {
            skills.add(new ThunderShock());
        } else if (element instanceof Earth) {
            skills.add(new Earthquake());
        }
    }
    
    public void gainExp(int amount, ArrayList<String> logs) {
        this.exp += amount;
        logs.add(name + " mendapatkan " + amount + " EXP!");
        while (exp >= maxExp && level < 100) {
            exp -= maxExp;
            level++;
            maxExp = (int)(maxExp * 1.5);
            maxHp += 10; hp = maxHp;
            attack += 3; 
            defense += 2; 
            speed += 2;
            logs.add("⭐ " + name + " NAIK LEVEL ke " + level + "! Stat meningkat!");
        }
    }

    public Monsters cloneMonster() {
        Monsters m = new Monsters(name, element, rarity, maxHp, attack, defense, speed);
        m.level = this.level; m.hp = this.maxHp; return m;
    }
}
