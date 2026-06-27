package Model.ElementsFile;
import Model.*;

public class Water extends Element{

    public Water(){
        super("Water");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Fire) return 2.0;
        if(enemy instanceof Grass) return 0.5;
        return 1.0;
    }
}