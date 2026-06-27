package Model.BattleActions;
import Game.*;
import Model.*;

public class RunAction implements BattleAction {

    @Override
    public void execute(Battle battle){
        battle.runBattle();
    }
}