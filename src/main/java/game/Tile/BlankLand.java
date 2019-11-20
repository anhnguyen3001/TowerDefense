package game.Tile;

import game.Tile.Tower.AbstractTower;

public class BlankLand extends Mountain{
    private boolean hasTower;
    private AbstractTower tower;
    public BlankLand(double x, double y, int id) {
        super(x, y, id);
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
}
