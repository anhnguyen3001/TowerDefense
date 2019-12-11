package game.Bullet;

import game.Config;
import game.Enemy.AbstractEnemy;

public class FreezeBullet extends AbstractBullet{
    private int delayDamage;
    private double delayRatio;

    public FreezeBullet(double x, double y, AbstractEnemy target, double angle, double strength, int delayDamage, double delayRatio){
        super(x, y, Config.FREEZE_BULLET_SPEED, strength, angle, target, Config.FREEZE_BULLET_PATH);
        this.delayDamage = delayDamage;
        this.delayRatio = delayRatio;
    }

    public void doDamage(){
        super.doDamage();

        AbstractEnemy target = getTarget();
        target.setDelayTime(delayDamage);
        target.setDelayRatio(delayRatio);
        System.out.println(delayRatio);
    }
}
