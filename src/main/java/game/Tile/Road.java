package game.Tile;

import game.Config;

public class Road extends AbstractTile {
    public Road(double x, double y) {
        super(x, y, Config.ROAD_PATH);
    }
}
