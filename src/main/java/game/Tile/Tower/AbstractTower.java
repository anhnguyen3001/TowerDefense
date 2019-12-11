package game.Tile.Tower;

import game.AbstractEntity;
import game.Bullet.AbstractBullet;
import game.Config;
import game.Enemy.AbstractEnemy;
import game.GameField;
import game.Tile.AbstractTile;
import game.Tile.UpdateEntity;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    private boolean isReadyShoot;
    private AbstractEnemy target;
    protected EventHandler<MouseEvent> towerClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            int tileSize = Config.SIZE_TILE;
            if (event.getX() >= getX() * tileSize && event.getX() <= (getX() + 1) * tileSize
                    && event.getY() >= getY() * tileSize && event.getY() <= (getY() + 1) * tileSize){
                hasClicked = !hasClicked;
            }
        }
    };

    public AbstractTower(double x, double y, double range, int fireRate, int damage,
                         int buyCost, int tick, int level, Image img) {
        super(x, y, img);
        this.tick = tick;
        this.level = level;
        this.range = range;
        this.fireRate = fireRate;
        this.damage = damage;
        this.buyCost = buyCost;
        updateInfo();
        angleRotation = 0;
        hasClicked = false;
        isReadyShoot = true;
        target = null;
    }

    protected void updateInfo(){
        if (level != 1) {
            --level;
            buyCost = getUpgradeCost();
            damage = getUpgradeDamage();
            fireRate -= 3;
            range = getUpgardeRange();
            ++level;
        }
    }

    public void upgrade(){
        updateInfo();
        level++;
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
        return (damage + 5 * level);
    }

    public double getUpgardeRange(){
        return (range + 0.3 * level);
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

    public boolean isReadyShoot(){
        return (isReadyShoot);
    }

    protected boolean isEnemyInRange(AbstractEnemy enemy) {
        double enemyCenterX = enemy.getX() + 0.5;
        double enemyCenterY = enemy.getY() + 0.5;

        double towerCenterX = getX() + 0.5;
        double towerCenterY = getY() + 0.5;

        return (AbstractEntity.evaluateDistance(enemyCenterX, enemyCenterY, towerCenterX, towerCenterY) <= range);
    }

    protected AbstractEnemy chooseEnemy(ArrayList<AbstractEnemy> enemies){
        double x = getX();
        double y = getY();
        double minDistance = Double.MAX_VALUE;
        for (AbstractEnemy enemy : enemies){
            double distance = AbstractEntity.evaluateDistance(x, y, enemy.getX(), enemy.getY());
            if (minDistance > distance || (minDistance == distance && target.getHealth() > enemy.getHealth())){
                target = enemy;
                minDistance = distance;
            }
        }

        return target;
    }

    private void calculateAngle(AbstractEnemy enemy) {
        double xDistance = enemy.getX() - getX();
        double yDistance = enemy.getY() - getY();
        angleRotation = Math.toDegrees(Math.atan2(yDistance, xDistance)) + 90;
    }

    protected ArrayList<AbstractEnemy> findListTarget(ArrayList<AbstractEnemy> enemies){
        ArrayList<AbstractEnemy> enemiesInRange = new ArrayList<>();
        for (AbstractEnemy enemy : enemies) if (isEnemyInRange(enemy)) enemiesInRange.add(enemy);
        return enemiesInRange;
    }

    @Override
    public void update(GameField field) {
        if (--tick <= 0) isReadyShoot = true;

        if (target == null || !isEnemyInRange(target) || target.isDestroyed()) {
            target = null;
            ArrayList<AbstractEnemy> enemiesInRange = findListTarget(field.getEnemies());

            if (enemiesInRange.size() != 0)
                target = chooseEnemy(enemiesInRange);
        }

        if (target != null) {
            calculateAngle(target);
            if (isReadyShoot) {
                field.addSpawnList(spawnBullet(getX(), getY(), target));
                isReadyShoot = false;
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
        gc.drawImage(getImage(), -tileSize/2, -tileSize/2);
        gc.restore();
        gc.getCanvas().setOnMouseClicked(towerClick);
        if (hasClicked) renderIfClick(gc);
    }

    public void renderIfClick(GraphicsContext gc){
        double tileSize = Config.SIZE_TILE;
        double topRangeX = getX() + 0.5 - range;
        double topRangeY = getY() + 0.5 - range;
        gc.setFill(Color.rgb(200, 200, 200, 0.3));
        gc.fillOval(topRangeX * tileSize, topRangeY * tileSize, 2 * range * tileSize, 2 * range * tileSize);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(topRangeX * tileSize, topRangeY * tileSize, 2 * range * tileSize, 2 * range * tileSize);
        TowerInfo.render(gc, this);
    }

    public String toString(){
        String res = super.toString() + Integer.toString(tick) + " " + Integer.toString(level) + "\n";
        return res;
    }
}
