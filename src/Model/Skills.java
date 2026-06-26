package Model;
import java.io.Serializable;

public abstract class Skills implements Serializable{
    protected String name;
    protected Element element;
    protected int power;
    protected StatusEffect effect;
    protected int effectChance;

    public Skills(String name, Element element, int power, StatusEffect effect, int effectChance) {
        this.name = name; 
        this.element = element; 
        this.power = power; 
        this.effect = effect; 
        this.effectChance = effectChance;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public Element getElement() {
        return element;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    public int getEffectChance() {
        return effectChance;
    }

    public abstract void use(Monsters attacker, Monsters target);
}
