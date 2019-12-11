package game.Tile.Spawner;

import game.Config;
import game.Enemy.FlyingEnemy;
import game.Enemy.SmallerEnemy;
import game.GameField;

import java.util.ArrayList;

public class SmallerSpawner extends AbstractSpawner{
    public SmallerSpawner(double x, double y, int tick, int delayTime, int numOfTurn) {
        super(x, y,tick,  delayTime, numOfTurn);
    }
    @Override
    protected SmallerEnemy doSpawn(GameField field) {
        ArrayList<ArrayList<Double>> wayPoint = field.getWayPoint();
        for (int i = 0; i < wayPoint.size(); i++){

            if (wayPoint.get(i).get(0).equals(getX()) && wayPoint.get(i).get(1).equals(getY())) {
                return new SmallerEnemy(wayPoint.get(i), getX(), getY(), Config.SMALLER_E_Blood, i);
            }
        }

        return null;
    }

    public String toString(){
        return "SmallerSpawner " + super.toString();
    }
}
