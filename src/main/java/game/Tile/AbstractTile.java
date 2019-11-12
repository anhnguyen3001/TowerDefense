package game.Tile;

import game.AbstractEntity;
import game.Config;
import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractTile extends AbstractEntity {
    public AbstractTile(double x, double y, String IMG_PATH) {
        super(x, y, IMG_PATH);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), getX() * Config.SIZE_TILE, getY() * Config.SIZE_TILE);
    }
}
