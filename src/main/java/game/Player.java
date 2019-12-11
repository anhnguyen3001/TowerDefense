package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Player {
    private String name;
    private int coin;
    private int live;

    public Player(){
        name = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public int getLive() {
        return live;
    }

    public void renderStatus(GraphicsContext gc){
        gc.clearRect(0, Config.SIZE_TILE*10, Config.SIZE_TILE*16, Config.SCREEN_HEIGHT - Config.SIZE_TILE*10);
        gc.strokeRect(0, Config.SIZE_TILE*10, Config.SIZE_TILE*16, Config.SCREEN_HEIGHT - Config.SIZE_TILE*10);
        gc.setStroke(Color.BLACK);

        gc.setFill(Color.GRAY);
        gc.fillRect(0, Config.SIZE_TILE*10, 16 * Config.SIZE_TILE, Config.SCREEN_HEIGHT - Config.SIZE_TILE*10);

        gc.setFont(Font.font("Bookman", 25));
        gc.setFill(Color.BLACK);
        gc.fillText("Lives: " + Integer.toString(getLive()), 7.5 * Config.SIZE_TILE, 10.7 * Config.SIZE_TILE);
        gc.fillText("Coins: " + Integer.toString(getCoin()), 12.5 * Config.SIZE_TILE, 10.7 * Config.SIZE_TILE);
    }

    public String toString(){
        return "Player " + name + " " + Integer.toString(live) + " " + Integer.toString(coin) + "\n";
    }
}
