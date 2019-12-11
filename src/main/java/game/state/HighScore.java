package game.state;

import game.Config;
import game.Display;
import game.helper.ListScore;
import game.helper.Score;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class HighScore extends State{
    private Display display;
    private Menu menu;
    private Group highscoreLayout;
    private GridPane easy;
    private GridPane medium;
    private GridPane hard;
    private Button back;
    private ArrayList<Score> easylist = new ArrayList<>();
    private ArrayList<Score> mediumlist = new ArrayList<>();
    private ArrayList<Score> hardlist = new ArrayList<>();

    public HighScore(Display display, Menu menu){
        this.display = display;
        this.menu = menu;
        back = new Button();
        easy = new GridPane();
        medium = new GridPane();
        hard = new GridPane();
        highscoreLayout = new Group();
        highscoreLayout.getChildren().addAll(easy, medium, hard);
        highscoreLayout.setVisible(false);
        display.getRoot().getChildren().add(highscoreLayout);
        creatHighScoreScene();
    }

    private void creatHighScoreScene(){
        //Easy Table
        setColumnSize(easy);
        easy.setTranslateX(80);
        Text title1 = new Text("Easy");
        title1.setFont(Font.font("Florence", FontWeight.BOLD, 35));
        Text no1 = new Text("No");
        no1.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text player1 = new Text("Name");
        player1.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text level1 = new Text("Level");
        level1.setFont(Font.font("Florence", FontPosture.ITALIC, 30));

        easy.add(title1,1, 0);
        easy.add(no1,0, 1);
        easy.add(player1, 1, 1);
        easy.add(level1, 2, 1);

        setColumnSize(medium);
        medium.setTranslateX(450);
        Text title2 = new Text("Medium");
        title2.setFont(Font.font("Florence", FontWeight.BOLD, 35));
        Text no2 = new Text("No");
        no2.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text player2 = new Text("Name");
        player2.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text level2 = new Text("Level");
        level2.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        medium.add(title2,1, 0);
        medium.add(no2,0, 1);
        medium.add(player2, 1, 1);
        medium.add(level2, 2, 1);

        Text title3 = new Text("Hard");
        hard.setTranslateX(850);
        title3.setFont(Font.font("Florence", FontWeight.BOLD, 35));
        Text no3 = new Text("No");
        no3.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text player3 = new Text("Name");
        player3.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        Text level3 = new Text("Level");
        level3.setFont(Font.font("Florence", FontPosture.ITALIC, 30));
        setColumnSize(hard);
        hard.add(title3,1, 0);
        hard.add(no3,0, 1);
        hard.add(player3, 1, 1);
        hard.add(level3, 2, 1);

        back.setText("Back");
        back.setTranslateX(600);
        back.setTranslateY(500);
        back.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 30));
        highscoreLayout.getChildren().add(back);
        highscoreLayout.setTranslateY(100);
    }

    public void loadHighScore(){
        easylist = new ListScore("easy").getScores();
        mediumlist = new ListScore("medium").getScores();
        hardlist = new ListScore("hard").getScores();
        addNo(easy, easylist);
        addNo(medium,mediumlist);
        addNo(hard, hardlist);
    }

    private void addNo(GridPane gridPane, ArrayList<Score> scoreList){
        if (scoreList.isEmpty()){
            Text information = new Text("No Content");
            information.setFont(Font.font("Times New Roman", 25));
        } else for (int i = 0; i < scoreList.size(); i++){
            Text no = new Text(Integer.toString(scoreList.get(i).getId()));
            Text name = new Text(scoreList.get(i).getName());
            Text level = new Text(Integer.toString(scoreList.get(i).getLevel()));
            no.setFont(Font.font("Times New Roman", 25));
            name.setFont(Font.font("Times New Roman", 25));
            level.setFont(Font.font("Times New Roman", 25));
            gridPane.add(no, 0, i+2);
            gridPane.add(name, 1, i+2);
            gridPane.add(level,2, i + 2);
        }
        gridPane.setVisible(true);
    }

    private void setColumnSize(GridPane gridPane){
        ColumnConstraints cl1 = new ColumnConstraints();
        cl1.setMinWidth(50);
        ColumnConstraints cl2 = new ColumnConstraints();
        cl2.setMinWidth(120);
        ColumnConstraints cl3 = new ColumnConstraints();
        cl3.setMinWidth(80);
        cl1.setHalignment(HPos.CENTER);
        cl2.setHalignment(HPos.CENTER);
        cl3.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().setAll(cl1, cl2, cl3);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
    }

    @Override
    public void update() {
        if (back.isPressed()){
            State.setState(menu);
        }
    }

    @Override
    public void render() {
        GraphicsContext gc = display.getGc();
        gc.setFill(Color.PAPAYAWHIP);
        display.getGc().fillRect(0, 0, display.getWidth(), display.getHeight());
        if (back.isPressed()) highscoreLayout.setVisible(false);
        else if (!highscoreLayout.isVisible()) highscoreLayout.setVisible(true);
    }

    @Override
    public void handle() {
    }
}
