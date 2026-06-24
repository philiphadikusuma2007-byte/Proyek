import java.io.Serializable;
import java.util.ArrayList;

public class Monsters implements Serializable{
    String name;
    Element element;
    Rarity rarity;
    int level, hp, maxHp, attack, defense, speed, exp, maxExp;
    ArrayList<Skills> skills = new ArrayList<>();
    StatusEffect currentStatus = StatusEffect.None;
    int statusDuration = 0;

    public Monsters(String name, Element element, Rarity rarity, int hp, int att, int def, int spd) {
        this.name = name; this.element = element; this.rarity = rarity;
        this.level = 1; this.maxHp = hp; this.hp = hp;
        this.attack = att; this.defense = def; this.speed = spd;
        this.exp = 0; this.maxExp = 100;
        initDefaultSkills();
    }
    
    private void initDefaultSkills() {
        skills.add(new Skills("Tackle", Element.Earth, 35, StatusEffect.None, 0));
        switch(element) {
            case Fire: 
                skills.add(new Skills("Ember", Element.Fire, 40, StatusEffect.Burn, 40));
                break;
            case Water: 
                skills.add(new Skills("Water Gun", Element.Water, 40, StatusEffect.Freeze, 30));
                break;
            case Grass: 
                skills.add(new Skills("Vine Whip", Element.Grass, 40, StatusEffect.Poison, 40));
                break;
            case Electric: 
                skills.add(new Skills("Thunder Shock", Element.Electric, 45, StatusEffect.Stun, 30));
                break;
            default: 
                skills.add(new Skills("Power Buff", element, 10, StatusEffect.Def_Buff, 100));
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
