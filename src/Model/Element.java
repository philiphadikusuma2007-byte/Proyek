package Model;

import Model.ElementsFile.Dark;
import Model.ElementsFile.Earth;
import Model.ElementsFile.Electric;
import Model.ElementsFile.Fire;
import Model.ElementsFile.Grass;
import Model.ElementsFile.Light;
import Model.ElementsFile.Water;
import Model.ElementsFile.Wind;

public abstract class Element {
    protected String name;

    public Element(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public static Element fromString(String nama){

        switch(nama){

            case "Fire":
                return new Fire();

            case "Water":
                return new Water();

            case "Grass":
                return new Grass();

            case "Electric":
                return new Electric();

            case "Earth":
                return new Earth();

            case "Wind":
                return new Wind();

            case "Dark":
                return new Dark();

            case "Light":
                return new Light();

            default:
                throw new IllegalArgumentException("Element tidak dikenal: " + nama);
        }
    }

    public abstract double getMultiplier(Element enemyElement);
}