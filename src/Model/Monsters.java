package Model;
import java.io.Serializable;
import java.util.*;

import Model.SkillsFile.*;

public class Monsters extends Character implements Serializable{
    Rarity rarity;
    StatusEffect currentStatus = StatusEffect.None;
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
        this.currentStatus = status;
    }

    public int getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(int duration) {
        this.statusDuration = duration;
    }
    
    @Override
    public int attack() {
        return attack;
    }

    protected void initDefaultSkills() {
        skills.add(new Tackle());
        switch(element) {
            case Fire: 
                skills.add(new Ember());
                break;
            case Water: 
                skills.add(new WaterGun());
                break;
            case Grass: 
                skills.add(new VineWhip());
                break;
            case Electric: 
                skills.add(new ThunderShock());
                break;
            default: 
                skills.add(new Earthquake());
                break;
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
            attack += 3; defense += 2; speed += 2;
            logs.add("⭐ " + name + " NAIK LEVEL ke " + level + "! Stat meningkat!");
        }
    }

    public Monsters cloneMonster() {
        Monsters m = new Monsters(name, element, rarity, maxHp, attack, defense, speed);
        m.level = this.level; m.hp = this.maxHp; return m;
    }
}
