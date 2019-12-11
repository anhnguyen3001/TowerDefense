package game.Tile;

import game.*;
import game.Enemy.AbstractEnemy;
import game.helper.Asset;

import java.util.ArrayList;

public class Target extends AbstractTile implements UpdateEntity{
    public Target(double x, double y) {
        super(x, y, Asset.target);
    }

    @Override
    public void update(GameField field) {
        ArrayList<AbstractEntity> destroyList = field.getDestroyList();
        Player player = field.getPlayer();

        for (AbstractEntity entity:destroyList)
            if (entity instanceof AbstractEnemy && ((AbstractEnemy) entity).isAlive())
                player.setLive(player.getLive() - 1);
    }
}
