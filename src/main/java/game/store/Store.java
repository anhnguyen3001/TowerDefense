package game.store;

import game.Config;
import game.helper.Asset;
import game.helper.Rectangle;
import game.Display;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Store {
    public Display display;

    public Rectangle normalTower;
    public Rectangle sniperTower;
    public Rectangle machinegunTower;
    public Rectangle rocketTower;
    public Rectangle freezeTower;

    private int towerShow;

    public Store(Display display){
        this.display = display;

        double size = 0.8;
        normalTower = new Rectangle(16.5, 0.3, size, size, Asset.NORMAL_TOWER);
        double x = normalTower.getMinX();
        double y = normalTower.getMinY();

        sniperTower = new Rectangle( x + size + 0.3, y, size, size, Asset.SNIPER_TOWER);

        machinegunTower = new Rectangle(x, y + 1, size, size, Asset.MACHINEGUNTOWER);

        rocketTower = new Rectangle(sniperTower.getMinX(), machinegunTower.getMinY(), size, size, Asset.FULL_ROCKET_TOWER);

        freezeTower = new Rectangle(x, rocketTower.getMinY() + 1, size, size, Asset.FREEZE_TOWER);

        towerShow = 0;
    }

    public void setTowerShow(int towerShow) {
        this.towerShow = towerShow;
    }

    public void createStore (GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);

        gc.strokeRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);
        gc.setStroke(Color.BLACK);

        normalTower.render(gc);
        sniperTower.render(gc);
        machinegunTower.render(gc);
        rocketTower.render(gc);
        freezeTower.render(gc);

        //showInfo
        switch (towerShow){
            case Config.NORMAL_TOWER: {
                new InfoTable().renderInfoTable(gc, Config.NORMAL_T_Speed, Config.NORMAL_T_DAMAGE,
                        Config.NORMAL_T_Range, Config.NORMAL_T_Cost);
                break;
            }
            case Config.SNIPER_TOWER:{
                new InfoTable().renderInfoTable(gc, Config.SNIPER_T_Speed, Config.SNIPER_T_DAMAGE,
                        Config.SNIPER_T_Range, Config.SNIPER_T_Cost);
                break;
            }
            case Config.MACHINE_GUN_TOWER:{
                new InfoTable().renderInfoTable(gc, Config.MACHINEGUN_T_Speed, Config.MACHINEGUN_T_DAMAGE,
                        Config.MACHINEGUN_T_Range, Config.MACHINEGUN_T_Cost);
                break;
            }
            case Config.ROCKET_TOWER:{
                new InfoTable().renderInfoTable(gc, Config.ROCKET_T_Speed, Config.ROCKET_T_DAMAGE,
                        Config.ROCKET_T_Range, Config.ROCKET_T_Cost);
                break;
            }
            case Config.FREEZE_TOWER:{
                new InfoTable().renderInfoTable(gc, Config.FREEZE_T_Speed, Config.FREEZE_T_DAMAGE,
                        Config.FREEZE_T_Range, Config.FREEZE_T_Cost);
                break;
            }
        }
    }
}
