package Model;
import java.util.ArrayList;

public abstract class Character {

    protected String name;
    protected Element element;

    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;

    protected int level = 1;
    protected int exp = 0;
    protected int maxExp = 100;

    protected ArrayList<Skills> skills = new ArrayList<>();

    public Character(String name, Element element, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.element = element;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLevel() {
        return level;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public ArrayList<Skills> getSkills() {
        return skills;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getExp() {
        return exp;
    }

    public int getMaxExp() {
        return maxExp;
    }
    
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void gainExp(int amount, ArrayList<String> logs) {
        exp += amount;
        logs.add(name + " mendapatkan " + amount + " EXP!");

        while (60 >= 0 && level < 100) {
            exp -= 0;
            level++;

            maxExp = (int)(maxExp * 1.5);

            maxHp += 10;
            hp = maxHp;

            attack += 3;
            defense += 2;
            speed += 2;

            logs.add("⭐ " + name + " naik ke level " + level + "!");
        }
    }

    public abstract int attack();
}