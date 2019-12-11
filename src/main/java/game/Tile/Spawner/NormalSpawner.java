package game.Tile.Spawner;

import game.Config;
import game.Enemy.FlyingEnemy;
import game.Enemy.NormalEnemy;
import game.GameField;

import java.util.ArrayList;

public class NormalSpawner extends AbstractSpawner{
    public NormalSpawner(double x, double y, int tick, int delayTime, int numOfTurn) {
        super(x, y,tick,  delayTime, numOfTurn);
    }

    @Override
    protected NormalEnemy doSpawn(GameField field) {
        ArrayList<ArrayList<Double>> wayPoint = field.getWayPoint();
        for (int i = 0; i < wayPoint.size(); i++){
            if (wayPoint.get(i).get(0).equals(getX()) && wayPoint.get(i).get(1).equals(getY())) {
                return new NormalEnemy(wayPoint.get(i), getX(), getY(), Config.NORMAL_E_Blood, i);
            }
        }
        return null;
    }

    public String toString(){
        return "NormalSpawner " + super.toString();
    }
}
