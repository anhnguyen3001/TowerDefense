package game.Tile.Spawner;

import game.AbstractEntity;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.GameField;
import game.Tile.AbstractTile;
import game.Tile.UpdateEntity;
import game.helper.Asset;

import java.util.ArrayList;

public abstract class AbstractSpawner extends AbstractTile implements UpdateEntity {
    private int intervalTime;
    private int tick;
    private int numOfInterval;

    public AbstractSpawner(double x, double y, int tick, int intervalTime, int numOfInterval) {
        super(x, y, Asset.spawner);
        this.intervalTime = intervalTime;
        this.numOfInterval = numOfInterval;
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }

    public void setNumOfInterval(int numOfInterval) {
        this.numOfInterval = numOfInterval;
    }

    public void setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getNumOfInterval() {
        return numOfInterval;
    }

    public void setNumOfInterval(int numOfInterval) {
        this.numOfInterval = numOfInterval;
    }

    public void setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
    }

    public void update(GameField field){
        --tick;

        if (tick <= 0 && numOfInterval > 0) {
            ArrayList<AbstractEntity> spawnList = field.getSpawnList();
            for (AbstractEntity entity:spawnList){
                if (entity instanceof AbstractEnemy) {
                    return;
                }
            }

            field.addSpawnList(doSpawn(field));
            tick = intervalTime;
            numOfInterval--;
        }
    }

    public boolean stillSpawn(){
        return (numOfInterval > 0);
    }

    protected abstract <T extends AbstractEnemy> T doSpawn(GameField field);

    public String toString(){
        String res = Integer.toString(tick) + " " + Integer.toString(intervalTime) + " " + Integer.toString(numOfInterval) + "\n";
        return res;
    }
}
