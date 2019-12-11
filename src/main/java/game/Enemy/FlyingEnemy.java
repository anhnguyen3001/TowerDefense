package game.Enemy;

import game.Config;
import game.helper.Asset;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class FlyingEnemy extends AbstractEnemy{
    private Image shadow_img;

    public FlyingEnemy(ArrayList<Double> wayPoint, double x, double y, int health, int priority){
        super(wayPoint, x, y, health, Config.FLYING_E_Speed, Config.FLYING_E_Armor, Config.FLYING_E_Reward, priority,
                Asset.FLYING_ENEMY);
        shadow_img = Asset.SHADOW_FLYING_ENEMY;
    }


    @Override
    public void renderHealthBar(GraphicsContext gc) {
        double x = getX() + 0.25;
        double y = getY();
        double sizeTile = Config.SIZE_TILE;
        double widthBar = sizeTile*0.5;
        double heightBar = sizeTile*0.1;
        gc.setFill(Color.RED);
        gc.fillRect(x * sizeTile, y * sizeTile, widthBar, heightBar);

        //RealBlood
        if (getHealth() > 0) {
            double ratio = getHealth() / (double)Config.FLYING_E_Blood;

            gc.setFill(Color.GREEN);
            gc.fillRect(x * sizeTile, y * sizeTile, widthBar * ratio, heightBar);
        }
    }

    public void render(GraphicsContext gc){
        super.render(gc);

        //Draw Shadow
        double x = getX();
        double y = getY();

        int direction = getDirection();
        if (direction == Config.DOWN) y -= 0.15;
        else if (direction == Config.UP) y += 0.15;
        else if (direction == Config.RIGHT) x -= 0.15;
        else x += 0.15;

        int sizeTile = Config.SIZE_TILE;
        int degree = 90 * direction;

        ImageView view = new ImageView(shadow_img);
        view.setRotate(degree);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Image img = view.snapshot(params, null);
        gc.drawImage(img, x * sizeTile, y * sizeTile);
    }

    public String toString(){
        return "FlyingEnemy " + super.toString();
    }
}
