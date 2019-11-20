package game.Tile.Tower;

import game.AbstractEntity;
import game.Bullet.AbstractBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.GameField;
import game.Tile.AbstractTile;
import game.Tile.UpdateEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class AbstractTower extends AbstractTile implements UpdateEntity {
    private final int maxLevel = 3;
    private int level;
    private double range;
    private int fireRate;
    private int damage;
    private int buyCost;
    private int tick;
    private double angleRotation;               	//goc xoay thap
    private boolean hasClicked;

    public AbstractTower(double x, double y, double range, int fireRate, int damage, int buyCost, String IMG_PATH) {
        super(x, y, IMG_PATH);
        this.range = range;
        this.fireRate = fireRate;
        this.damage = damage;
        this.buyCost = buyCost;
        tick = 0;
        angleRotation = 0;
        level = 1;
        hasClicked = false;
    }

    public void upgrade(){
        level++;
        buyCost *= 2;
        damage += 5 * (level - 1);
        fireRate -= 3;
        range += 0.3 * (level - 1);
    }

    public boolean canUpgrade(){
        return (level < maxLevel);
    }

    public double getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getUpgradeDamage(){
        return (damage + 5 * (level - 1));
    }

    public double getUpgardeRange(){
        return (range + 0.3 * (level - 1));
    }

    public int getUpgradeCost(){
        return (buyCost*2);
    }

    public int getSellValue(){
        return buyCost-5;
    }

    public int getLevel() {
        return level;
    }

    public double getAngleRotation(){
        return angleRotation;
    }

    public boolean isHasClicked() {
        return hasClicked;
    }

    public void setHasClicked(boolean hasClicked) {
        this.hasClicked = hasClicked;
    }

    public boolean isEnemyInRange(AbstractEnemy enemy) {
        double enemyCenterX = enemy.getX() + 0.5;
        double enemyCenterY = enemy.getY() + 0.5;

        double towerCenterX = getX() + 0.5;
        double towerCenterY = getY() + 0.5;

        return (AbstractEntity.evaluateDistance(enemyCenterX, enemyCenterY, towerCenterX, towerCenterY) <= range + 1/3);
    }

    public AbstractEnemy chooseEnemy(ArrayList<AbstractEnemy> enemies){
        double maxDistanceTravelled = -1;
        AbstractEnemy target = null;

        for (AbstractEnemy enemy : enemies){
            double tempDistanceTravelled = enemy.getDistancedTravelled();
            if (target == null || tempDistanceTravelled > maxDistanceTravelled
                    ||(tempDistanceTravelled == maxDistanceTravelled && target.getHealth() < enemy.getHealth())){
                target = enemy;
                maxDistanceTravelled = tempDistanceTravelled;
            }
        }

        return target;
    }

    public void calculateAngle(AbstractEnemy enemy) {
        double targetX = enemy.getX();
        double targetY = enemy.getY();
        double xDistance = targetX - getX();
        double yDistance = targetY - getY();
        angleRotation = Math.toDegrees(Math.atan2(yDistance, xDistance)) + 90;
    }

    @Override
    public void update(GameField field) {
        tick--;
        ArrayList<AbstractEnemy> enemies = field.getEnemies();

        ArrayList<AbstractEnemy> enemiesInRange = new ArrayList<>();
        for(AbstractEnemy enemy:enemies) if(isEnemyInRange(enemy)) enemiesInRange.add(enemy);

        if (enemiesInRange.size() != 0) {
            AbstractEnemy target = chooseEnemy(enemiesInRange);
            calculateAngle(target);
            if (tick <= 0) {
                field.addSpawnList(spawnBullet(getX(), getY(), target));
                tick = fireRate;
            }
        }
    }

    public abstract <T extends AbstractBullet> T spawnBullet(double x, double y, AbstractEnemy enemy);

    public void render(GraphicsContext gc){
        double tileSize = Config.SIZE_TILE;
        double centerX = getX() + 0.5;
        double centerY = getY() + 0.5;

        gc.save();
        gc.translate(centerX * tileSize, centerY * tileSize);
        gc.rotate(getAngleRotation());
        gc.drawImage(getImage(), -getSize()/2, -getSize()/2);
        gc.restore();

        if (hasClicked) renderIfClick(gc);
    }

    public void renderIfClick(GraphicsContext gc){
        double tileSize = Config.SIZE_TILE;
        double topRangeX = getX() + 0.5 - range;
        double topRangeY = getY() + 0.5 - range;
        gc.setFill(Color.rgb(200, 200, 200, 0.3));
        gc.fillOval(topRangeX * tileSize, topRangeY * tileSize, 2 * range * tileSize, 2 * range * tileSize);
        new TowerInfo().render(gc, this);
    }
}
