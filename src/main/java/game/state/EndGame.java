package game.state;

import game.Config;
import game.Display;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Optional;

public class EndGame extends State{
    private Display display;
    private Pane pane;
    private Menu menu;
    private String state;
    private Button newgame;
    private Button quitgame;
    private ImageView background;
    private int option;

    public EndGame(Display display, Menu menu){
        this.display = display;
        this.menu = menu;
        state = "";

        newgame = new Button();
        quitgame = new Button();
        background = new ImageView();
        pane = new Pane();
        pane.getChildren().addAll(background,newgame,quitgame);

    }

    public void draw(String status) {
        this.state = status;
        drawButton();
    }

    @Override
    public void update() {
        if (option == Config.NEW_GAME){
            menu.setOption(Config.ENTER_NAME);
            State.setState(menu);
            option = 0;
        }
    }

    public void render(){
        if (option == Config.NEW_GAME) display.getRoot().getChildren().remove(pane);
        else if (!display.getRoot().getChildren().contains(pane)) display.getRoot().getChildren().add(pane);
    }

    @Override
    public void handle() {
        newgame.setOnAction(e-> {
            option = Config.NEW_GAME;
        });

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
    }

    private void drawButton(){
        if (state.equals("win")) background.setImage(new Image(Config.YOU_WIN));
        else background.setImage(new Image(Config.YOU_LOSE));

        newgame.setGraphic(new ImageView(new Image(Config.BUTTON_START_GAME)));
        newgame.setLayoutX(480);
        newgame.setLayoutY(370);

        quitgame.setGraphic(new ImageView(new Image(Config.BUTTON_QUIT_GAME)));
        quitgame.setLayoutX(480);
        quitgame.setLayoutY(500);
    }
}
