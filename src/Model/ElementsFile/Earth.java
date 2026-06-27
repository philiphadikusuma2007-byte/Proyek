package Model.ElementsFile;
import Model.*;

public class Earth extends Element{

    public Earth(){
        super("Earth");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Electric) return 2.0;
        if(enemy instanceof Wind) return 0.5;
        return 1.0;
    }
}