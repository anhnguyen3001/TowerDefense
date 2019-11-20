package game;

import game.Bullet.EffectEntity;
import game.Enemy.AbstractEnemy;
import game.Tile.BlankLand;
import game.Tile.Spawner.AbstractSpawner;
import game.Tile.Tower.AbstractTower;
import game.Tile.Tower.MachineGunTower;
import game.Tile.Tower.NormalTower;
import game.Tile.Tower.SniperTower;
import game.Tile.UpdateEntity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class GameField {
    private GameStage stage;
    private int currentLevel;
    private int maxLevel;
    private int row;
    private int col;
    private ArrayList<AbstractEntity> entities = new ArrayList<>();
    private ArrayList<AbstractEntity> spawnList = new ArrayList<>();
    private ArrayList<Double> wayPoint = new ArrayList<>();
    private boolean stillSpawn;
    private Player player;

    public GameField(GameStage stage, Player player) {
        this.stage = stage;
        row = stage.getRow();
        col = stage.getCol();
        entities = stage.getEntities();
        this.player = player;

        stillSpawn = false;
        this.wayPoint = stage.getWayPoint();
        maxLevel = stage.getMaxLevel();
        currentLevel = 1;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public ArrayList<AbstractEnemy> getEnemies() {
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        for (AbstractEntity entity : entities) if (entity instanceof AbstractEnemy) enemies.add((AbstractEnemy) entity);
        return enemies;
    }

    public ArrayList<AbstractEntity> getEntities() {
        return entities;
    }

    public ArrayList<Double> getWayPoint(){
        return wayPoint;
    }

    public void addSpawnList(AbstractEntity entity) {
        spawnList.add(entity);
    }

    public void update(){
        stillSpawn = false;
        //Update Entity:
        ArrayList<AbstractEntity> destroyList = new ArrayList<>();
        for (AbstractEntity entity: entities){
            if (entity instanceof UpdateEntity) {
                if (entity instanceof DestroyEntity && ((DestroyEntity) entity).isDestroyed()){
                    destroyList.add(entity);

                    if (entity instanceof AbstractEnemy)
                        if(((AbstractEnemy) entity).isAlive()) player.setLive(player.getLive()-1);
                        else player.setCoin(player.getCoin() + ((AbstractEnemy)entity).getReward());
                } else {
                    ((UpdateEntity) entity).update(this);

                    if (entity instanceof AbstractSpawner && ((AbstractSpawner) entity).stillSpawn())
                        stillSpawn = true;
                }
            }
        }

        for (AbstractEntity entity:entities)
            if (entity instanceof EffectEntity) ((EffectEntity) entity).doDamage();

        //DestroyEntity: enemy, bullet...
        if (destroyList.size() != 0) entities.removeAll(destroyList);

        //Spawn Entity
        if (spawnList.size() != 0) {
            entities.addAll(spawnList);
            spawnList.clear();
        }
    }

    public void render(GraphicsContext gc){
        for (AbstractEntity entity : entities)
            entity.render(gc);

        player.renderStatus(gc);
        renderLevel(gc);
    }

    public void sellTower(BlankLand land){
        entities.remove(land.getTower());
        player.setCoin(player.getCoin() + land.getTower().getSellValue());
        land.setHasTower(false);
        land.setTower(null);
    }

    public void upgradeTower(AbstractTower tower){
        if (tower.canUpgrade())
            if (player.getCoin() >= tower.getUpgradeCost()){
                tower.upgrade();
                player.setCoin(player.getCoin() - tower.getBuyCost());
            }
    }

    public void placeTower(int towerType, int x, int y){
        if (entities.get(col * y + x) instanceof BlankLand){
            BlankLand land = (BlankLand)entities.get(col * y + x);
            if (!land.isHasTower()){
                AbstractTower tower = null;

                if (towerType == Config.NORMAL_TOWER) tower = new NormalTower(x, y);
                else if (towerType == Config.SNIPER_TOWER) tower = new SniperTower(x, y);
                else tower = new MachineGunTower(x, y);

                int price = tower.getBuyCost();
                int playerCoin = player.getCoin();
                if (playerCoin >= price){
                    entities.add(tower);
                    player.setCoin(playerCoin - price);
                    land.setHasTower(true);
                    land.setTower(tower);
                }
            }
        }
    }
    public boolean isContinue(){
        return (getEnemies().size()!=0 || stillSpawn);
    }

    public void newLevel(){
        ++currentLevel;
        entities = stage.newLevel("src//main//java//game//LevelInfo//level" + currentLevel + ".txt");
    }

    public boolean endGame(){
        return (!isContinue() && currentLevel == maxLevel);
    }

    public void renderLevel(GraphicsContext gc){
        gc.fillText("Level: " + Integer.toString(currentLevel) + "/" + Integer.toString(maxLevel),
                2 * Config.SIZE_TILE, 10.7 * Config.SIZE_TILE);
        gc.setFont(Font.font("Bookman", 25));
        gc.setFill(Color.BLACK);
    }
}
