package game.helper;

import game.Config;
import javafx.scene.image.Image;

public class Asset {
    public static Image ROAD;
    public static Image BLANK_LAND;
    private static Image PATTERN1;
    private static Image PATTERN2;
    public static Image spawner;
    public static Image target;

    //Enemy
    public static Image BOSS_ENEMY;
    public static Image NORMAL_ENEMY;
    public static Image SMALLER_ENEMY;
    public static Image FLYING_ENEMY;
    public static Image TANKER_ENEMY;
    public static Image SHADOW_FLYING_ENEMY;

    //Tower
    public static Image NORMAL_TOWER;
    public static Image ROCKET_TOWER;
    public static Image FREEZE_TOWER;
    public static Image MACHINEGUNTOWER;
    public static Image SNIPER_TOWER;
    public static Image FULL_ROCKET_TOWER;

    public static void loadAsset(String maptype){
        String path = "file:src/main/resources/AssetsKit_2/PNG/Map/" + maptype + "\\";
        System.out.println(maptype);
        BLANK_LAND = new Image(path + "towerDefense_tile000.png");  //land
        ROAD = new Image(path + "towerDefense_tile001.png");  //road

        PATTERN1 = new Image(path + "towerDefense_tile002.png");  //pattern
        PATTERN2 = new Image(path + "towerDefense_tile003.png");

        spawner = new Image(path + "towerDefense_tile005.png");
        target = new Image(path + "towerDefense_tile004.png");

        path = "file:src/main/resources/AssetsKit_2/PNG/Default size/";
        //Enemy
        BOSS_ENEMY = new Image(path + "towerDefense_tile248.png");
        NORMAL_ENEMY = new Image(path + "towerDefense_tile245.png");
        TANKER_ENEMY = new Image(path + "towerDefense_tile246.png");
        FLYING_ENEMY = new Image(path + "towerDefense_tile270.png");
        SHADOW_FLYING_ENEMY = new Image(path + "towerDefense_tile293.png");
        SMALLER_ENEMY = new Image(path + "towerDefense_tile247.png");

        //Tower
        NORMAL_TOWER = new Image(path + "towerDefense_tile249.png");
        ROCKET_TOWER = new Image(path + "towerDefense_tile229.png");
        FREEZE_TOWER = new Image(path + "towerDefense_tile292.png");
        MACHINEGUNTOWER = new Image(path + "towerDefense_tile250.png");
        SNIPER_TOWER = new Image(path + "towerDefense_tile291.png");
        FULL_ROCKET_TOWER = new Image( path + "towerDefense_tile206.png");
    }

    public static Image getBaseImage(int type){
        switch (type){
            case (0): return BLANK_LAND;
            case (1): return ROAD;
            case (2): return PATTERN1;
            case (3): return PATTERN2;
        }
        return null;
    }
}
