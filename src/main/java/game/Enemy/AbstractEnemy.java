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
    private final int armor;
    private final int reward;
    private int direction;
    private ArrayList<Double> wayPoint;
    private int wayPointIndex;
    private double distancedTravelled;

    public AbstractEnemy(ArrayList<Double> wayPoint, double x, double y, int health, double speed, int armor, int reward, String IMG_PATH) {
        super(x, y, IMG_PATH);
        this.health = health;
        this.speed = speed;
        this.armor = armor;
        this.reward = reward;
        distancedTravelled = 0;
        this.wayPoint = wayPoint;
        wayPointIndex = 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public double getDistancedTravelled() {
        return distancedTravelled;
    }

    public int getDirection() {
        return direction;
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

    public void calculateDirection() {
        if (wayPointIndex == wayPoint.size()) {
            direction = Config.END_OF_ROAD;
            return;
        }

        double nPointX = wayPoint.get(wayPointIndex);
        double nPointY = wayPoint.get(wayPointIndex + 1);
        double distance = AbstractEntity.evaluateDistance(getX(), getY(), nPointX, nPointY);
        if (distance <= speed) {
            setX(nPointX);
            setY(nPointY);

            wayPointIndex += 2;
            if (wayPointIndex == wayPoint.size()) {
                direction = Config.END_OF_ROAD;
                return;
            }

            nPointX = wayPoint.get(wayPointIndex);
            nPointY = wayPoint.get(wayPointIndex + 1);
            double curPosX = getX();
            double curPosY = getY();

            if (nPointX - curPosX > 0) direction = Config.RIGHT;
            else if (nPointX - curPosX < 0) direction = Config.LEFT;
            else if (nPointY - curPosY < 0) direction = Config.UP;
            else direction = Config.DOWN;
        }
    }

    @Override
    public boolean isDestroyed() {
        return (health <= 0 || wayPointIndex == wayPoint.size());
    }

    public void update(GameField field){
        calculateDirection();

        if (direction != Config.END_OF_ROAD){
            distancedTravelled += speed;

            double posX = getX();
            double posY = getY();

            if (direction == Config.UP) setY(posY - speed);
            else if (direction == Config.DOWN) setY(posY + speed);
            else if (direction == Config.RIGHT) setX(posX + speed);
            else setX(posX - speed);
        }
    }

    public double getSpeed() {
        return speed;
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
}
