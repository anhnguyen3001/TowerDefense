package game.state;

import game.Config;
import game.Display;
import game.helper.Asset;
import game.helper.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapModel extends State {
    private Image screen;
    private Game game;
    private int[][] easyBitMap;
    private Rectangle easyRect;

    private int[][] mediumBitMap;
    private Rectangle mediumRect;

    private int[][] hardBitMap;
    private Rectangle hardRect;

    private Display display;
    private String maptype;
    private StackPane menuLayout;

    private EventHandler<MouseEvent> chooseMap = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double mouseX = event.getX();
            double mouseY = event.getY();
            if (easyRect.contains(mouseX, mouseY)) maptype = "easy";
            else if (mediumRect.contains(mouseX, mouseY)) maptype = "medium";
            else if (hardRect.contains(mouseX, mouseY)) maptype = "hard";
        }
    };

    public MapModel(Display display, Game game){
        this.game = game;
        this.display = display;
        maptype = "";
        screen = new Image("file:src/main/resources/AssetsKit_2/bk.png");
        //Calculate position
        double SCREEN_WIDTH = display.getWidth();
        double SCREEN_HEIGHT = display.getHeight();
        double height = 0.2 * SCREEN_HEIGHT;
        double width = 0.25 * SCREEN_WIDTH;
        double gap = 50;
        double startX = (SCREEN_WIDTH - 2*gap - 3*width) * 0.5;
        double startY = SCREEN_HEIGHT - height - 100;

        easyRect = new Rectangle(startX, startY, width, height, null);
        mediumRect = new Rectangle(easyRect.getMinX() + width + gap, startY, width, height, null);
        hardRect = new Rectangle(mediumRect.getMinX() + gap + width, startY, width, height, null);

        easyBitMap = readBitMap("easy");
        mediumBitMap = readBitMap("medium");
        hardBitMap = readBitMap("hard");
    }

    public void setMenuLayout(StackPane menuLayout) {
        this.menuLayout = menuLayout;
    }

    private int[][] readBitMap(String path){
        try {
            Scanner sc = new Scanner(new File("src//main//java//game//" + path + ".txt"));
            sc.nextLine();
            //Size of map
            String[] temp = sc.nextLine().split("x");
            int row = Integer.parseInt(temp[0]);
            int col = Integer.parseInt(temp[1]);
            int[][] bitmap = new int[row][col];
            //Map
            for (int i = 0; i < row; i++) {
                String s = sc.nextLine();
                temp = s.split(" ");
                for (int j = 0; j < col; j++) {
                    if (temp[j].equals("1")) bitmap[i][j] = 1;
                    else bitmap[i][j] = 0;
                }
            }
            sc.close();

            return bitmap;
        } catch (IOException e){
            System.out.println("can't load map img");
        }

        return null;
    }

    @Override
    public void update() {
        if (!maptype.equals("")) {
            //display.changeStage();
            game.loadGame(maptype, display);
            maptype = "";
            State.setState(game);
            display.getGc().clearRect(0, 0, display.getWidth(), display.getHeight());
        }
    }

    @Override
    public void render() {
        menuLayout.setVisible(false);
        GraphicsContext gc = display.getGc();
        gc.drawImage(screen, 0, 0);

        gc.setFont(Font.font("BookMan", FontPosture.ITALIC, 60));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Choose Map", mediumRect.getMinX() + mediumRect.getWidth() *0.5, easyRect.getMinY() - 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRect(easyRect.getMinX(), easyRect.getMinY(), easyRect.getWidth(), easyRect.getHeight());
        drawMap(easyBitMap, easyRect);

        gc.strokeRect(mediumRect.getMinX(), mediumRect.getMinY(), mediumRect.getWidth(), mediumRect.getHeight());
        drawMap(mediumBitMap, mediumRect);

        gc.strokeRect(hardRect.getMinX(), hardRect.getMinY(), hardRect.getWidth(), hardRect.getHeight());
        drawMap(hardBitMap,hardRect);
    }

    @Override
    public void handle() {
        display.getGc().getCanvas().setOnMouseClicked(chooseMap);
    }

    public void drawMap(int[][] bitmap, Rectangle rect){
        GraphicsContext gc = display.getGc();

        double width = rect.getWidth()/bitmap[0].length;
        double height = rect.getHeight()/bitmap.length;
        double startX = rect.getMinX();
        double startY = rect.getMinY();
        Image road = new Image(Config.ROAD);
        Image mountain = new Image(Config.MOUNTAIN);

        for (int i = 0; i < bitmap.length; i++){
            for (int j = 0; j < bitmap[i].length; j++) {
                if (bitmap[i][j] == 1)
                    gc.drawImage(road, j * width + startX, i * height + startY, width, height);
                else gc.drawImage(mountain, j * width + startX, i * height + startY, width, height);
            }
        }
    }
}
