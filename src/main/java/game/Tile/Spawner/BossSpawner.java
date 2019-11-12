package game.Tile.Spawner;

import game.Enemy.BossEnemy;
import game.GameField;

public class BossSpawner extends AbstractSpawner{
    public BossSpawner(double x, double y, int delayTime, int numOfTurn) {
        super(x, y, delayTime, numOfTurn);
    }

    @Override
    protected BossEnemy doSpawn(GameField field) {
        return new BossEnemy(field.getWayPoint(), getX(), getY());
    }
}
