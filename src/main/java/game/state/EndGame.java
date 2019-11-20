package game.state;

import game.Config;
import game.Display;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Optional;

public class EndGame {
    public static void render(Display display, String state){
        ImageView iv = new ImageView();
        if (state.equals("win")) iv.setImage(new Image(Config.YOU_WIN));
        else iv.setImage(new Image(Config.YOU_LOSE));

        Pane pane = new Pane();
        Button newgame = new Button();
        newgame.setGraphic(new ImageView(new Image(Config.BUTTON_START_GAME)));
        newgame.setOnAction(e-> {
            display.changeStage();
            Game game = new Game(display);
            game.start();
        });
        newgame.setLayoutX(480);
        newgame.setLayoutY(370);
        Button quitgame = new Button();
        quitgame.setGraphic(new ImageView(new Image(Config.BUTTON_QUIT_GAME)));
        quitgame.setOnAction(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(no, yes);
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get().getButtonData() == ButtonBar.ButtonData.YES) display.getStage().close();
        });

        quitgame.setLayoutX(480);
        quitgame.setLayoutY(500);
        pane.getChildren().addAll(iv,newgame,quitgame);
        display.setScene(new Scene(pane));
        display.getStage().setScene(display.getScene());
    }
}
