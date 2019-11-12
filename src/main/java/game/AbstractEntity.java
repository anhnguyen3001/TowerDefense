package game;

import game.Tile.UpdateEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractEntity {
    private double x;
    private double y;
    private double size;
    private Image img;

    public AbstractEntity(double x, double y, String IMG_PATH){
        this.x = x;
        this.y = y;
        size = Config.SIZE_TILE;
        this.img = new Image(IMG_PATH);
    }

    public void setX(double x){
        this.x = x;
    }

    public double getX(){
        return x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getY(){
        return y;
    }

    public void setSize(double size) { this.size = size; }
    public double getSize() { return size; }

    public Image getImage(){
        return img;
    }

    public abstract void render(GraphicsContext gc);

    public static double evaluateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
