package game.Bullet;

import game.*;
import game.Enemy.AbstractEnemy;
import game.Tile.UpdateEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractBullet extends AbstractEntity implements UpdateEntity, DestroyEntity, EffectEntity{
    private AbstractEnemy target;
    private double strength;
    private double speed;
    private double angle;

    public AbstractBullet(double x, double y, double speed, double strength, double angle, AbstractEnemy target, String bulletPath){
        super(x, y, new Image(bulletPath));
        this.target = target;
        this.strength = strength;
        this.speed = speed;
        this.angle = angle;
    }

    public AbstractEnemy getTarget(){
        return target;
    }

    public void calculate(){
        angle = Math.toDegrees(Math.atan2(target.getY() - getY(), target.getX() - getX())) + 90;
    }

    @Override
    public void update(GameField field) {
        calculate();
        double angleToRadian = Math.toRadians(angle - 90);
        double distanceFromBullet = AbstractEntity.evaluateDistance(target.getX(), target.getY(), getX(), getY());
        if (distanceFromBullet < speed){
            setX(getX() + Math.cos(angleToRadian) * distanceFromBullet);
            setY(getY() + Math.sin(angleToRadian) * distanceFromBullet);
        }

        setX(getX() + Math.cos(angleToRadian) * speed);
        setY(getY() + Math.sin(angleToRadian) * speed);
    }

    public boolean checkCollision(){
        return (AbstractEntity.evaluateDistance(target.getX(), target.getY(), getX(), getY()) <= speed);
    }

    public void doDamage(){
        if (checkCollision()) target.onEffect(strength);
    }

    public boolean isDestroyed() {

        return checkCollision();
    }

    public void render(GraphicsContext gc){
        double tileSize = Config.SIZE_TILE;

        gc.save();
        gc.translate((getX() + 0.5) * tileSize, (getY() + 0.5) * tileSize);
        gc.rotate(angle);

        gc.drawImage(getImage(), -tileSize/2, -tileSize/2);
        gc.restore();
    }
}
