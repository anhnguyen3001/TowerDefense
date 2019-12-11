package game.Bullet;

import game.Config;
import game.Enemy.AbstractEnemy;

public class RocketBullet extends AbstractBullet {
    public RocketBullet(double x, double y, AbstractEnemy target, double angle, double strength){
        super(x, y, Config.ROCKET_BULLET_SPEED, strength, angle, target, Config.ROCKET_BULLET_PATH);
    }
}
