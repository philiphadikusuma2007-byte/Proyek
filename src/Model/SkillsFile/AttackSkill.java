package Model.SkillsFile;
import Model.*;

import java.util.*;

public class AttackSkill extends Skills{

    public AttackSkill(String name, Element element, int power, StatusEffect effect, int effectChance){
        super(name, element, power, effect, effectChance);
    }

    @Override
    public void use(Monsters attacker, Monsters target){

        int damage = Math.max(1, attacker.getAttack()+power-target.getDefense()/2
        );

        target.takeDamage(damage);

        if(new Random().nextInt(100) < effectChance){
            target.setCurrentStatus(effect);
            target.setStatusDuration(3);
        }

    }

}