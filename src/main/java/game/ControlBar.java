package game;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class ControlBar {
    private static Group group;
    public static void createControlBar(GameField field, Display display){
        Button NextWave = new Button();
        Button QuitGame = new Button();

        NextWave.setMaxSize(100, 50);
        NextWave.setGraphic(new ImageView(new Image(Config.BUTTON_NEXT_WAVE)));
        NextWave.setTranslateX(16.5 * Config.SIZE_TILE);
        NextWave.setTranslateY(2.5 * Config.SIZE_TILE);
        NextWave.setOnMouseClicked(event -> {
            if (!field.isContinue()){
                field.newLevel();
            }
        });

        QuitGame.setGraphic(new ImageView(new Image(Config.BUTTON_QUIT)));
        QuitGame.setTranslateX(Config.SIZE_TILE * 16.5);
        QuitGame.setTranslateY(Config.SIZE_TILE * 9.5);
        QuitGame.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(no, yes);
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get().getButtonData() == ButtonBar.ButtonData.YES) System.exit(0);
        });

        group = new Group();
        group.setAutoSizeChildren(true);
        group.getChildren().addAll(NextWave, QuitGame);
        display.getRoot().getChildren().add(group);
    }

    public static void deleteControlBar(Display display){
        display.getRoot().getChildren().remove(group);
    }
}
