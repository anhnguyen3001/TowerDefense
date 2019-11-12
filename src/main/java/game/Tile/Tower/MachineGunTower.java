package game.Tile.Tower;

import game.Bullet.MachineGunBullet;
import game.Config;
import game.Enemy.AbstractEnemy;

public class MachineGunTower extends AbstractTower{
    public MachineGunTower(double x, double y){
        super(x, y, Config.MACHINEGUN_T_Range, Config.MACHINEGUN_T_Speed, Config.MACHINEGUN_T_DAMAGE, Config.MACHINEGUN_T_Cost,
                Config.MACHINE_GUN_T_PATH);
    }
     public MachineGunBullet spawnBullet(double x, double y, AbstractEnemy enemy){
        return new MachineGunBullet(x, y, enemy, getAngleRotation(), getDamage());
     }
}
