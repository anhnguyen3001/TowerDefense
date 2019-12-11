package game.state;

import game.Config;
import game.Display;
import game.Player;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Optional;

public class Menu extends State{
    private Display display;
    private Game game;
    private MapModel map;
    private HighScore highScoreScene;

    private StackPane menuLayout;

    private VBox layout;
    private Button playGame;
    private Button highscore;
    private Button cont;
    private Button quit;

    private VBox nameInput;
    private Button submit;
    private Button cancel;

    private int option;

    public Menu(Display display, Game game, MapModel map) {
        this.display = display;
        this.game = game;
        option = 0;

        playGame = new Button();
        highscore = new Button();
        cont = new Button();
        quit = new Button();
        layout = new VBox(30);

        nameInput = new VBox(30);
        submit = new Button();
        cancel = new Button();
        createInputNamePlayer();

        menuLayout = new StackPane();
        menuLayout.prefWidthProperty().bind(display.getScene().widthProperty());
        menuLayout.prefHeightProperty().bind(display.getScene().heightProperty());
        menuLayout.getChildren().addAll(layout, nameInput);
        display.getRoot().getChildren().add(menuLayout);

        this.map = map;
        map.setMenuLayout(menuLayout);
        highScoreScene = new HighScore(display, this);
        createMenu();
    }

    public void setOption(int option){
        this.option = option;
    }

    public void createMenu(){
        Image screen = new Image("file:src/main/resources/AssetsKit_2/bk.png");
        BackgroundImage bki= new BackgroundImage(screen, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background bkg = new Background(bki);
        menuLayout.setBackground(bkg);

        Image playgame = new Image(Config.BUTTON_START_GAME);
        Image highScore = new Image(Config.BUTTON_HIGH_SCORE);
        Image continuegame = new Image(Config.BUTTON_CONTINUE);
        Image Quit = new Image(Config.BUTTON_QUIT_GAME);

        playGame.setTranslateY(80);
        playGame.setGraphic(new ImageView(playgame));

        highscore.setTranslateY(80);
        highscore.prefWidthProperty().bind(playGame.widthProperty());
        highscore.setGraphic(new ImageView(highScore));

        cont.prefWidthProperty().bind(playGame.widthProperty());
        cont.setTranslateY(80);
        cont.setGraphic(new ImageView(continuegame));
        cont.setTextAlignment(TextAlignment.CENTER);

        quit.prefWidthProperty().bind(playGame.widthProperty());
        quit.setTranslateY(80);
        quit.setGraphic(new ImageView(Quit));

        layout.prefWidthProperty().bind(display.getScene().widthProperty());
        layout.prefHeightProperty().bind(display.getScene().heightProperty());
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(playGame, highscore, cont, quit);
    }

    public void update(){
        if (option == Config.CONTINUE) {
            option = 0;
            game.setPlayer(new Player());
            game.loadGame("SaveGame", display);
            State.setState(game);
        } else if (option == Config.NEW_GAME){
            option = 0;
            State.setState(map);
        } else if (option == Config.HIGHSCORE){
            option = 0;
            highScoreScene.loadHighScore();
            State.setState(highScoreScene);
        }
    }

    public void render(){
        if (!menuLayout.isVisible()) menuLayout.setVisible(true);
        if (option == 0) {
            nameInput.setVisible(false);
            layout.setVisible(true);
        } else if (option == Config.ENTER_NAME){
            nameInput.setVisible(true);
            layout.setVisible(false);
        } else {
            menuLayout.setVisible(false);
            nameInput.setVisible(false);
            layout.setVisible(false);
        }
    }

    @Override
    public void handle() {
        playGame.setOnAction(e -> {
            option = Config.ENTER_NAME;
        });

        highscore.setOnAction(e ->{
            option = Config.HIGHSCORE;
        });

        cont.setOnAction(e -> {
            option = Config.CONTINUE;
        });

        quit.setOnAction(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to quit?");

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(no, yes);
            Optional<ButtonType> res = alert.showAndWait();

            if (res.isPresent() && res.get().getButtonData() == ButtonBar.ButtonData.YES) display.getStage().close();
        });
    }

    private void createInputNamePlayer(){
        Text title = new Text();
        title.setFont(new Font("Arial", 40));
        title.setText("Please enter your player name");

        TextField name = new TextField();
        name.setMaxSize(display.getWidth() *0.5, 200);
        name.setPromptText("Name");

        submit.setText("Ok");
        submit.setPrefSize(display.getWidth() * 0.1, 30);
        submit.setOnMouseClicked(event -> {
            if (!name.getText().isEmpty()) {
                option = Config.NEW_GAME;
                Player player = new Player();
                player.setName(name.getText());
                player.setCoin(50);
                player.setLive(15);
                game.setPlayer(player);
            }
        });

        cancel.setText("Cancel");
        cancel.setPrefSize(display.getWidth() * 0.1, 30);
        cancel.setOnMouseClicked(event -> {
            option = 0;
        });

        HBox layout = new HBox(60);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(submit, cancel);

        nameInput.setTranslateY(display.getHeight() * 0.1);
        nameInput.setAlignment(Pos.CENTER);
        nameInput.setPrefSize(display.getWidth() * 0.2, display.getHeight() * 0.3);
        nameInput.getChildren().addAll(title, name, layout);
    }
}
