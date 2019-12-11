package game;

import game.state.MapModel;
import game.state.EndGame;
import game.state.Game;
import game.state.Menu;
import game.state.State;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Display display;
    private Menu menu;
    private MapModel map;
    private Game game;
    private EndGame endGame;

    public void start(Stage primaryStage){
        display = new Display(Config.GAME_TITLE, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, primaryStage);

        game = new Game();
        map = new MapModel(display, game);
        menu = new Menu(display, game, map);
        endGame = new EndGame(display, menu);
        game.setEndGame(endGame);

        State.setState(menu);
        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                // else// if (now - lastTime >= Config.ELAPSE_TIME)
                {
                    State.getState().render();
                    State.getState().update();
                    lastTime = now;
                }
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
