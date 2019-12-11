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
                double x = 16.1 * SIZE_TILE;
                double y = 4.5 * SIZE_TILE;
                range *= SIZE_TILE;

                gc.strokeRect(x, y, width - x - 0.1 *SIZE_TILE, SIZE_TILE * 3.9);
                gc.setStroke(Color.BLACK);
                gc.setFill(Color.rgb(250, 250, 250, 0.8));
                gc.fillRect(x, y, width - x - 0.1 * SIZE_TILE, SIZE_TILE * 3.9);

                gc.setFill(Color.BLACK);
                gc.setFont(Font.font("Roboto Mono", 18));

                double leftAlign = x + SIZE_TILE*0.2;
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText("Fire Rate: " , leftAlign, y + SIZE_TILE * 0.8);
                gc.fillText("Damage: ", leftAlign, y + SIZE_TILE * 1.5);
                gc.fillText("Range: ", leftAlign, y + SIZE_TILE * 2.3);
                gc.fillText("Price: ", leftAlign, y + SIZE_TILE * 3.1);

                double rightAlign = width - SIZE_TILE * 0.2;
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(Integer.toString(fireRate), rightAlign, y + 0.8 * SIZE_TILE);
                gc.fillText(Integer.toString(damage), rightAlign, y + 1.5 *SIZE_TILE);
                gc.fillText(Double.toString(range), rightAlign, y + 2.3 * SIZE_TILE);
                gc.fillText(Integer.toString(price), rightAlign, y + 3.1 * SIZE_TILE);

                gc.setTextAlign(TextAlignment.LEFT);
        }
}
