package game.Tile.Tower;

import game.Bullet.FreezeBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.helper.Asset;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class FreezeTower extends AbstractTower{
    private int delayDamage;
    private double delayRatio;
    private Image baseIMG;

    public FreezeTower(double x, double y, int tick, int level){
        super(x, y, Config.FREEZE_T_Range, Config.FREEZE_T_Speed, Config.FREEZE_T_DAMAGE,
                Config.FREEZE_T_Cost, tick, level, Asset.FREEZE_TOWER);
        delayDamage = Config.DELAY_DAMAGE;
        delayRatio = Config.DELAY_RATIO;
        baseIMG = new Image(Config.BASE_FREEZE_T_PATH);
    }

    public void updateInfo(){
        super.updateInfo();
        if (getLevel() != 1){
            delayDamage = getUpgradeDelayDamage();
            delayRatio = upgradeDelayRatio();
        }
    }

    public int getDelayDamage(){
        return delayDamage;
    }

    public int getUpgradeDelayDamage(){
        return delayDamage + 5;
    }

    public double upgradeDelayRatio(){
        return delayRatio *1.2;
    }

    @Override
    public FreezeBullet spawnBullet(double x, double y, AbstractEnemy enemy) {
        return new FreezeBullet(x, y, enemy, getAngleRotation(), getDamage(), delayDamage, delayRatio);
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
        return "FreezeTower " + super.toString();
    }
}
