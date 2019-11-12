package game.Bullet;

import game.Config;
import game.Enemy.AbstractEnemy;

public class NormalBullet extends AbstractBullet{
    public NormalBullet(double x, double y, AbstractEnemy target, double angle, double strength){
        super(x, y, Config.NORMAL_BULLET_SPEED, strength, angle, target, Config.NORMAL_BULLET_PATH);
    }
}
