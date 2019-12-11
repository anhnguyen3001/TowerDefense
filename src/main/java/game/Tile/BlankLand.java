package game.Tile;

import game.Config;
import game.Tile.Tower.AbstractTower;
import game.helper.Asset;

public class BlankLand extends AbstractTile{
    private int id;
    private boolean hasTower;
    private AbstractTower tower;
    public BlankLand(double x, double y, int id) {
        super(x, y, Asset.getBaseImage(Config.land));
        this.id = Config.land;
        hasTower = false;
        tower = null;
    }

    public void setHasTower(boolean hasTower) {
        this.hasTower = hasTower;
    }

    public void setTower(AbstractTower tower) {
        this.tower = tower;
    }

    public AbstractTower getTower() {
        return tower;
    }

    public boolean isHasTower() {
        return hasTower;
    }

    public int getId() {
        return id;
    }
}
