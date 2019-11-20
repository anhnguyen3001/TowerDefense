package game.helper;

import game.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Rectangle {
    private double minX;
    private double minY;
    private double width;
    private double height;
    private Image img;

    public Rectangle(double minX, double minY, double width, double height, String IMG_PATH){
        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
        img = new Image(IMG_PATH);
    }

    public void render(GraphicsContext gc){
        int TILE_SIZE = Config.SIZE_TILE;
        gc.strokeRect(minX * TILE_SIZE, minY * TILE_SIZE, width * TILE_SIZE, height * TILE_SIZE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.SQUARE);
        gc.fillRect(minX * TILE_SIZE, minY * TILE_SIZE, width * TILE_SIZE, height * TILE_SIZE);
        gc.drawImage(img, minX * Config.SIZE_TILE, minY * Config.SIZE_TILE, width * TILE_SIZE, height * TILE_SIZE);
    }

    public double getMinX(){
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Image getImg() {
        return img;
    }

    public boolean contains(double x, double y){
        return (minX <= x && minX + width >= x && minY <= y && minY + height >= y);
    }
}
