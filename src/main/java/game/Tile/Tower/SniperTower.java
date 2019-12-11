package game.Tile.Tower;

import game.Bullet.SniperBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.Enemy.FlyingEnemy;
import game.helper.Asset;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SniperTower extends AbstractTower{
    private Image baseIMG;
    public SniperTower(double x, double y, int tick, int level){
        super(x, y, Config.SNIPER_T_Range, Config.SNIPER_T_Speed, Config.SNIPER_T_DAMAGE, Config.SNIPER_T_Cost,
                tick, level, Asset.SNIPER_TOWER);
        baseIMG = new Image(Config.BASE_SNIPER_T_PATH);
    }

    @Override
    protected ArrayList<AbstractEnemy> findListTarget(ArrayList<AbstractEnemy> enemies) {
        ArrayList<AbstractEnemy> enemyInRange = new ArrayList<>();
        for (AbstractEnemy enemy : enemies)
            if (enemy instanceof FlyingEnemy &&isEnemyInRange(enemy)) enemyInRange.add(enemy);
        return enemyInRange;
    }

    @Override
    public SniperBullet spawnBullet(double x, double y, AbstractEnemy enemy) {
        return new SniperBullet(x, y, enemy, getAngleRotation(), getDamage());
    }

    public void render(GraphicsContext gc){
        double tileSize = Config.SIZE_TILE;
        gc.drawImage(baseIMG, getX() * tileSize, getY() * tileSize);

        double angle = getAngleRotation();
        Image gunIMG = getImage();
        if (angle != 0) {
            //Rotate GunIMG
            ImageView view = new ImageView(getImage());
            view.setRotate(-90);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            gunIMG = view.snapshot(params, null);
        }

        //Draw Gun
        double centerX = getX() + 0.5;
        double centerY = getY() + 0.5;

        gc.save();
        gc.translate(centerX * tileSize, centerY * tileSize);
        gc.rotate(getAngleRotation());
        gc.drawImage(gunIMG, -tileSize/2, -tileSize/2);
        gc.restore();

        gc.getCanvas().setOnMouseClicked(towerClick);
        if (isHasClicked()) renderIfClick(gc);
    }

    public String toString(){
        return "SniperTower " + super.toString();
    }
}
