package game.Enemy;

import game.*;
import game.Tile.UpdateEntity;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class AbstractEnemy extends AbstractEntity implements DestroyEntity, UpdateEntity {
    private int health;
    private double speed;
    private int delayTime;
    private double delayRatio;
    private final int armor;
    private final int reward;
    private int direction;
    private ArrayList<Double> wayPoint;
    private int wayPointIndex;
    private double distancedTravelled;
    private int priority;
    private boolean reachedWaypoint;

    public AbstractEnemy(ArrayList<Double> wayPoint, double x, double y, int health, double speed, int armor, int reward, int priority, Image img) {
        super(x, y, img);
        this.health = health;
        this.speed = speed;
        this.armor = armor;
        this.reward = reward;
        distancedTravelled = 0;
        this.wayPoint = wayPoint;
        calculateWaypointIndex();
        delayTime = 0;
        delayRatio = 0;
        this.priority = priority;
        reachedWaypoint = false;
    }

    private void calculateWaypointIndex(){
        for (int i = 0; i < wayPoint.size() - 1; i+=2)
            if (wayPoint.get(i).equals(getX()) || wayPoint.get(i+1).equals(getY())){
                if (wayPoint.get(i).equals(getX())){
                    distancedTravelled += Math.abs(wayPoint.get(i+1) - getY());
                } else distancedTravelled += Math.abs(wayPoint.get(i) - getX());
                wayPointIndex = i+2;
                break;
            } else if (i != 0){
                distancedTravelled += AbstractEntity.evaluateDistance(wayPoint.get(i), wayPoint.get(i+1), wayPoint.get(i-2), wayPoint.get(i-1));
            }

        if (wayPoint.get(wayPointIndex).equals(getX())){
            if (wayPoint.get(wayPointIndex + 1).compareTo(getY()) > 0)
                direction = Config.DOWN;
            else direction = Config.UP;
        } else {
            if (wayPoint.get(wayPointIndex).compareTo(getX()) > 0)
                direction = Config.RIGHT;
            else direction = Config.LEFT;
        }
    }

    public int getHealth() {
        return health;
    }

    public double getDistancedTravelled() {
        return distancedTravelled;
    }

    public int getReward(){
        return reward;
    }

    public void onEffect(double damage) {
        health -= (damage - armor);
    }

    public boolean isAlive() {
        return (health > 0);
    }

    public double getSpeed() {
        return speed;
    }

    public int getDirection(){
        return direction;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayRatio(double delayRatio) {
        this.delayRatio = delayRatio;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getPriority() {
        return priority;
    }

    public String getWayPointXY(){
        if (wayPointIndex == wayPoint.size()) return  "";
        return (Double.toString(wayPoint.get(wayPointIndex)) + " " + Double.toString(wayPoint.get(wayPointIndex+1)));
    }

    public ArrayList<Double> getWayPoint(){
        return wayPoint;
    }

    public void calculateDirection() {
        if (wayPointIndex == wayPoint.size()) return;

        double nPointX = wayPoint.get(wayPointIndex);
        double nPointY = wayPoint.get(wayPointIndex + 1);
        double distance = AbstractEntity.evaluateDistance(getX(), getY(), nPointX, nPointY);

        if (distance <= speed * (1-delayRatio)) {
            reachedWaypoint = true;
            wayPointIndex += 2;
            if (wayPointIndex == wayPoint.size()) return;

            double curPosX = nPointX;
            double curPosY = nPointY;
            nPointX = wayPoint.get(wayPointIndex);
            nPointY = wayPoint.get(wayPointIndex + 1);

            if (nPointX - curPosX > 0) direction = Config.RIGHT;
            else if (nPointX - curPosX < 0) direction = Config.LEFT;
            else if (nPointY - curPosY < 0) direction = Config.UP;
            else direction = Config.DOWN;
        }
    }

    @Override
    public boolean isDestroyed() {
        return (!isAlive() || wayPointIndex == wayPoint.size());
    }

    public void update(GameField field){
        if (delayTime > 0) delayTime--;
        else delayRatio = 0;

        calculateDirection();

        double posX = getX();
        double posY = getY();
        double wayPointX = wayPoint.get(wayPointIndex - 2);
        double wayPointY = wayPoint.get(wayPointIndex - 1);
        if (reachedWaypoint){
            posX = wayPointX;
            posY = wayPointY;
        }

        if (direction == Config.UP) posY -= speed * (1-delayRatio);
        else if (direction == Config.DOWN) posY += speed * (1-delayRatio);
        else if (direction == Config.RIGHT) posX += speed * (1-delayRatio);
        else posX -= speed * (1-delayRatio);

        if (delayTime == 0) {
            ArrayList<game.Enemy.AbstractEnemy> enemies = field.getEnemies();

            for (game.Enemy.AbstractEnemy enemy : enemies) {
                if (this.getClass().equals(enemy.getClass()) && !this.equals(enemy)
                        && enemy.getDelayTime() == 0 && getWayPointXY().equals(enemy.getWayPointXY())) {
                    double distance = AbstractEntity.evaluateDistance(enemy.getX(), enemy.getY(), posX, posY);

                    if (enemy.getDelayTime() != 0 && ((enemy.getDirection() == direction && distance <= 3 * speed)
                            || (Math.abs(direction - enemy.getDirection()) == 2 && distance <= 0.5 && priority > enemy.getPriority()))){
                        if (reachedWaypoint){
                            wayPointIndex-=2;
                            reachedWaypoint = false;
                        }
                        return;
                    }
                }
            }
        }

        if (reachedWaypoint) reachedWaypoint = false;

        distancedTravelled += (Math.abs(getX() - posX) + (Math.abs(getY() - posY)));
        setX(posX);
        setY(posY);
    }

    public void render(GraphicsContext gc){
        double x = getX();
        double y = getY();
        int sizeTile = Config.SIZE_TILE;
        int degree = 90 * direction;
        ImageView view = new ImageView(getImage());
        view.setRotate(degree);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image img = view.snapshot(params,null);
        gc.drawImage(img, x * sizeTile, y * sizeTile);

        renderHealthBar(gc);
    }

    public abstract void renderHealthBar(GraphicsContext gc);

    public String toString(){
        int size = wayPoint.size();
        String res = Double.toString(wayPoint.get(0)) + " " + Double.toString(wayPoint.get(1)) + " "
                + Double.toString(wayPoint.get(size - 2)) + " " + Double.toString(wayPoint.get(size - 1)) + " "
                + super.toString() + Integer.toString(health) + "\n";
        return res;
    }
}
