package game.state;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Menu{
    private Display display;
    private Game game;
    private Button playGame;
    private Button highScore;
    private Button quit;
    public Menu(Display display, Game game) {
        this.display = display;
        this.game = game;
    }

    public void createMenu(){
        Image playgame = new Image("file:src/main/resources/Button/Start.png");
        Image highscore = new Image("file:src/main/resources/Button/HighScore.png");
        Image Quit = new Image("file:src/main/resources/Button/QuitGame.png");
        Image screen = new Image("file:src/main/resources/AssetsKit_2/Sample.png");

        playGame = new Button();
        playGame.setGraphic(new ImageView(playgame));
        playGame.setOnAction(e -> {
            final Canvas canvas = new Canvas(display.getWidth(), display.getHeight());
            display.changeStage(canvas);
            game.start();
        });

        highScore = new Button();
        highScore.setGraphic(new ImageView(highscore));
        highScore.setOnAction(e -> {

        });

        quit = new Button();
        quit.setGraphic(new ImageView(Quit));
        quit.setOnAction(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(no, yes);
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get().getButtonData() == ButtonBar.ButtonData.YES) display.getStage().close();
        });

        BackgroundImage bki= new BackgroundImage(screen, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bkg = new Background(bki);

        VBox layout = new VBox(100);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(bkg);
        layout.getChildren().addAll(playGame, highScore, quit);

        display.setScene(new Scene(layout, display.getWidth(), display.getHeight()));
        display.getStage().setScene(display.getScene());
    }
}
