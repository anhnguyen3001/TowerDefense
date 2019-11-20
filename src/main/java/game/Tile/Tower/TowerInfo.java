package game.Tile.Tower;

import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TowerInfo {
    public void render(GraphicsContext gc, AbstractTower tower){
        int SIZE_TILE = Config.SIZE_TILE;
        int width = Config.SCREEN_WIDTH;
        int height = Config.SCREEN_HEIGHT;

        double startX = 16.1 * SIZE_TILE;
        double startY =  3.7 * SIZE_TILE;

        //Current Tower Info
        String damage = Integer.toString(tower.getDamage());
        String range = Double.toString(tower.getRange());
        String level = Integer.toString(tower.getLevel());

        gc.strokeRect(startX, startY, width - startX - 0.1 *SIZE_TILE, height * 0.5);
        gc.setStroke(Color.BLACK);

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.JUSTIFY);
        gc.setFont(Font.font("Roboto Mono", 18));

        gc.fillText("Level: ", startX + 0.1 * SIZE_TILE, startY + 0.5 * SIZE_TILE);
        gc.fillText("Range: ", startX + 0.1 * SIZE_TILE, startY +  1 * SIZE_TILE);
        gc.fillText("Damage: ", startX + 0.1 * SIZE_TILE, startY + 1.5 * SIZE_TILE);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(level, width - SIZE_TILE * 0.2, startY + 0.5 * SIZE_TILE);
        gc.fillText(range, width - SIZE_TILE * 0.2, startY +  1 * SIZE_TILE);
        gc.fillText(damage, width - SIZE_TILE * 0.2, startY + 1.5 * SIZE_TILE);

        gc.setTextAlign(TextAlignment.LEFT);

        //Upgrade Tower Info
        if (tower.canUpgrade()) {
            gc.strokeLine(startX + 0.2 * SIZE_TILE, startY + 2 * SIZE_TILE,
                    width - 0.3 * SIZE_TILE, startY + 2 * SIZE_TILE);

            damage = Integer.toString(tower.getUpgradeDamage());
            range = Double.toString(tower.getUpgardeRange());
            String price = Integer.toString(tower.getUpgradeCost());

            gc.fillText("Upgrade", startX + 0.8 * SIZE_TILE, startY + 2.5 * SIZE_TILE);

            gc.fillText("Range: ", startX + 0.1 * SIZE_TILE, startY + 3 * SIZE_TILE);
            gc.fillText("Damage: ", startX + 0.1 * SIZE_TILE, startY + 3.5 * SIZE_TILE);
            gc.fillText("Price: ", startX + 0.1 * SIZE_TILE, startY + 4 * SIZE_TILE);

            gc.setTextAlign(TextAlignment.RIGHT);
            gc.fillText(price, width - SIZE_TILE * 0.2, startY + 4 * SIZE_TILE);
            gc.fillText(damage, width - SIZE_TILE * 0.2, startY + 3 * SIZE_TILE);
            gc.fillText(range, width - SIZE_TILE * 0.2, startY + 3.5 * SIZE_TILE);

            gc.setTextAlign(TextAlignment.LEFT);
        }

        //Sell Value
        String sellValue = Integer.toString(tower.getSellValue());
        gc.fillText("Sell\nValue: ", startX + 0.1 * SIZE_TILE, startY + 5 * SIZE_TILE);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(sellValue, width - SIZE_TILE * 0.2, startY + 5.2 * SIZE_TILE);
    }
}
