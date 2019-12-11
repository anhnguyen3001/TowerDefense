package game.Tile.Tower;

import game.Bullet.NormalBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.helper.Asset;

public class NormalTower extends AbstractTower{
    public NormalTower(double x, double y, int tick, int level){
        super(x, y, Config.NORMAL_T_Range, Config.NORMAL_T_Speed, Config.NORMAL_T_DAMAGE,
                Config.NORMAL_T_Cost, tick, level, Asset.NORMAL_TOWER);
    }

    @Override
    public NormalBullet spawnBullet(double x, double y, AbstractEnemy enemy) {
        return new NormalBullet(x, y, enemy, getAngleRotation(), getDamage());
    }

    public String toString(){
        return "NormalTower " + super.toString();
    }
}
