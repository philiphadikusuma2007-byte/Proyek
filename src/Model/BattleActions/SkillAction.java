package Model.BattleActions;
import Game.*;

public class SkillAction implements BattleAction {

    @Override
    public void execute(Battle battle){
        battle.openSkillMenu();
    }
}