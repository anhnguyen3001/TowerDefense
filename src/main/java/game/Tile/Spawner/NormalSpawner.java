package game.Tile.Spawner;

import game.Enemy.NormalEnemy;
import game.GameField;

public class NormalSpawner extends AbstractSpawner{
    public NormalSpawner(double x, double y, int delayTime, int numOfTurn) {
        super(x, y, delayTime, numOfTurn);
    }

    @Override
    protected NormalEnemy doSpawn(GameField field) {
        return new NormalEnemy(field.getWayPoint(), getX(), getY());
    }
}
