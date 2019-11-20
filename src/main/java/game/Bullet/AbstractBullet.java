package game.Bullet;

import game.*;
import game.Enemy.AbstractEnemy;
import game.Tile.UpdateEntity;
import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractBullet extends AbstractEntity implements UpdateEntity, DestroyEntity, EffectEntity{
    private AbstractEnemy target;
    private double strength;
    private double speed;
    private double angle;

    public AbstractBullet(double x, double y, double speed, double strength, double angle, AbstractEnemy target, String bulletPath){
        super(x, y, bulletPath);
        this.target = target;
        this.strength = strength;
        this.speed = speed;
        calculate();
    }

    public void calculate(){
        double distanceX = target.getX() - getX();
        double distanceY = target.getY() - getY();

        if (target.getDirection() == Config.LEFT) distanceX -= target.getSpeed();
        else if (target.getDirection() == Config.RIGHT) distanceX += target.getSpeed();
        else if (target.getDirection() == Config.UP) distanceY -= target.getSpeed();
        else distanceY += target.getSpeed();

        angle = Math.toDegrees(Math.atan2(distanceY, distanceX)) + 90;
    }

    @Override
    public void update(GameField field) {
        calculate();
        double angleToRadian = Math.toRadians(angle - 90);
        double distanceFromBullet = AbstractEntity.evaluateDistance(target.getX(), target.getY(), getX(), getY());
        if (distanceFromBullet < speed){
            int direction = target.getDirection();
            if (direction == Config.UP || direction == Config.DOWN) {
                setY(Math.tan(angleToRadian) * (target.getX() - getX()));
                setX(target.getX());
            }
            else {
                setX(Math.tan(angleToRadian) * (target.getY() - getY()));
                setY(target.getY());
            }
            /**setX(getX() + Math.cos(angleToRadian) * distanceFromBullet);
            setY(getY() + Math.sin(angleToRadian) * distanceFromBullet);**/
        } else {
            setX(getX() + Math.cos(angleToRadian) * speed);
            setY(getY() + Math.sin(angleToRadian) * speed);
        }
    }

    public boolean checkCollision(){
        return (AbstractEntity.evaluateDistance(target.getX(), target.getY(), getX(), getY()) <= (double)2/3);
    }

    public void doDamage(){
        if (checkCollision()) target.onEffect(strength);
    }

    public boolean isDestroyed() {
        return checkCollision();
    }

    public void render(GraphicsContext gc){
        double size = getSize();
        double tileSize = Config.SIZE_TILE;

        gc.save();
        gc.translate((getX() + 0.5) * tileSize, (getY() + 0.5) * tileSize);
        gc.rotate(angle);

        gc.drawImage(getImage(), -size/2, -size/2, size, size);
        gc.restore();
    }
}
