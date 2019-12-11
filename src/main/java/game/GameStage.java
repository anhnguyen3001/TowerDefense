package game;

import game.Enemy.*;
import game.Tile.*;
import game.Tile.Spawner.*;
import game.Tile.Tower.*;
import game.helper.Asset;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameStage {
    private int row;
    private int col;
    private String maptype;
    private ArrayList<AbstractEntity> entities;
    private ArrayList<ArrayList<Double>> wayPoint = new ArrayList<>();
    private final int maxLevel;
    private int currentLevel;

    public GameStage(int row, int col, ArrayList<AbstractEntity> entities, String maptype,
                     ArrayList<ArrayList<Double>> wayPoint, int maxLevel, int currentLevel) {
        this.entities = entities;
        this.row = row;
        this.col = col;
        this.maptype = maptype;
        this.wayPoint = wayPoint;
        this.maxLevel = maxLevel;
        this.currentLevel = currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }

    public int getRow(){return row;}

    public int getCol(){return col;}

    public String getMaptype() {
        return maptype;
    }

    public final ArrayList<AbstractEntity> getEntities() {
        return entities;
    }

    public ArrayList<ArrayList<Double>> getWayPoint(){
        return wayPoint;
    }

    public static game.GameStage load(String path, Player player)  {
        try {
            Scanner sc = new Scanner(new File("src//main//java//game//" + path + ".txt"));

            String maptype = sc.nextLine();
            Asset.loadAsset(maptype);

            //Size of map
            String[] temp = sc.nextLine().split("x");
            int row = Integer.parseInt(temp[0]);
            int col = Integer.parseInt(temp[1]);

            //Map
            int[][] bitmap = new int[row][col];
            ArrayList<AbstractEntity> entities = new ArrayList<>();
            for (int i = 0; i < row; i++) {
                temp = sc.nextLine().split(" ");

                for (int j = 0; j < temp.length; j++) {
                    int bit = Integer.parseInt(temp[j]);
                    bitmap[i][j] = bit;

                    if (bit == 1) entities.add(new Road(j, i, bit));
                    else if (bit == 0) entities.add(new BlankLand(j, i, bit));
                    else entities.add(new Mountain(j, i, bit));
                }
            }

            ArrayList<ArrayList<Double>> wayPoint = new ArrayList<>();
            temp = sc.nextLine().split(" ");

            for (int i = 1; i < temp.length; i++) {
                boolean[][] visited = new boolean[row][col];
                for (int j = 0; j < row; j++)
                    for (int k = 0; k < col; k++) visited[j][k] = false;

                ArrayList<Double> road = new ArrayList<>();
                double curX = Double.parseDouble(temp[i]);
                double curY = Double.parseDouble(temp[++i]);
                double endX = Double.parseDouble(temp[++i]);
                double endY = Double.parseDouble(temp[++i]);
                road.add(curX);
                road.add(curY);
                visited[(int) curY][(int) curX] = true;
                double preX = curX;
                double preY = curY;

                int[][] delta = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                ArrayList<Double> junction = new ArrayList<>();
                while (curX != endX || curY != endY) {
                    double chooseX = curX;
                    double chooseY = curY;
                    int numOfRoad = 0;
                    boolean noRoadleft = true;
                    for (int j = 0; j < delta.length; j++) {
                        curX = preX + delta[j][0];
                        curY = preY + delta[j][1];

                        if (curX < col && 0 <= curX && curY < row && 0 <= curY && !visited[(int) curY][(int) curX]
                                && bitmap[(int) curY][(int) curX] == 1) {
                            numOfRoad++;
                            chooseX = curX;
                            chooseY = curY;
                            noRoadleft = false;
                        }
                    }

                    if (numOfRoad > 1) {
                        junction.add(preX);
                        junction.add(preY);
                    }

                    preX = chooseX;
                    preY = chooseY;
                    curX = chooseX;
                    curY = chooseY;
                    road.add(curX);
                    road.add(curY);
                    visited[(int) curY][(int) curX] = true;

                    if (noRoadleft && junction.size() != 0 && (chooseX != endX || chooseY != endY)) {
                        for (int j = 0; j < road.size(); j += 2)
                            if (road.get(j).equals(junction.get(0)) && road.get(j + 1).equals(junction.get(1))) {
                                for (int k = j + 2; k < road.size(); ) road.remove(k);
                                break;
                            }

                        preX = junction.get(0);
                        preY = junction.get(1);
                        curX = preX;
                        curY = preY;
                        junction.clear();
                    }
                }

                ArrayList<Double> waypoint = new ArrayList<>();
                for (int j = 0; j < road.size(); j+=2){
                    if (j == 0 || j == road.size()-2) {
                        waypoint.add(road.get(j));
                        waypoint.add(road.get(j+1));
                    } else if (!road.get(j-2).equals(road.get(j + 2)) && !road.get(j-1).equals(road.get(j+3))){
                        waypoint.add(road.get(j));
                        waypoint.add(road.get(j+1));
                    }
                }
                wayPoint.add(waypoint);
            }

            HashSet<String> target = new HashSet<>();
            for (ArrayList<Double> i:wayPoint){
                int size = i.size();
                target.add(Double.toString(i.get(size - 2)) + " " + Double.toString(i.get(size - 1)));
            }

            for (String s:target){
                String[] point = s.split(" ");
                entities.add(new Target(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
            }

            int maxLevel = 0;
            int currentLevel = 0;
            HashMap<String, Integer> spawner = new HashMap<>();

            while (sc.hasNext()) {
                temp = sc.nextLine().split(" ");

                if (temp[0].contains("Spawner")){
                    int index = 0;
                    if (!spawner.containsKey(temp[0])) spawner.put(temp[0], index);
                    else {
                        index = spawner.get(temp[0]) + 1;
                        spawner.replace(temp[0], index);
                    }

                    double x = wayPoint.get(index).get(0);
                    double y = wayPoint.get(index).get(1);
                    int tick = Integer.parseInt(temp[1]);
                    int delayTime = Integer.parseInt(temp[2]);
                    int numOfTurn = Integer.parseInt(temp[3]);

                    if (temp[0].equals("NormalSpawner")) entities.add(new NormalSpawner(x, y, tick, delayTime, numOfTurn));
                    else if (temp[0].equals("SmallerSpawner")) entities.add(new SmallerSpawner(x, y, tick, delayTime, numOfTurn));
                    else if (temp[0].equals("BossSpawner")) entities.add(new BossSpawner(x, y, tick, delayTime, numOfTurn));
                    else if (temp[0].equals("FlyingSpawner")) entities.add(new FlyingSpawner(x, y, tick, delayTime, numOfTurn));
                    else if (temp[0].equals("TankerSpawner")) entities.add(new TankerSpawner(x, y, tick, delayTime, numOfTurn));
                } else if (temp[0].equals("Player")){
                    String name = temp[1];
                    int live = Integer.parseInt(temp[2]);
                    int coin = Integer.parseInt(temp[3]);

                    player.setName(name);
                    player.setLive(live);
                    player.setCoin(coin);
                } else if (temp[0].equals("MaxLevel")){
                    maxLevel = Integer.parseInt(temp[1]);
                } else if (temp[0].equals("CurrentLevel")){
                    currentLevel = Integer.parseInt(temp[1]);
                } else if (temp[0].contains("Enemy")){
                    double startX = Double.parseDouble(temp[1]);
                    double startY = Double.parseDouble(temp[2]);
                    double endX = Double.parseDouble(temp[3]);
                    double endY = Double.parseDouble(temp[4]);
                    double x = Double.parseDouble(temp[5]);
                    double y = Double.parseDouble(temp[6]);
                    int blood = Integer.parseInt(temp[7]);
                    int waypointIndex = 0;

                    for (int i = 0; i < wayPoint.size(); i++){
                        int size = wayPoint.get(i).size();

                        if (wayPoint.get(i).get(0) == startX && wayPoint.get(i).get(1) == startY
                                && wayPoint.get(i).get(size - 2) == endX && wayPoint.get(i).get(size - 1) == endY) {
                            waypointIndex = i;
                            break;
                        }
                    }

                    if (temp[0].equals("NormalEnemy")) entities.add(new NormalEnemy(wayPoint.get(waypointIndex), x, y, blood, waypointIndex));
                    else if (temp[0].equals("SmallerEnemy")) entities.add(new SmallerEnemy(wayPoint.get(waypointIndex), x, y, blood, waypointIndex));
                    else if (temp[0].equals("BossEnemy")) entities.add(new BossEnemy(wayPoint.get(waypointIndex), x, y, blood, waypointIndex));
                    else if (temp[0].equals("TankerEnemy")) entities.add(new TankerEnemy(wayPoint.get(waypointIndex), x, y, blood, waypointIndex));
                    else if (temp[0].equals("FlyingEnemy")) entities.add(new FlyingEnemy(wayPoint.get(waypointIndex), x, y, blood, waypointIndex));
                } else if (temp[0].contains("Tower")){
                    double x = Double.parseDouble(temp[1]);
                    double y = Double.parseDouble(temp[2]);
                    int tick = Integer.parseInt(temp[3]);
                    int level = Integer.parseInt(temp[4]);

                    AbstractTower tower = null;
                    if (temp[0].equals("FreezeTower")) tower = new FreezeTower(x, y, tick, level);
                    else if (temp[0].equals("MachineGunTower")) tower = new MachineGunTower(x, y, tick, level);
                    else if (temp[0].equals("NormalTower")) tower = new NormalTower(x, y, tick, level);
                    else if (temp[0].equals("RocketTower")) tower = new RocketTower(x, y, tick, level);
                    else if (temp[0].equals("SniperTower")) tower = new SniperTower(x, y, tick, level);

                    if (tower != null) {
                        entities.add(tower);

                        ((BlankLand) entities.get((int) (y * col + x))).setTower(tower);
                        ((BlankLand) entities.get((int) (y * col + x))).setHasTower(true);
                    }
                }
            }
            sc.close();
            return new game.GameStage(row, col, entities, maptype, wayPoint, maxLevel, currentLevel);
        }catch(IOException e) {
            System.out.println("Can't load file GameStage");
        }
        return null;
    }
}