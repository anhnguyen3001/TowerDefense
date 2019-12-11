package game;

import game.helper.Rectangle;
import game.state.Display;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Store {
    public Display display;
    public Rectangle normalTower;
    public Rectangle sniperTower;
    public Rectangle machinegunTower;
    public Button upgrade;


    public Store(Display display){
        this.display = display;

        normalTower = new Rectangle(16.2, 0.5, 0.8, 0.8, Config.NORMAL_T_PATH);
        sniperTower = new Rectangle(normalTower.getWidth() + normalTower.getMinX() + 0.5,
                0.5,0.8, 0.8, Config.SNIPER_T_PATH);
        machinegunTower = new Rectangle(16.2, 1.5, 0.8, 0.8, Config.MACHINE_GUN_T_PATH);
    }

    public void createStore (GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);
        normalTower.render(gc);
        sniperTower.render(gc);
        machinegunTower.render(gc);
    }
}
