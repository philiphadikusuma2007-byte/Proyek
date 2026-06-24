import java.io.Serializable;

public class Skills implements Serializable{
    String name;
    public Element element;
    int power;
    StatusEffect effect;
    int effectChance;

    public Skills(String name, Element element, int power, StatusEffect effect, int effectChance) {
        this.name = name; this.element = element; this.power = power; this.effect = effect; this.effectChance = effectChance;
    }
}
