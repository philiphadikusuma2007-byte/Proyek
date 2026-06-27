package Model.ElementsFile;
import Model.*;

public class Wind extends Element{

    public Wind(){
        super("Wind");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Earth) return 2.0;
        return 1.0;
    }
}