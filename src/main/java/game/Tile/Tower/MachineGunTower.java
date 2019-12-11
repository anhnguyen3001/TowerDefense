package game.Tile.Tower;

import game.Bullet.MachineGunBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.helper.Asset;

public class MachineGunTower extends AbstractTower{
    public MachineGunTower(double x, double y, int tick, int level){
        super(x, y, Config.MACHINEGUN_T_Range, Config.MACHINEGUN_T_Speed, Config.MACHINEGUN_T_DAMAGE,
                Config.MACHINEGUN_T_Cost, tick, level, Asset.MACHINEGUNTOWER);
    }
    public MachineGunBullet spawnBullet(double x, double y, AbstractEnemy enemy){
        return new MachineGunBullet(x, y, enemy, getAngleRotation(), getDamage());
    }



    public String toString(){
        return "MachineGunTower " + super.toString();
    }
}
