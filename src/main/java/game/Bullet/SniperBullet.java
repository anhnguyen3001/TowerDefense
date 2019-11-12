package game.Bullet;

import game.Config;
import game.Enemy.AbstractEnemy;

public class SniperBullet extends AbstractBullet{
    public SniperBullet(double x, double y, AbstractEnemy target, double angle, double strength){
        super(x, y, Config.SNIPER_BULLET_SPEED, strength, angle, target, Config.SNIPER_BULLET_PATH);
    }
}
