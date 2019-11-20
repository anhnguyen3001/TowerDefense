package game.Tile.Spawner;

import game.Config;
import game.Enemy.AbstractEnemy;
import game.GameField;
import game.Tile.AbstractTile;
import game.Tile.UpdateEntity;

public abstract class AbstractSpawner extends AbstractTile implements UpdateEntity {
    private int intervalTime;
    private int tick;
    private int numOfInterval;

    public AbstractSpawner(double x, double y, int intervalTime, int numOfInterval) {
        super(x, y, Config.SPAWNER_PATH);
        this.intervalTime = intervalTime;
        this.numOfInterval = numOfInterval;
        tick = 0;
    }

    public void setNumOfInterval(int numOfInterval) {
        this.numOfInterval = numOfInterval;
    }

    public void setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
    }

    public void update(GameField field){
        tick -= 1;

        if (tick <= 0 && numOfInterval > 0) {
            field.addSpawnList(doSpawn(field));
            tick = intervalTime;
            numOfInterval--;
        }
    }

    public boolean stillSpawn(){
        return (numOfInterval > 0);
    }

    protected abstract <T extends AbstractEnemy> T doSpawn(GameField field);
}
