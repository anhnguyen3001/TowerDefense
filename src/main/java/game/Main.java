package game;

import game.state.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Game game;
    @Override
    public void start(Stage primaryStage){
        game = new Game(Config.GAME_TITLE, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
