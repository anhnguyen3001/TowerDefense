package game.Bullet;

import game.Config;
import game.Enemy.AbstractEnemy;

public class MachineGunBullet extends AbstractBullet{
    public MachineGunBullet(double x, double y, AbstractEnemy target, double angle, double strength){
        super(x, y, Config.MACHINEGUN_BULLET_SPEED, strength, angle, target, Config.MACHINE_BULLET_PATH);
    }
}
