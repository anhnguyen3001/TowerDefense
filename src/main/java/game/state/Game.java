package game.state;

import game.*;
import game.store.Store;
import javafx.animation.AnimationTimer;

public class Game {
    private Display display;
    private GameField field;
    private GameStage gameStage;
    private Player player;
    private Store store;
    private Handle handler;

    public Game(Display display){
        this.display = display;
        gameStage = GameStage.load("demo.txt");
        player = gameStage.getPlayer();
        store = new Store(display);
        field = new GameField(gameStage, player);
        handler = new Handle(field, store, display.getRoot(),display.getGc());
    }

    public void start(){
        ControlBar.createControlBar(field, display);
        setMouseHandler();

        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;
            @Override
            public void handle(long now) {
                if (now - lastTime >= Config.ELAPSE_TIME) {
                    store.createStore(display.getGc());
                    field.update();
                    field.render(display.getGc());

                    if (player.getLive() <= 0) {
                        int tick = 0;
                        while (tick != 100000000) tick++;
                        EndGame.render(display, "lose");
                        ControlBar.deleteControlBar(display);
                        stop();
                    } else if (field.endGame()) {
                        int tick = 0;
                        while (tick != 100000000) tick++;
                        EndGame.render(display, "win");
                        ControlBar.deleteControlBar(display);
                        stop();
                    }

                    lastTime = now;
                }
            }
        };
        timer.start();
    }

    public void setMouseHandler(){
        display.getScene().setOnMouseClicked(handler);
        display.getScene().setOnMouseMoved(handler);
    }
}
