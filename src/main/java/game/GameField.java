package game;

import game.Bullet.EffectEntity;
import game.Enemy.AbstractEnemy;
import game.Tile.Spawner.AbstractSpawner;
import game.Tile.Target;
import game.Tile.Tower.AbstractTower;
import game.Tile.UpdateEntity;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GameField {
    private int row;
    private int col;
    private ArrayList<AbstractEntity> entities = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> bitMap = new ArrayList<ArrayList<Integer>>();
    private ArrayList<AbstractEntity> spawnList = new ArrayList<>();
    private ArrayList<Double> wayPoint = new ArrayList<>();
    private boolean stillSpawn;
    private Player player;
    private Group root;
    private GraphicsContext gc;
    private Handle handler;

    public GameField(GameStage stage, Player player, GraphicsContext gc, Group root) {
        row = stage.getRow();
        col = stage.getCol();
        entities = stage.getEntities();
        bitMap = stage.getBitMap();
        this.player = player;
        this.gc = gc;
        this.root = root;
        handler = new Handle(this);
        stillSpawn = false;
        this.wayPoint = stage.getWayPoint();
        Store.createStore(this);
    }

    public ArrayList<AbstractEnemy> getEnemies() {
        ArrayList<AbstractEnemy> enemies = new ArrayList<>();
        for (AbstractEntity entity : entities) if (entity instanceof AbstractEnemy) enemies.add((AbstractEnemy) entity);
        return enemies;
    }

    public Group getRoot(){
        return root;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public ArrayList<ArrayList<Integer>> getBitMap(){
        return bitMap;
    }

    public Handle getHandler(){
        return handler;
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

    public void render(){
        for (AbstractEntity entity : entities)
            entity.render(gc);
    }

    public void placeTower(AbstractTower tower){
        int price = tower.getBuyCost();
        int playerCoin = player.getCoin();
        if (playerCoin >= price){
            entities.add(tower);
            player.setCoin(playerCoin - price);
        }
    }

    public boolean isContinue(){
        return (getEnemies().size()!=0 || stillSpawn);
    }

    public ArrayList<Double> getWayPoint(){
        return wayPoint;
    }
}
