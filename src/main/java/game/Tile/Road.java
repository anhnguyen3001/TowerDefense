package game.Tile;

import game.helper.Asset;
import javafx.scene.canvas.GraphicsContext;
public class Road extends AbstractTile {
    private int id;

    public Road(double x, double y, int bit) {
        super(x, y, Asset.getBaseImage(bit));
        this.id = bit;
    }

    public int getId() {
        return id;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
