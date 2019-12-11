package game;

import game.helper.Rectangle;
import game.store.Store;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class UI {
    private Display display;
    private GraphicsContext gc;
    private ControlBar controlBar;
    private Store store;
    private Rectangle music;
    private boolean onMusic;
    private Handle handle;

    public UI(Display display, GameField field){
        this.display = display;
        music = new Rectangle(field.getCol(), 0, 0.5, 0.5, new Image(Config.MUSIC_ON));
        gc = display.getGc();
        store = new Store(display);
        handle = new Handle(field, store, display.getRoot(), music);
        controlBar = new ControlBar(display);
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

    private void setMusic(boolean onMusic){
        this.onMusic = onMusic;
        if (onMusic) music.setImg(new Image(Config.MUSIC_ON));
        else music.setImg(new Image(Config.MUSIC_OFF));
    }

    public void render(){
        store.createStore(gc);
        setMusic(handle.isOnMusic());
        music.render(gc);
    }

    public boolean savegame(){
        return controlBar.isSaveGame();
    }

    public void setHandle(){
        display.getScene().setOnMouseClicked(handle);
        display.getScene().setOnMouseMoved(handle);
    }

    public void clear(){
        gc.clearRect(0,0, display.getWidth(), display.getHeight());
        controlBar.setDisable();
    }
}
