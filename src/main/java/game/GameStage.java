package game;

import game.Tile.*;
import game.Tile.Spawner.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameStage {
    private int row;
    private int col;
    private Player player;
    private ArrayList<AbstractEntity> entities;
    private ArrayList<ArrayList<Integer>> BitMap = new ArrayList<ArrayList<Integer>>();
    private ArrayList<Double> wayPoint = new ArrayList<>();
    private int maxLevel;

    public GameStage(int row, int col, ArrayList<AbstractEntity> entities,
                     ArrayList<ArrayList<Integer>> BitMap, Player player, ArrayList<Double> wayPoint, int maxLevel) {
        this.entities = entities;
        this.BitMap = BitMap;
        this.row = row;
        this.col = col;
        this.player = player;
        this.wayPoint = wayPoint;
        this.maxLevel = maxLevel;
    }

    public static GameStage load(String path) {
        try {
            Scanner sc = new Scanner(new File("src//main//java//game//demo.txt"));

            //Size of map
            String[] temp = sc.nextLine().split("x");
            int row = Integer.parseInt(temp[0]);
            int col = Integer.parseInt(temp[1]);

            //Map
            ArrayList<AbstractEntity> entities = new ArrayList<>();
            ArrayList<ArrayList<Integer>> BitMap = new ArrayList<ArrayList<Integer>>();
            Player player = null;
            int maxLevel = 0;
            double startX = 0;
            double startY = 0;
            double endX = 0;
            double endY = 0;
            for (int i = 0; i < row; i++) {
                temp = sc.nextLine().split(" ");

                ArrayList<Integer> value = new ArrayList<>();
                for (int j = 0; j < temp.length; j++) {
                    int bit = Integer.parseInt(temp[j]);
                    value.add(bit);
                    if (bit != 1 && bit != 0) entities.add(new Mountain(j, i, bit));
                    else if (bit == 1) entities.add(new Road(j, i, bit));
                    else entities.add(new BlankLand(j, i, bit));
                }
                BitMap.add(value);
            }

            while (sc.hasNext()) {
                temp = sc.nextLine().split(" ");
                if (temp[0].equals("Start")){
                    startX = Integer.parseInt(temp[1]);
                    startY = Integer.parseInt(temp[2]);
                } else if (temp[0].equals("NormalSpawner")) {
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    entities.add(new NormalSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("Target")) {
                    endX = Double.parseDouble(temp[1]);
                    endY = Double.parseDouble(temp[2]);
                    int health = Integer.parseInt(temp[3]);
                    entities.add(new Target(endX, endY,health));
                } else if (temp[0].equals("BossSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    entities.add(new BossSpawner(startX, startY, delayTime, numOfTurn));
                }else if (temp[0].equals("SmallerSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    entities.add(new SmallerSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("TankerSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    entities.add(new TankerSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("Player")){
                    int live = Integer.parseInt(temp[1]);
                    int coin = Integer.parseInt(temp[2]);

                    player = new Player(coin, live);
                } else if (temp[0].equals("Level")){
                    maxLevel = Integer.parseInt(temp[1]);
                }
            }

            //FindingPath
            ArrayList<Double> Path = new ArrayList<>();
            Integer[][] delta = {{1, 0}, {0,1}, {-1, 0}, {0,-1}};
            Path.add(startX);
            Path.add(startY);

            double curX = startX;
            double curY = startY;
            double preX = curX;
            double preY = curY;
            int preDirection = -1;
            while (curX != endX || curY != endY){
                for(int i = 0; i < delta.length; i++) {
                    int deltaX = delta[i][0];
                    int deltaY = delta[i][1];
                    if (preDirection != -1)
                        if (deltaX * -1 == delta[preDirection][0] && deltaY * -1 == delta[preDirection][1])
                            continue;

                    curX = preX + deltaX;
                    curY = preY + deltaY;
                    if (curX <  col && 0 <= curX && curY < row && 0 <= curY)
                         if (BitMap.get((int)curY).get((int)curX) == 1) {
                             preDirection = i;
                             Path.add(curX);
                             Path.add(curY);
                             preX = curX;
                             preY = curY;
                             break;
                         }
                }
            }

            //Build WayPoint
            ArrayList<Double> wayPoint = new ArrayList<>();
            for (int i = 0; i < Path.size(); i++){
                if (i == Path.size() - 2 || i == 0){
                    wayPoint.add(Path.get(i));
                    wayPoint.add(Path.get(++i));
                } else if (Path.get(i-2) != Path.get(i+2) && Path.get(i+3) != Path.get(i-1)) {
                    wayPoint.add(Path.get(i));
                    wayPoint.add(Path.get(++i));
                }
            }

            sc.close();
            return new GameStage(row, col, entities, BitMap, player, wayPoint, maxLevel);
        }catch(IOException e) {
            System.out.println("Can't load file GameStage");
        }
        return null;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getRow(){return row;}

    public int getCol(){return col;}

    public final ArrayList<AbstractEntity> getEntities() {
        return entities;
    }

    public ArrayList<ArrayList<Integer>> getBitMap(){
        return BitMap;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Double> getWayPoint(){
        return wayPoint;
    }

    public ArrayList<AbstractEntity> newLevel(String path) {
        ArrayList<AbstractSpawner> spawners = new ArrayList<>();

        for (AbstractEntity entity : entities) {
            if (!(entity instanceof AbstractTile)) entities.remove(entity);
            else if (entity instanceof AbstractSpawner) spawners.add((AbstractSpawner)entity);
        }

        try {
            Scanner sc = new Scanner(new File(path));

            while(sc.hasNext()){
                String[] temp = sc.nextLine().split(" ");

                if (temp[0].equals("NormalSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    for (AbstractSpawner spawner:spawners)
                        if (spawner instanceof NormalSpawner) {
                            spawner.setIntervalTime(delayTime);
                            spawner.setNumOfInterval(numOfTurn);
                            break;
                        }
                } else if (temp[0].equals("BossSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    for (AbstractSpawner spawner:spawners)
                        if (spawner instanceof BossSpawner) {
                            spawner.setIntervalTime(delayTime);
                            spawner.setNumOfInterval(numOfTurn);
                            break;
                        }
                } else if (temp[0].equals("SmallerSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    for (AbstractSpawner spawner:spawners)
                        if (spawner instanceof SmallerSpawner) {
                            spawner.setIntervalTime(delayTime);
                            spawner.setNumOfInterval(numOfTurn);
                            break;
                        }
                } else if (temp[0].equals("TankerSpawner")){
                    int delayTime = Integer.parseInt(temp[1]);
                    int numOfTurn = Integer.parseInt(temp[2]);
                    for (AbstractSpawner spawner:spawners)
                        if (spawner instanceof TankerSpawner) {
                            spawner.setIntervalTime(delayTime);
                            spawner.setNumOfInterval(numOfTurn);
                            break;
                        }
                }
            }
            sc.close();
            return entities;
        } catch (IOException e){
            System.out.println("Can't load new level");
        }

        return null;
    }
}