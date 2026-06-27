package Model.BattleActions;
import Game.*;

public class AttackAction implements BattleAction {

    @Override
    public void execute(Battle battle) {
        battle.executeBasicAttack();
    }
}