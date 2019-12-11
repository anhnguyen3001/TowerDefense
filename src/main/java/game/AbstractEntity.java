package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractEntity {
    private double x;
    private double y;
    private Image img;

    public AbstractEntity(double x, double y, Image img){
        this.x = x;
        this.y = y;
        this.img = img;
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

    public Image getImage(){
        return img;
    }

    public abstract void render(GraphicsContext gc);

    public static double evaluateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public String toString(){
        return (Double.toString(x) + " " + Double.toString(y) + " ");
    }
}
