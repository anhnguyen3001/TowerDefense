package game.Tile.Spawner;

import game.Enemy.TankerEnemy;
import game.GameField;

public class TankerSpawner extends AbstractSpawner{
    public TankerSpawner(double x, double y, int delayTime, int numOfTurn) {
        super(x, y, delayTime, numOfTurn);
    }
    @Override
    protected TankerEnemy doSpawn(GameField field) {
        return new TankerEnemy(field.getWayPoint(), getX(), getY());
    }
}
