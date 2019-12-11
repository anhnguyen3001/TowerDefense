package game;

import game.Tile.Tower.*;
import game.helper.Asset;
import game.helper.Rectangle;
import game.store.Store;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class UI {
    private Display display;
    private GraphicsContext gc;
    private ControlBar controlBar;
    private Store store;
    private Rectangle music;
    private boolean onMusic;
    private Handle handle;

    private EventHandler<MouseEvent> MusicControl = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (music.contains(event.getX()/Config.SIZE_TILE, event.getY()/Config.SIZE_TILE)){
                onMusic = !onMusic;
                if (onMusic) music.setImg(new Image(Config.MUSIC_ON));
                else music.setImg(new Image(Config.MUSIC_OFF));
                System.out.println("1");
            }
        }
    };

    public UI(Display display, GameField field){
        this.display = display;
        music = new Rectangle(field.getCol(), 0, 0.5, 0.5, new Image(Config.MUSIC_ON));
        gc = display.getGc();
        store = new Store(display);
        handle = new Handle(field, store, display.getRoot());
        controlBar = new ControlBar(display);
        onMusic = true;
    }
    public boolean isOnMusic(){
        return onMusic;
    }

    public boolean nextWave(){
        return controlBar.isClickNext();
    }

    public boolean pauseGame(){
        return controlBar.isClickPause();
    }

    public void setclickNext(boolean clickNext){
        controlBar.setClickNext(clickNext);
    }

    public void render(){
        store.createStore(gc);
        music.render(gc);
    }

    public boolean savegame(){
        return controlBar.isSaveGame();
    }

    public void setHandle(){
        display.getGc().getCanvas().setOnMouseClicked(MusicControl);
        display.getScene().setOnMouseClicked(handle);
        display.getScene().setOnMouseMoved(handle);
    }

    public void clear(){
        gc.clearRect(0,0, display.getWidth(), display.getHeight());
        controlBar.setDisable();
    }
    /**private ArrayList<Rectangle> buttonStore = new ArrayList<>();
    private String towerPick;
    private String towerShow;
    private AbstractTower tower;
    private boolean placeTower;
    private ControlBar controlBar;
    private EventHandler<MouseEvent> pickTower = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double mouseX = event.getX() / Config.SIZE_TILE;
            double mouseY = event.getY() / Config.SIZE_TILE;

            if (mouseX < 16.5) {
                if (towerPick.equals("normal tower")) tower = new NormalTower(mouseX, mouseY, 0, 1);
                else if (towerPick.equals("sniper tower")) tower = new SniperTower(mouseX, mouseY, 0, 1);
                else if (towerPick.equals("machine gun tower"))
                    tower = new MachineGunTower(mouseX, mouseY, 0, 1);
                else if (towerPick.equals("freeze tower")) tower = new FreezeTower(mouseX, mouseY, 0, 1);
                else if (towerPick.equals("rocket tower")) tower = new RocketTower(mouseX, mouseY, 0, 1);
                towerPick = "";
                towerShow = "";
            } else {
                for (Rectangle button : buttonStore) {
                    if (button.contains(mouseX, mouseY) && !towerPick.equals(button.getName())) {
                        if (!towerPick.equals(button.getName())) towerPick = button.getName();
                        else towerPick = "";
                        towerShow = button.getName();
                        break;
                    } else towerShow = "";
                }
            }
        }
    };

    private EventHandler<MouseEvent> showTower = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double mouseX = event.getX()/Config.SIZE_TILE;
            double mouseY = event.getY()/Config.SIZE_TILE;
            if (!towerPick.equals("")) drawTowerPick(towerPick, mouseX, mouseY);
            else for (Rectangle button:buttonStore)
                    if (button.contains(mouseX, mouseY))
                        towerShow = button.getName();
        }
    };

    public UI(Display display) {
        gc = display.getGc();
        setupStore();
        towerPick = "";
        towerShow = "";
        tower = null;
        placeTower = false;
        display.getScene().setOnMouseClicked(pickTower);
        display.getScene().setOnMouseMoved(showTower);

        controlBar = new ControlBar(display);
    }

    private void setupStore(){
        double size = 0.8;
        double x = 16.5;
        double y = 0.3;

        buttonStore.add(new Rectangle("normal tower", x, y, size, size, Asset.NORMAL_TOWER));
        buttonStore.add(new Rectangle("sniper tower", x + size + 0.3, y, size, size, Asset.SNIPER_TOWER));
        buttonStore.add(new Rectangle("machine gun tower", x, y + 1, size, size, Asset.MACHINEGUNTOWER));
        buttonStore.add(new Rectangle("rocket tower", x + size + 0.3, y + 1, size, size, Asset.ROCKET_TOWER));
        buttonStore.add(new Rectangle("freeze tower", x, y + 2, size, size, Asset.FREEZE_TOWER));
    }

    public void render(){
        gc.setFill(Color.GRAY);
        gc.fillRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);

        gc.strokeRect(16 * Config.SIZE_TILE, 0, Config.SCREEN_WIDTH - 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT);
        gc.setStroke(Color.BLACK);
        for (Rectangle button : buttonStore) {
            button.render(gc);
        }

        switch (towerShow) {
            case "normal tower": {
                new InfoTable().renderInfoTable(gc, Config.NORMAL_T_Speed, Config.NORMAL_T_DAMAGE,
                        Config.NORMAL_T_Range, Config.NORMAL_T_Cost);
                break;
            }
            case "sniper tower": {
                new InfoTable().renderInfoTable(gc, Config.SNIPER_T_Speed, Config.SNIPER_T_DAMAGE,
                        Config.SNIPER_T_Range, Config.SNIPER_T_Cost);
                break;
            }
            case "machine gun tower": {
                new InfoTable().renderInfoTable(gc, Config.MACHINEGUN_T_Speed, Config.MACHINEGUN_T_DAMAGE,
                        Config.MACHINEGUN_T_Range, Config.MACHINEGUN_T_Cost);
                break;
            }
            case "rocket tower": {
                new InfoTable().renderInfoTable(gc, Config.ROCKET_T_Speed, Config.ROCKET_T_DAMAGE,
                        Config.ROCKET_T_Range, Config.ROCKET_T_Cost);
                break;
            }
            case "freeze tower": {
                new InfoTable().renderInfoTable(gc, Config.FREEZE_T_Speed, Config.FREEZE_T_DAMAGE,
                        Config.FREEZE_T_Range, Config.FREEZE_T_Cost);
                break;
            }
        }
    }

    public void drawTowerPick(String towerPick, double x, double y){
        int size = Config.SIZE_TILE;
        for (Rectangle button: buttonStore){
            if (towerPick.equals(button.getName()))
                gc.drawImage(button.getImg(), (x-0.5) * size, (y-0.5)*size);
        }
    }

    public boolean nextWave(){
        return controlBar.isClickNext();
    }

    public boolean pauseGame(){
        return controlBar.isClickPause();
    }

    public void setclickNext(boolean clickNext){
        controlBar.setClickNext(clickNext);
    }

    public AbstractTower getTowerBuild(){
        if (placeTower) return tower;
        return null;
    }**/
}
