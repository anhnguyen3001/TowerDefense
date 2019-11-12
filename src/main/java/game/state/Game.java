package game.state;

import game.Config;
import game.GameField;
import game.GameStage;
import game.Player;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import javax.naming.CompositeName;

public class Game {
    private Display display;
    private Menu menu;
    private GameField field;
    private GameStage gameStage;
    private Player player;

    public Game(String gameTitle, int width, int height, Stage stage){
        display = new Display(gameTitle, width, height, stage);

        menu = new Menu(display, this);
        menu.createMenu();

        gameStage = GameStage.load("demo.txt");
        player = gameStage.getPlayer();
    }

    public void start(){
        field = new GameField(gameStage, player, display.getGc(), display.getRoot());
        setMouseHandler();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                field.update();
                field.render();

                if (player.getLive() <= 0) {
                    System.out.println("You Lose");
                    stop();
                } else if (!field.isContinue()) {
                    System.out.println("You win");
                    stop();
                }
            }
        };
        timer.start();
    }

    public void setMouseHandler(){
        display.getScene().setOnMouseClicked(field.getHandler());
        display.getScene().setOnMouseMoved(field.getHandler());
    }
}
