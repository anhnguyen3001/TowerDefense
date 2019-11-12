package game.Tile.Spawner;

import game.Enemy.SmallerEnemy;
import game.GameField;

public class SmallerSpawner extends AbstractSpawner{
    public SmallerSpawner(double x, double y, int delayTime, int numOfTurn) {
        super(x, y, delayTime, numOfTurn);
    }
    @Override
    protected SmallerEnemy doSpawn(GameField field) {
        return new SmallerEnemy(field.getWayPoint(), getX(), getY());
    }
}
