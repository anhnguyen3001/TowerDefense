package game.Tile;

import game.Config;
import game.helper.Asset;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Mountain extends AbstractTile {
    private int id;
    private Image pattern;

    public Mountain(double x, double y, int id) {
        super(x, y, Asset.getBaseImage(Config.land));
        this.id = id;
        pattern = Asset.getBaseImage(id);
    }

    public int getId() {
        return id;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        double sizeTile = Config.SIZE_TILE;
        gc.drawImage(pattern, getX() * sizeTile, sizeTile * getY(), sizeTile, sizeTile);
    }
}
