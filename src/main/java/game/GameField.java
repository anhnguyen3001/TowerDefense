package game;

import game.Bullet.AbstractBullet;
import game.Bullet.EffectEntity;
import game.Enemy.AbstractEnemy;
import game.Tile.*;
import game.Tile.Spawner.*;
import game.Tile.Tower.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameField {
    private int currentLevel;
    private int maxLevel;
    private int row;
    private int col;
    private String maptype;
    private ArrayList<AbstractEntity> entities = new ArrayList<>();
    private ArrayList<AbstractEntity> spawnList = new ArrayList<>();
    private ArrayList<ArrayList<Double>> wayPoint = new ArrayList<>();
    private ArrayList<AbstractEntity> destroyList = new ArrayList<>();
    private boolean stillSpawn;
    private Player player;

    public GameField(GameStage stage, Player player) {
        row = stage.getRow();
        col = stage.getCol();
        entities = stage.getEntities();
        this.player = player;
        maptype = stage.getMaptype();
        stillSpawn = false;
        this.wayPoint = stage.getWayPoint();
        maxLevel = stage.getMaxLevel();
        currentLevel = stage.getCurrentLevel();
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

    public ArrayList<ArrayList<Double>> getWayPoint(){
        return wayPoint;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void addSpawnList(AbstractEntity entity) {
        spawnList.add(entity);
    }

    public ArrayList<AbstractEntity> getDestroyList(){
        return destroyList;
    }

    public ArrayList<AbstractEntity> getSpawnList() {
        return spawnList;
    }

    public String getMaptype(){
        return maptype;
    }

    public void update(){
        //DestroyEntity
        destroyList.clear();
        for (AbstractEntity entity:entities){
            if(entity instanceof DestroyEntity && ((DestroyEntity) entity).isDestroyed()){
                if (entity instanceof AbstractEnemy && !((AbstractEnemy) entity).isAlive())
                    player.setCoin(player.getCoin() + ((AbstractEnemy) entity).getReward());
                destroyList.add(entity);
            }
        }
        //DestroyEntity: enemy, bullet...
        if (destroyList.size() != 0) entities.removeAll(destroyList);

        stillSpawn = false;
        //Update Entity:
        for (AbstractEntity entity: entities){
            if (entity instanceof UpdateEntity) {
                ((UpdateEntity) entity).update(this);

                if (entity instanceof AbstractSpawner && ((AbstractSpawner) entity).stillSpawn())
                    stillSpawn = true;
            }
        }

        for (AbstractEntity entity:entities)
            if (entity instanceof EffectEntity) ((EffectEntity) entity).doDamage();

        //Spawn Entity
        if (spawnList.size() != 0) {
            entities.addAll(spawnList);
            spawnList.clear();
        }
    }

    public void render(GraphicsContext gc){
        for (AbstractEntity entity : entities)
            entity.render(gc);

        for (AbstractEntity entity:destroyList) {
            if (entity instanceof AbstractEnemy && !((AbstractEnemy) entity).isAlive()) {
                Image boom = new Image("file:src/main/resources/AssetsKit_2/boom.gif");
                gc.drawImage(boom,entity.getX()*Config.SIZE_TILE,entity.getY()*Config.SIZE_TILE);
            }
        }

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

                if (towerType == Config.NORMAL_TOWER) tower = new NormalTower(x, y, 0, 1);
                else if (towerType == Config.SNIPER_TOWER) tower = new SniperTower(x, y, 0, 1);
                else if (towerType == Config.MACHINE_GUN_TOWER) tower = new MachineGunTower(x, y, 0, 1);
                else if (towerType == Config.ROCKET_TOWER) tower = new RocketTower(x, y, 0, 1);
                else if (towerType == Config.FREEZE_TOWER) tower = new FreezeTower(x, y, 0, 1);

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

    public boolean endGame(){
        return (!isContinue() && currentLevel == maxLevel);
    }

    public void renderLevel(GraphicsContext gc){
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Level: " + Integer.toString(currentLevel) + "/" + Integer.toString(maxLevel),
                2 * Config.SIZE_TILE, 10.7 * Config.SIZE_TILE);
        gc.setFont(Font.font("Bookman", 25));
        gc.setFill(Color.BLACK);
    }

    public void newLevel() {
        ArrayList<AbstractSpawner> spawners = new ArrayList<>();

        for (AbstractEntity entity : entities)
            if (entity instanceof AbstractSpawner) {
                spawners.add((AbstractSpawner)entity);
            }

        try {
            Scanner sc = new Scanner(new File("src//main//java//game//LevelInfo//" + maptype + "//level" + Integer.toString(++currentLevel) + ".txt"));
            HashMap<String, Integer> spawner = new HashMap<>();

            while (sc.hasNext()) {
                String[] temp = sc.nextLine().split(" ");

                if (temp[0].contains("Spawner")){
                    int index = 0;
                    if (!spawner.containsKey(temp[0])) spawner.put(temp[0], index);
                    else {
                        index = spawner.get(temp[0]) + 1;
                        spawner.replace(temp[0], index);
                    }

                    double x = wayPoint.get(index).get(0);
                    double y = wayPoint.get(index).get(1);
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);

                    for (AbstractSpawner spawn: spawners)
                        if (spawn.getX() == x && spawn.getY() == y){
                            if ((temp[0].equals("NormalSpawner") && spawn instanceof NormalSpawner)
                                    || (temp[0].equals("BossSpawner") && spawn instanceof BossSpawner)
                                    || (temp[0].equals("FlyingSpawner") && spawn instanceof FlyingSpawner)
                                    || (temp[0].equals("SmallerSpawner") && spawn instanceof SmallerSpawner)
                                    || (temp[0].equals("TankerSpawner") && spawn instanceof TankerSpawner)){
                                spawn.setNumOfInterval(numOfTurn);
                                spawn.setIntervalTime(delayTime);
                            }
                        }
                }
            }
            sc.close();
        } catch (IOException e){
            System.out.println("Can't load new level");
        }
    }

    public int[][] convert2DtoBitMap(){
        int[][] bitmap = new int[row][col];

        int size = row * col;
        for (AbstractEntity entity:entities){
            if (entity instanceof Road){
                bitmap[(int)entity.getY()][(int)entity.getX()] = ((Road) entity).getId();
                --size;
            } else if (entity instanceof Mountain){
                bitmap[(int)entity.getY()][(int)entity.getX()] = ((Mountain) entity).getId();
                --size;
            } else if (entity instanceof BlankLand){
                bitmap[(int)entity.getY()][(int)entity.getX()] = ((BlankLand) entity).getId();
                --size;
            }

            if(size < 0) break;
        }

        return bitmap;
    }
}
