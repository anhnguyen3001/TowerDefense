package game.Enemy;

import game.Config;
import game.helper.Asset;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TankerEnemy extends AbstractEnemy{
    public TankerEnemy(ArrayList<Double> wayPoint, double x, double y, int health, int priority){
        super(wayPoint, x, y, health, Config.TANKER_E_Speed, Config.TANKER_E_Armor, Config.TANKER_E_Reward, priority,
                Asset.TANKER_ENEMY);
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
            double ratio = getHealth() / (double)Config.TANKER_E_Blood;

            gc.setFill(Color.GREEN);
            gc.fillRect(x * sizeTile, y * sizeTile, widthBar * ratio, heightBar);
        }
    }

    public String toString(){
        return "TankerEnemy " + super.toString();
    }
}
