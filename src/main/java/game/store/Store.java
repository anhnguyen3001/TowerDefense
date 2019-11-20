package game.store;

import game.Config;
import game.helper.Rectangle;
import game.Display;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Store {
    public Display display;

    public Rectangle normalTower;
    public Rectangle sniperTower;
    public Rectangle machinegunTower;

    private int towerShow;

    public Store(Display display){
        this.display = display;

        normalTower = new Rectangle(16.5, 0.5, 0.8, 0.8, Config.NORMAL_T_PATH);

        sniperTower = new Rectangle(normalTower.getWidth() + normalTower.getMinX() + 0.3,
                0.5,0.8, 0.8, Config.SNIPER_T_PATH);

        machinegunTower = new Rectangle(16.5, 1.5, 0.8, 0.8, Config.MACHINE_GUN_T_PATH);

        towerShow = 0;
    }

    public void setTowerShow(int towerShow) {
        this.towerShow = towerShow;
    }

    public int getTowerShow() {
        return towerShow;
    }

    public void createStore (GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);

        gc.strokeRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);
        gc.setStroke(Color.BLACK);

        normalTower.render(gc);
        sniperTower.render(gc);
        machinegunTower.render(gc);

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
        }
    }
}
