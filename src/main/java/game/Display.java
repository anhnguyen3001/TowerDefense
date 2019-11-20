package game;

import game.Config;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Display {
    private Stage primaryStage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc;
    private Group root;
    private int width;
    private int height;

    public Display(String gameTitle, int width, int height, Stage stage){
        this.width = width;
        this.height = height;
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root = new Group(canvas);
        scene = new Scene(root);

        primaryStage = stage;
        primaryStage.setTitle(gameTitle);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public void changeStage(){
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root = new Group(canvas);
        scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public Group getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

}
