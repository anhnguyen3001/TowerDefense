package game;

import game.Enemy.AbstractEnemy;
import game.Enemy.SmallerEnemy;
import game.Tile.Mountain;
import game.Tile.Road;
import game.Tile.Spawner.BossSpawner;
import game.Tile.Spawner.NormalSpawner;
import game.Tile.Spawner.SmallerSpawner;
import game.Tile.Spawner.TankerSpawner;
import game.Tile.Target;

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

    public GameStage(int row, int col, ArrayList<AbstractEntity> entities,
                     ArrayList<ArrayList<Integer>> BitMap, Player player, ArrayList<Double> wayPoint) {
        this.entities = entities;
        this.BitMap = BitMap;
        this.row = row;
        this.col = col;
        this.player = player;
        this.wayPoint = wayPoint;
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
            ArrayList<Double> wayPoint = new ArrayList<>();
            Player player = null;

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

                    if (bit != 1) entities.add(new Mountain(j, i, bit));
                    else entities.add(new Road(j, i));
                }
                BitMap.add(value);
            }

            while (sc.hasNext()) {
                temp = sc.nextLine().split(" ");
                if (temp[0].equals("NormalSpawner")) {
                    startX = Double.parseDouble(temp[1]);
                    startY = Double.parseDouble(temp[2]);
                    int delayTime = Integer.parseInt(temp[3]);
                    int numOfTurn = Integer.parseInt(temp[4]);
                    entities.add(new NormalSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("Target")) {
                    endX = Double.parseDouble(temp[1]);
                    endY = Double.parseDouble(temp[2]);
                    int health = Integer.parseInt(temp[3]);
                    entities.add(new Target(endX, endY,health));
                } else if (temp[0].equals("BossSpawner")){
                    startX = Double.parseDouble(temp[1]);
                    startY = Double.parseDouble(temp[2]);
                    int delayTime = Integer.parseInt(temp[3]);
                    int numOfTurn = Integer.parseInt(temp[4]);
                    entities.add(new BossSpawner(startX, startY, delayTime, numOfTurn));
                }else if (temp[0].equals("SmallerSpawner")){
                    startX = Double.parseDouble(temp[1]);
                    startY = Double.parseDouble(temp[2]);
                    int delayTime = Integer.parseInt(temp[3]);
                    int numOfTurn = Integer.parseInt(temp[4]);
                    entities.add(new SmallerSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("TankerSpawner")){
                    startX = Double.parseDouble(temp[1]);
                    startY = Double.parseDouble(temp[2]);
                    int delayTime = Integer.parseInt(temp[3]);
                    int numOfTurn = Integer.parseInt(temp[4]);
                    entities.add(new TankerSpawner(startX, startY, delayTime, numOfTurn));
                } else if (temp[0].equals("WayPoint")) {
                    for (int i = 1; i < temp.length; i++){

                        String[] bit = temp[i].split(",");
                        double x = Integer.parseInt(bit[0].substring(1, bit[0].length()));
                        double y = Integer.parseInt(bit[1].substring(0, bit[1].length() - 1));

                        wayPoint.add(x);
                        wayPoint.add(y);
                    }
                } else if (temp[0].equals("Player")){
                    int live = Integer.parseInt(temp[1]);
                    int coin = Integer.parseInt(temp[2]);

                    player = new Player(coin, live);
                }
            }
            sc.close();
            return new GameStage(row, col, entities, BitMap, player, wayPoint);
        }catch(IOException e) {
            System.out.println("Can't load file GameStage");
        }
        return null;
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
}