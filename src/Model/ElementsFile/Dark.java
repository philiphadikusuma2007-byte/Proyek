package Model.ElementsFile;
import Model.*;

public class Dark extends Element{

    public Dark(){
        super("Dark");
    }

    @Override
    public double getMultiplier(Element enemy){
        if(enemy instanceof Light) return 2.0;
        return 1.0;
    }
    
}
