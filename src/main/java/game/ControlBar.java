package game;

import game.state.Game;
import javafx.beans.property.LongProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class ControlBar {
    private Display display;
    private Group group;
    private boolean clickNext;
    private boolean clickPause;
    private boolean saveGame;

    public ControlBar(Display display){
        this.display = display;
        this.group = new Group();
        createControlBar();
        clickNext = false;
        clickPause = false;
        saveGame = false;
    }

    private void createControlBar(){
        Button NextWave = new Button();
        Button PauseGame = new Button();

        NextWave.setGraphic(new ImageView(new Image(Config.BUTTON_NEXT_WAVE)));
        NextWave.setTranslateX(16.5 * Config.SIZE_TILE);
        NextWave.setTranslateY(3.2 * Config.SIZE_TILE);
        NextWave.setOnMouseClicked(event -> {
            clickNext = !clickNext;
        });

        //Resume, Quit (after click Pause)
        VBox layout = new VBox(40);
        layout.prefWidthProperty().bind(display.getScene().widthProperty());
        layout.prefHeightProperty().bind(display.getScene().heightProperty());
        layout.setAlignment(Pos.CENTER);
        group.getChildren().add(layout);
        Button Resume = new Button();
        Resume.setGraphic(new ImageView(new Image(Config.BUTTON_RESUME)));
        Resume.setOnMouseClicked(e -> {
            clickPause = false;
            clickNext = false;
            layout.setVisible(false);
        });

        Button QuitGame = new Button();
        QuitGame.setGraphic(new ImageView(new Image(Config.BUTTON_QUIT_GAME)));
        QuitGame.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(no, yes);
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get().getButtonData() == ButtonBar.ButtonData.YES) {
                saveGame = true;
            } else clickPause = false;
            layout.setVisible(false);
        });

        layout.getChildren().addAll(Resume, QuitGame);
        layout.setVisible(false);

        PauseGame.setGraphic(new ImageView(new Image(Config.BUTTON_PAUSE)));
        PauseGame.setTranslateX(Config.SIZE_TILE * 16.5);
        PauseGame.setTranslateY(Config.SIZE_TILE * 9.5);
        PauseGame.setOnMouseClicked(event -> {
            clickPause = true;
            layout.setVisible(true);
        });

        group.getChildren().addAll(NextWave, PauseGame);
        display.getRoot().getChildren().add(group);
    }

    public void setClickNext(boolean clickNext) {
        this.clickNext = clickNext;
    }

    public boolean isClickNext() {
        return clickNext;
    }

    public boolean isClickPause() {
        return clickPause;
    }

    public boolean isSaveGame() {
        return saveGame;
    }

    public void setVisible(){
        group.setVisible(true);
    }

    public void setDisable(){
        group.setVisible(false);
    }
}
