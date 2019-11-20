package game.store;

import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class InfoTable {
    public void renderInfoTable(GraphicsContext gc, int fireRate, int damage, double range, int price){
        int SIZE_TILE = Config.SIZE_TILE;
        int width = Config.SCREEN_WIDTH;
        int height = Config.SCREEN_HEIGHT;
        double x = 16.1 * SIZE_TILE;
        double y = 3.7 * SIZE_TILE;
        range *= SIZE_TILE;

        gc.strokeRect(x, y, width - x - 0.1 *SIZE_TILE, height * 0.3);
        gc.setStroke(Color.BLACK);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Roboto Mono", 18));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Fire Rate: " , x + SIZE_TILE*0.2, y + SIZE_TILE*0.8);
        gc.fillText("Damage: ", x + SIZE_TILE*0.2, y + SIZE_TILE * 1.5);
        gc.fillText("Range: ", x + SIZE_TILE*0.2, y + SIZE_TILE * 2.3);
        gc.fillText("Price: ", x + SIZE_TILE*0.2, y + SIZE_TILE * 3.1);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(Integer.toString(fireRate), width - SIZE_TILE * 0.2, y + 0.8 * SIZE_TILE);
        gc.fillText(Integer.toString(damage), width - SIZE_TILE * 0.2, y + 1.5 *SIZE_TILE);
        gc.fillText(Double.toString(range), width - SIZE_TILE * 0.2, y + 2.3 * SIZE_TILE);
        gc.fillText(Integer.toString(price), width - SIZE_TILE * 0.2, y + 3.1 * SIZE_TILE);
    }
}
