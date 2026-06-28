package World;

import Model.Items;

public class Chest {
    public int x;
    public int y;
    public Items reward;     // null jika hadiahnya gold
    public int goldReward;   // 0 jika hadiahnya item
    public boolean opened;

    // Constructor untuk hadiah item
    public Chest(int x, int y, Items reward) {
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.goldReward = 0;
        this.opened = false;
    }

    // Constructor untuk hadiah gold
    public Chest(int x, int y, int goldReward) {
        this.x = x;
        this.y = y;
        this.reward = null;
        this.goldReward = goldReward;
        this.opened = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Items getReward() { return reward; }
    public int getGoldReward() { return goldReward; }
    public boolean isGoldReward() { return reward == null; }
    public boolean isOpened() { return opened; }
    public void open() { this.opened = true; }
}