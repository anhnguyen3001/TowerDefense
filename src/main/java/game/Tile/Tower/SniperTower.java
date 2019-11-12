package game.Tile.Tower;

import game.Bullet.SniperBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SniperTower extends AbstractTower{
    public SniperTower(double x, double y){
        super(x, y, Config.SNIPER_T_Range, Config.SNIPER_T_Speed, Config.SNIPER_T_DAMAGE, Config.SNIPER_T_Cost,
                Config.SNIPER_T_PATH);
    }

    @Override
    public SniperBullet spawnBullet(double x, double y, AbstractEnemy enemy) {
        return new SniperBullet(x, y, enemy, getAngleRotation(), getDamage());
    }

    public void render(GraphicsContext gc){
        Image baseIMG = new Image(Config.BASE_SNIPER_T_PATH);
        gc.drawImage(baseIMG, getX() * Config.SIZE_TILE, getY() * Config.SIZE_TILE);

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
        double tileSize = Config.SIZE_TILE;
        double centerX = getX() + 0.5;
        double centerY = getY() + 0.5;

        gc.save();
        gc.translate(centerX * tileSize, centerY * tileSize);
        gc.rotate(getAngleRotation());
        gc.drawImage(gunIMG, -getSize()/2, -getSize()/2);
        gc.restore();

        //Draw Range
        double range = getRange();
        double topRangeX = centerX - range;
        double topRangeY = centerY - range;
        gc.setFill(Color.rgb(200, 200, 200, 0.3));
        gc.fillOval(topRangeX * tileSize, topRangeY * tileSize, 2 * range * tileSize, 2 * range * tileSize);
    }
}
