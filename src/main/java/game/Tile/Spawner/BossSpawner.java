package game.Tile.Spawner;

import game.Config;
import game.Enemy.BossEnemy;
import game.GameField;

import java.util.ArrayList;

public class BossSpawner extends AbstractSpawner{
    public BossSpawner(double x, double y, int tick, int delayTime, int numOfTurn) {
        super(x, y,tick,  delayTime, numOfTurn);
    }

    @Override
    protected BossEnemy doSpawn(GameField field) {
        ArrayList<ArrayList<Double>> wayPoint = field.getWayPoint();
        for (int i = 0; i < wayPoint.size(); i++){

            if (wayPoint.get(i).get(0).equals(getX()) && wayPoint.get(i).get(1).equals(getY())) {
                return new BossEnemy(wayPoint.get(i), getX(), getY(), Config.BOSS_E_Blood, i);
            }
        }

        return null;
    }

    public String toString(){
        return "BossSpawner " + super.toString();
    }
}
