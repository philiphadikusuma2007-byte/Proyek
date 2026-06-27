package Model.ElementsFile;
import Model.*;

public class Electric extends Element{

    public Electric(){
        super("Electric");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Water) return 2.0;
        if(enemy instanceof Earth) return 0.5;
        return 1.0;
    }
}