package game;

import game.Tile.Tower.MachineGunTower;
import game.Tile.Tower.NormalTower;
import game.Tile.Tower.SniperTower;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Stack;

public class Handle implements EventHandler<MouseEvent> {
    private GameField field;
    private ImageView iv;
    private Stack<Object> inputType = new Stack<>();

    private Image imgNormalTower = new Image(Config.NORMAL_T_PATH);
    private Image imgSniperTower = new Image(Config.SNIPER_T_PATH);
    private Image imgMachineGunTower = new Image(Config.MACHINE_GUN_T_PATH);

    public Handle(GameField field){
        this.field = field;
        iv = new ImageView();
        field.getRoot().getChildren().add(iv);
    }

    public void mouseClickedHandler(MouseEvent e){
        if (e.getSource() instanceof Button) {
            iv.setImage(null);
            if (inputType.isEmpty()) inputType.add(e.getSource());
            else {
                boolean hasAlreadyCliked = false;
                if (inputType.peek() == e.getSource()) hasAlreadyCliked = true;
                inputType.clear();

                if (!hasAlreadyCliked) inputType.add(e.getSource());
            }
        } else {
            int x = (int) e.getX()/Config.SIZE_TILE;
            int y = (int) e.getY()/Config.SIZE_TILE;

            if (x < field.getCol() && x >= 0 && y >= 0 && y < field.getRow() && !inputType.isEmpty()) {
                ArrayList<ArrayList<Integer>> bitmap = field.getBitMap();
                if (bitmap.get(y).get(x) == 0){
                    if (inputType.peek() == Store.normalTower) field.placeTower(new NormalTower(x, y));
                    else if (inputType.peek() == Store.sniperTower) field.placeTower(new SniperTower(x, y));
                    else field.placeTower(new MachineGunTower(x, y));
                    bitmap.get(y).set(x, 2);
                }
            }
            inputType.clear();
            iv.setImage(null);
        }
    }

    public void mouseMovedHandler(MouseEvent event){
        if (!inputType.isEmpty()){
            double x = event.getX();
            double y = event.getY();

            if (inputType.peek() == Store.normalTower) iv.setImage(imgNormalTower);
            else if (inputType.peek() == Store.sniperTower) iv.setImage(imgSniperTower);
            else if (inputType.peek() == Store.machineTower) iv.setImage(imgMachineGunTower);

            iv.setTranslateX(x - Config.SIZE_TILE*0.5);
            iv.setTranslateY(y - Config.SIZE_TILE*0.5);
        }
    }

    @Override
    public void handle(MouseEvent event) {
        if (MouseEvent.MOUSE_CLICKED == event.getEventType()) mouseClickedHandler(event);
        else if (MouseEvent.MOUSE_MOVED == event.getEventType()) mouseMovedHandler(event);
    }
}
