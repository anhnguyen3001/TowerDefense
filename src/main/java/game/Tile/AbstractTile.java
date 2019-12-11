package game.Tile;

import game.AbstractEntity;
import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractTile extends AbstractEntity {
    public AbstractTile(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), getX() * Config.SIZE_TILE, getY() * Config.SIZE_TILE);
    }
}
