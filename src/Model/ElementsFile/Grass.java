package Model.ElementsFile;
import Model.*;

public class Grass extends Element{

    public Grass(){
        super("Grass");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Water) return 2.0;
        if(enemy instanceof Fire) return 0.5;
        return 1.0;
    }
}