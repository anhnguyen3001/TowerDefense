package game.Tile;

import game.Config;

public class Mountain extends AbstractTile {
    public Mountain(double x, double y, int id) {
        super(x, y, Config.PATH + Config.MAP_SERIES[id]);
    }
}
