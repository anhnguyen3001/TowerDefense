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
        double angle = getAngleRotation();
        double difference = Math.tan(Math.toRadians(angle));
        /**if (angle <= 90 && angle >= 0) {
            x += 0.5 * difference;
            y -= 0.5 / difference;
        }
        else if (angle < 180){
            x += 0.5 * difference;
            y += 0.5 / difference;
        } else if (angle < 270){
            x -=0.5 * difference;
            y += 0.5 / difference;
        } else {
            x -= 0.5 * difference;
            y -= 0.5 / difference;
        }**/

        /**if (angle%180 == 0) difference = 1;

        if (angle >= 0 && angle <= 180) x += (double)1/3 * difference;
        else x -= (double)1/3*difference;

        if (angle/90 <= 1 || angle/90 >= 3) y -= (double)2/3 / difference;
        else y += (double)2/3/difference;**/

        return new NormalBullet(x, y, enemy, getAngleRotation(), getDamage());
    }

    public String toString(){
        return "NormalTower " + super.toString();
    }
}
