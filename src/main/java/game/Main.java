package game;

import game.state.Game;
import game.state.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Display display;
    private Menu menu;
    private Game game;
    @Override
    public void start(Stage primaryStage){
        display = new Display(Config.GAME_TITLE, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, primaryStage);
        menu = new Menu(display,game);
        menu.createMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
