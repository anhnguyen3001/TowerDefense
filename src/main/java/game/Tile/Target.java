package game.Tile;

import game.Config;
import game.Enemy.AbstractEnemy;
import game.GameField;

import java.util.ArrayList;

public class Target extends AbstractTile implements UpdateEntity{
    private int health;
    public Target(double x, double y, int health) {
        super(x, y, Config.TARGET_PATH);
    }

    @Override
    public void update(GameField field) {
        ArrayList<AbstractEnemy> enemies = field.getEnemies();

        for (AbstractEnemy enemy:enemies)
            if (enemy.getX() == getX() && enemy.getY() == getY())
                health--;
    }

    public int getHealth(){
        return health;
    }
}
