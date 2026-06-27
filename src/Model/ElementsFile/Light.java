package Model.ElementsFile;
import Model.*;

public class Light extends Element{

    public Light(){
        super("Light");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Dark) return 2.0;
        return 1.0;
    }
    
}
