package game.Tile.Tower;

import game.Bullet.NormalBullet;
import game.Config;
import game.Enemy.AbstractEnemy;

public class NormalTower extends AbstractTower{
    public NormalTower(double x, double y){
        super(x, y, Config.NORMAL_T_Range, Config.NORMAL_T_Speed, Config.NORMAL_T_DAMAGE, Config.NORMAL_T_Cost,
                Config.NORMAL_T_PATH);
    }
    @Override
    public NormalBullet spawnBullet(double x, double y, AbstractEnemy enemy) {
        return new NormalBullet(x, y, enemy, getAngleRotation(), getDamage());
    }
}
