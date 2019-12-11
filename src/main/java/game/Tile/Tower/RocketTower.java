package game.Tile.Tower;

import game.Bullet.RocketBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.Enemy.FlyingEnemy;
import game.helper.Asset;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class RocketTower extends AbstractTower{
    private Image rocketIMG;
    public RocketTower(double x, double y, int tick, int level){
        super(x, y, Config.ROCKET_T_Range, Config.ROCKET_T_Speed, Config.ROCKET_T_DAMAGE,
                Config.ROCKET_T_Cost, tick, level, Asset.ROCKET_TOWER);
        rocketIMG = Asset.FULL_ROCKET_TOWER;
    }
    public RocketBullet spawnBullet(double x, double y, AbstractEnemy enemy){
        return new RocketBullet(x, y, enemy, getAngleRotation(), getDamage());
    }

    @Override
    protected ArrayList<AbstractEnemy> findListTarget(ArrayList<AbstractEnemy> enemies) {
        ArrayList<AbstractEnemy> enemyInRange = new ArrayList<>();
        for (AbstractEnemy enemy : enemies)
            if (enemy instanceof FlyingEnemy &&isEnemyInRange(enemy)) enemyInRange.add(enemy);
        return enemyInRange;
    }

    public void render(GraphicsContext gc){
        super.render(gc);

        //Draw Rocket
        if (isReadyShoot()){
            double tileSize = Config.SIZE_TILE;
            double centerX = getX() + 0.5;
            double centerY = getY() + 0.5;

            gc.save();
            gc.translate(centerX * tileSize, centerY * tileSize);
            gc.rotate(getAngleRotation());
            gc.drawImage(rocketIMG, -tileSize/2, -tileSize/2);
            gc.restore();
        }
    }

    public String toString(){
        return "RocketTower " + super.toString();
    }
}
