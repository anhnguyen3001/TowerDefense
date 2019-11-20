package game.Tile;

import game.Config;

public class Road extends AbstractTile {
    public Road(double x, double y, int id) {
        super(x, y, Config.PATH + Config.MAP_SERIES[id]);
    }
}
