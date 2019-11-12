package game.Enemy;

import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TankerEnemy extends AbstractEnemy{
    public TankerEnemy(ArrayList<Double> wayPoint, double x, double y){
        super(wayPoint, x, y, Config.TANKER_E_Blood, Config.TANKER_E_Speed,
                Config.TANKER_E_Armor, Config.TANKER_E_Reward, Config.TANKER_E_PATH);
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
            double ratio = getHealth() / (double)Config.TANKER_E_Blood;

            gc.setFill(Color.GREEN);
            gc.fillRect(x * sizeTile, y * sizeTile, widthBar * ratio, heightBar);
        }
    }
}
