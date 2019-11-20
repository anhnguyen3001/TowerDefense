package game;

public class Config {
	public static final String GAME_TITLE = "TowerDefense";
	public static final double ELAPSE_TIME = 1000000000/30;

	//Window Dimension
	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 700;
	public static final int SIZE_TILE = 64;
	
	//Store
	public static final int NORMAL_TOWER = 1;
	public static final int SNIPER_TOWER = 2;
	public static final int MACHINE_GUN_TOWER = 3;

	//Enemy
	//NormalEnemy
	public static final int NORMAL_E_Blood = 70;
	public static final double NORMAL_E_Speed = 0.05;
	public static final int NORMAL_E_Armor = 5;
	public static final int NORMAL_E_Reward = 10;

	//TankerEnemy
	public static final int TANKER_E_Blood = 250;
	public static final double TANKER_E_Speed = 0.02;
	public static final int TANKER_E_Armor = 15;
	public static final int TANKER_E_Reward = 15;

	//SmallerEnemy
	public static final int SMALLER_E_Blood = 20;
	public static final double SMALLER_E_Speed = 0.1;
	public static final int SMALLER_E_Armor = 0;
	public static final int SMALLER_E_Reward = 5;

	//BossEnemy
	public static final int BOSS_E_Blood = 600;
	public static final double BOSS_E_Speed = 0.03;
	public static final int BOSS_E_Armor = 25;
	public static final int BOSS_E_Reward = 50;

	//EnemyDirection
	public static final int END_OF_ROAD = -1;
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;

	//Tower
	//NormalTower
	public static double NORMAL_T_Range = 2;
	public static int NORMAL_T_DAMAGE = 20;
	public static int NORMAL_T_Speed = 20;
	public static int NORMAL_T_Cost = 25;

	//SniperTower
	public static double SNIPER_T_Range = 3;
	public static int SNIPER_T_DAMAGE = 50;
	public static int SNIPER_T_Speed = 40;
	public static int SNIPER_T_Cost = 45;

	//MachineGunTower
	public static double MACHINEGUN_T_Range = 1.5;
	public static int MACHINEGUN_T_DAMAGE = 30;
	public static int MACHINEGUN_T_Speed = 13;
	public static int MACHINEGUN_T_Cost = 35;

	//Bullet
		//Normal Bullet
	public static double NORMAL_BULLET_SPEED = 0.3;
		//Machine Gun Bullet
	public static double MACHINEGUN_BULLET_SPEED = 0.4;
		//Sniper Bullet
	public static double SNIPER_BULLET_SPEED = 0.2;
	
	//IMGPath
	public static final String PATH_MAP = "file:src/main/resources/AssetsKit_2/PNG/Default size/";
	public static final String PATH_BUTTON = "file:src/main/resources/Button/";
	public static final String PATH = "file:src/main/resources/AssetsKit_2/Map/";

	public static final String[] MAP_SERIES = {
			"towerDefense_tile024.png", "towerDefense_tile093.png", "towerDefense_tile001.png",
			"towerDefense_tile002.png", "towerDefense_tile003.png", "towerDefense_tile004.png",
	};

	//Target SpawnerPath
	public static final String TARGET_PATH = PATH_MAP + "towerDefense_tile063.png";
	public static final String SPAWNER_PATH = PATH_MAP + "towerDefense_tile049.png";

	//Enemy Path
	public static final String NORMAL_E_PATH = PATH_MAP + "towerDefense_tile245.png";
	public static final String BOSS_E_PATH = PATH_MAP + "towerDefense_tile248.png";
	public static final String SMALLER_E_PATH = PATH_MAP + "towerDefense_tile247.png";
	public static final String TANKER_E_PATH = PATH_MAP + "towerDefense_tile246.png";

	//Tower Path
	public static final String NORMAL_T_PATH = PATH_MAP + "towerDefense_tile249.png";
	public static final String MACHINE_GUN_T_PATH = PATH_MAP + "towerDefense_tile250.png";
	public static final String SNIPER_T_PATH = PATH_MAP + "towerDefense_tile291.png";

	//BaseTower Path
	public static final String BASE_SNIPER_T_PATH = PATH_MAP + "towerDefense_tile268.png";

	//Bullet Path
	public static final String NORMAL_BULLET_PATH = PATH_MAP + "towerDefense_tile295.png";
	public static final String MACHINE_BULLET_PATH = PATH_MAP + "towerDefense_tile298.png";
	public static final String SNIPER_BULLET_PATH = PATH_MAP + "towerDefense_tile297.png";

	//Button Game
    public static final String BUTTON_QUIT = PATH_BUTTON + "quit.png";
    public static final String BUTTON_NEXT_WAVE = PATH_BUTTON + "Nextwave.png";
    public static final String BUTTON_START_GAME = PATH_BUTTON + "Start.png";
    public static final String BUTTON_HIGH_SCORE = PATH_BUTTON + "HighScore.png";
    public static final String BUTTON_QUIT_GAME = PATH_BUTTON + "QuitGame.png";

    //EndGame
	public static final String YOU_WIN = "file:src/main/resources/AssetsKit_2/youwin.png";
	public static final String YOU_LOSE = "file:src/main/resources/AssetsKit_2/youlose.png";

    //Upgrade Sell
	public static final String UPGRADE = PATH + "towerDefense_tile289.png";
	public static final String SELL = PATH + "towerDefense_tile287.png";
}