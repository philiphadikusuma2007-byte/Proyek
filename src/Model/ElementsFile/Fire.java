package Model.ElementsFile;
import Model.*;

public class Fire extends Element{

    public Fire(){
        super("Fire");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Grass) return 2.0;
        if(enemy instanceof Water) return 0.5;
        return 1.0;
    }
}