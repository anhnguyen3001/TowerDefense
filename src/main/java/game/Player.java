package game;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class Player {
    private int coin;
    private int live;

    public Player(int coin, int live){
        this.coin = coin;
        this.live = live;
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

    public void renderStatus(Group root, GraphicsContext gc){
        Text health = new Text();
        health.setText(Integer.toString(this.live));
        health.setX(2*Config.SIZE_TILE);
        health.setY(Config.SCREEN_HEIGHT - Config.SIZE_TILE);
        root.getChildren().add(health);
    }
}
