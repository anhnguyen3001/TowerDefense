package game;

import game.Tile.BlankLand;
import game.store.Store;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Handle implements EventHandler<MouseEvent> {
    private GameField field;
    private Store store;
    private Group root;
    private GraphicsContext gc;

    private int towerType;
    private boolean clickSellUpgrade;

    private ImageView ivTower;
    private ImageView ivSell;
    private ImageView ivUpgrade;

    private BlankLand land;

    public Handle(GameField field, Store store, Group root, GraphicsContext gc){
        this.field = field;
        this.store = store;
        this.root = root;
        this.gc = gc;

        towerType = 0;
        land = null;
        clickSellUpgrade = false;
        ivTower = new ImageView();
        this.root.getChildren().add(ivTower);

        ivSell = new ImageView(new Image(Config.SELL));
        ivSell.setFitHeight(35);
        ivSell.setFitWidth(35);

        ivUpgrade = new ImageView(new Image(Config.UPGRADE));
        ivUpgrade.setFitWidth(35);
        ivUpgrade.setFitHeight(35);
    }

    private void chooseTower(double mouseX, double mouseY){
        ivTower.setImage(null);
        if (store.normalTower.contains(mouseX, mouseY)){
            if (towerType == Config.NORMAL_TOWER) towerType = 0;
            else towerType = Config.NORMAL_TOWER;
            store.setTowerShow(Config.NORMAL_TOWER);
        } else if (store.sniperTower.contains(mouseX, mouseY)){
            if (towerType == Config.SNIPER_TOWER) towerType = 0;
            else towerType = Config.SNIPER_TOWER;
            store.setTowerShow(Config.SNIPER_TOWER);
        } else if (store.machinegunTower.contains(mouseX, mouseY)){
            if (towerType == Config.MACHINE_GUN_TOWER) towerType = 0;
            else towerType = Config.MACHINE_GUN_TOWER;
            store.setTowerShow(Config.MACHINE_GUN_TOWER);
        }
    }

    private void buildTower(int mouseX, int mouseY){
        field.placeTower(towerType, mouseX, mouseY);
        towerType = 0;
        ivTower.setImage(null);
        if (land != null && land.getTower().isHasClicked()) land.getTower().setHasClicked(false);
    }

    private void chooseUpgradeSell(int mouseX, int mouseY){
        AbstractEntity entity = field.getEntities().get(field.getCol() * mouseY + mouseX);
        if (entity instanceof BlankLand) {
            land = (BlankLand) entity;
            if (land.isHasTower()) {
                ivSell.setX((entity.getX() - 0.25) * Config.SIZE_TILE);
                ivSell.setY((entity.getY() - 0.5) * Config.SIZE_TILE);

                ivUpgrade.setX((entity.getX() + 0.65) * Config.SIZE_TILE);
                ivUpgrade.setY((entity.getY() - 0.5) * Config.SIZE_TILE);

                root.getChildren().addAll(ivSell, ivUpgrade);
                clickSellUpgrade = true;
                land.getTower().setHasClicked(true);                //Set to show info table of tower
            } else land = null;
        }
    }

    private void upgradeSell(double mouseX, double mouseY){
        double ivSellX = ivSell.getX()/Config.SIZE_TILE;
        double ivSellY = ivSell.getY()/Config.SIZE_TILE;
        double ivSellW = ivSell.getFitWidth()/Config.SIZE_TILE;
        double ivSellH = ivSell.getFitHeight()/Config.SIZE_TILE;

        double ivUpgradeX = ivUpgrade.getX()/Config.SIZE_TILE;
        double ivUpgradeY = ivUpgrade.getY()/Config.SIZE_TILE;
        double ivUpgradeW = ivUpgrade.getFitWidth()/Config.SIZE_TILE;
        double ivUpgradeH = ivUpgrade.getFitHeight()/Config.SIZE_TILE;

        if (mouseX >= ivSellX && mouseX <= ivSellX + ivSellW && mouseY >= ivSellY && mouseY <= ivSellY + ivSellH){
            field.sellTower(land);
            System.out.println("5");
        } else if (mouseX >= ivUpgradeX && mouseX <= ivUpgradeX + ivUpgradeW
                && mouseY >= ivUpgradeY && mouseY <= ivUpgradeY + ivUpgradeH){
            field.upgradeTower(land.getTower());
            System.out.println("6");
        }
        land.getTower().setHasClicked(false);
        clickSellUpgrade = false;
        land = null;
        root.getChildren().removeAll(ivSell, ivUpgrade);
    }

    public void mouseClickedHandler(double mouseX, double mouseY){
        if (mouseX >= field.getCol()) {
            chooseTower(mouseX, mouseY);
            if (clickSellUpgrade){
                clickSellUpgrade = false;
                root.getChildren().removeAll(ivSell, ivUpgrade);
            }
            if (land != null && land.getTower().isHasClicked()) land.getTower().setHasClicked(false);
        }
        else {
            if (!clickSellUpgrade && towerType == 0) chooseUpgradeSell((int) mouseX, (int) mouseY);
            else {
                if (land != null && land.getTower().isHasClicked()) land.getTower().setHasClicked(false);
                if (towerType != 0) buildTower((int) mouseX, (int) mouseY);
                else if (clickSellUpgrade) upgradeSell(mouseX, mouseY);
            }
        }
    }


    public void mouseMovedHandler(double mouseX, double mouseY){
        if (towerType != 0){
            if (towerType == Config.NORMAL_TOWER) ivTower.setImage(store.normalTower.getImg());
            else if (towerType == Config.SNIPER_TOWER) ivTower.setImage(store.sniperTower.getImg());
            else if (towerType == Config.MACHINE_GUN_TOWER) ivTower.setImage(store.machinegunTower.getImg());
            ivTower.setTranslateX((mouseX - 0.5) * Config.SIZE_TILE);
            ivTower.setTranslateY((mouseY - 0.5) * Config.SIZE_TILE);
        }
    }

    public void handle(MouseEvent event) {
        double mouseX = event.getX()/Config.SIZE_TILE;
        double mouseY = event.getY()/Config.SIZE_TILE;

        if (MouseEvent.MOUSE_CLICKED == event.getEventType()) mouseClickedHandler(mouseX, mouseY);
        else if (MouseEvent.MOUSE_MOVED == event.getEventType()) mouseMovedHandler(mouseX, mouseY);

        if (!store.normalTower.contains(mouseX, mouseY) && !store.sniperTower.contains(mouseX,mouseY)
            && !store.machinegunTower.contains(mouseX,mouseY)) store.setTowerShow(0);
    }
}
