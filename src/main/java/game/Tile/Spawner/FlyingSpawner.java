package game.Tile.Spawner;

import game.Config;
import game.Enemy.BossEnemy;
import game.Enemy.FlyingEnemy;
import game.GameField;

import java.util.ArrayList;

public class FlyingSpawner extends AbstractSpawner{
    public FlyingSpawner(double x, double y, int tick, int delayTime, int numOfTurn) {
        super(x, y,tick,  delayTime, numOfTurn);
    }

    @Override
    protected FlyingEnemy doSpawn(GameField field) {
        ArrayList<ArrayList<Double>> wayPoint = field.getWayPoint();
        for (int i = 0; i < wayPoint.size(); i++){

            if (wayPoint.get(i).get(0).equals(getX()) && wayPoint.get(i).get(1).equals(getY())) {
                return new FlyingEnemy(wayPoint.get(i), getX(), getY(), Config.FLYING_E_Blood, i);
            }
        }

        return null;
    }

    public String toString(){
        return "FlyingSpawner " + super.toString();
    }
}
