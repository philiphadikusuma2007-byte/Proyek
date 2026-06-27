package Model;

public abstract class StatusEffect {

    protected String name;
    protected int duration;

    public StatusEffect(String name, int duration){
        this.name = name;
        this.duration = duration;
    }

    public String getName(){
        return name;
    }

    public int getDuration(){
        return duration;
    }

    public void decreaseDuration(){
        duration--;
    }

    public boolean isFinished(){
        return duration <= 0;
    }

    public abstract void applyEffect(Monsters monster);

    public boolean canMove(){
        return true;
    }
}