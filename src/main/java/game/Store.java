package game;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class Store {
    public static Button normalTower;
    public static Button sniperTower;
    public static Button machineTower;
    public static Button upgrade;
    public static Button NextWave;
    public static Button QuitGame;

    public static void createStore (GameField field){
        normalTower = new Button();
        normalTower.setGraphic(new ImageView(new Image(Config.NORMAL_T_PATH)));
        normalTower.setOnMouseClicked(field.getHandler());
        normalTower.setPrefSize(50, 50);
        normalTower.setMaxSize(50,50);

        sniperTower = new Button();
        sniperTower.setGraphic(new ImageView(new Image(Config.SNIPER_T_PATH)));
        sniperTower.setPrefSize(50, 50);
        sniperTower.setOnMouseClicked(field.getHandler());
        sniperTower.setTranslateX(0.5 * Config.SIZE_TILE);
        sniperTower.setTranslateY(1.2*Config.SIZE_TILE);

        machineTower = new Button();
        machineTower.setPrefSize(50, 50);
        machineTower.setGraphic(new ImageView(new Image(Config.MACHINE_GUN_T_PATH)));
        machineTower.setOnMouseClicked(field.getHandler());
        machineTower.setTranslateX(1.3 * Config.SIZE_TILE);

        NextWave = new Button();
        NextWave.setMaxSize(100, 50);
        NextWave.setGraphic(new ImageView(new Image(Config.BUTTON_NEXT_WAVE)));
        NextWave.setOnMouseClicked(field.getHandler());
        NextWave.setTranslateX(0.5 * Config.SIZE_TILE);
        NextWave.setTranslateY(4 * Config.SIZE_TILE);

        QuitGame = new Button();
        QuitGame.setGraphic(new ImageView(new Image(Config.BUTTON_QUIT)));
        QuitGame.setTranslateX(Config.SIZE_TILE * 0.5);
        QuitGame.setTranslateY(Config.SIZE_TILE * 8);
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

        Group layout = new Group();
        layout.setAutoSizeChildren(true);
        layout.setLayoutX(Config.SIZE_TILE * field.getCol());
        layout.getChildren().addAll(normalTower, machineTower, sniperTower, NextWave, QuitGame);
        field.getRoot().getChildren().add(layout);
    }
}
