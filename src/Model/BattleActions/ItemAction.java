package Model.BattleActions;
import Game.*;

public class ItemAction implements BattleAction {

    @Override
    public void execute(Battle battle){
        battle.openItemMenu();
    }
}