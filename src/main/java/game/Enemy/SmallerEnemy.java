package game.Enemy;

import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SmallerEnemy extends AbstractEnemy{
    public SmallerEnemy(ArrayList<Double> wayPoint, double x, double y){
        super(wayPoint, x, y, Config.SMALLER_E_Blood, Config.SMALLER_E_Speed,
                Config.SMALLER_E_Armor, Config.SMALLER_E_Reward, Config.SMALLER_E_PATH);
    }

    @Override
    public void renderHealthBar(GraphicsContext gc) {
        double x = getX() + 0.25;
        double y = getY();
        double sizeTile = Config.SIZE_TILE;
        double widthBar = getSize()*0.5;
        double heightBar = getSize()*0.1;
        gc.setFill(Color.RED);
        gc.fillRect(x * sizeTile, y * sizeTile, widthBar, heightBar);

        //RealBlood
        if (getHealth() > 0) {
            double ratio = getHealth() / (double)Config.SMALLER_E_Blood;

            gc.setFill(Color.GREEN);
            gc.fillRect(x * sizeTile, y * sizeTile, widthBar * ratio, heightBar);
        }
    }
}
