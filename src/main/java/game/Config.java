package game;

public class Config {
	public static final String GAME_TITLE = "TowerDefense";

	//Window Dimension
	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 700;
	public static final int SIZE_TILE = 64;

	//BitMap
	public static final int land = 0;
	public static final int road = 1;
	public static final int PATTERN = 2;

	//Store
	public static final int NORMAL_TOWER = 1;
	public static final int SNIPER_TOWER = 2;
	public static final int MACHINE_GUN_TOWER = 3;
	public static final int ROCKET_TOWER = 4;
	public static final int FREEZE_TOWER = 5;

	//Menu
	public static final int ENTER_NAME = 1;
	public static final int NEW_GAME = 2;
	public static final int CONTINUE = 3;
	public static final int HIGHSCORE = 4;

	//Enemy
	//NormalEnemy
	public static final int NORMAL_E_Blood = 70;
	public static final double NORMAL_E_Speed = 0.05;
	public static final int NORMAL_E_Armor = 5;
	public static final int NORMAL_E_Reward = 10;

	//TankerEnemy
	public static final int TANKER_E_Blood = 200;
	public static final double TANKER_E_Speed = 0.02;
	public static final int TANKER_E_Armor = 10;
	public static final int TANKER_E_Reward = 15;

	//SmallerEnemy
	public static final int SMALLER_E_Blood = 20;
	public static final double SMALLER_E_Speed = 0.06;
	public static final int SMALLER_E_Armor = 3;
	public static final int SMALLER_E_Reward = 5;

	//BossEnemy
	public static final int BOSS_E_Blood = 550;
	public static final double BOSS_E_Speed = 0.03;
	public static final int BOSS_E_Armor = 20;
	public static final int BOSS_E_Reward = 50;

	//FlyingEnemy
	public static final int FLYING_E_Blood = 550;
	public static final double FLYING_E_Speed = 0.05;
	public static final int FLYING_E_Armor = 20;
	public static final int FLYING_E_Reward = 50;

	//Direction
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
	public static int SNIPER_T_DAMAGE = 45;
	public static int SNIPER_T_Speed = 40;
	public static int SNIPER_T_Cost = 45;

	//MachineGunTower
	public static double MACHINEGUN_T_Range = 1.5;
	public static int MACHINEGUN_T_DAMAGE = 25;
	public static int MACHINEGUN_T_Speed = 13;
	public static int MACHINEGUN_T_Cost = 35;

	//RocketTower
	public static double ROCKET_T_Range = 2;
	public static int ROCKET_T_DAMAGE = 35;
	public static int ROCKET_T_Speed = 25;
	public static int ROCKET_T_Cost = 40;

	//FreezeTower
	public static double FREEZE_T_Range = 2;
	public static int FREEZE_T_DAMAGE = 15;
	public static int FREEZE_T_Speed = 20;
	public static int FREEZE_T_Cost = 35;
	public static int DELAY_DAMAGE = 20;
	public static double DELAY_RATIO = 0.1;

	//Bullet
	//Normal Bullet
	public static double NORMAL_BULLET_SPEED = 0.3;
	//Machine Gun Bullet
	public static double MACHINEGUN_BULLET_SPEED = 0.4;
	//Sniper Bullet
	public static double SNIPER_BULLET_SPEED = 0.2;
	//Sniper Bullet
	public static double ROCKET_BULLET_SPEED = 0.2;
	//Freeze Bullet
	public static double FREEZE_BULLET_SPEED = 0.2;

	//IMGPath
	public static final String PATH_MAP = "file:src/main/resources/AssetsKit_2/PNG/Default size/";
	public static final String PATH_BUTTON = "file:src/main/resources/Button/";

	public static final String ROAD = PATH_MAP + "towerDefense_tile093.png";
	public static final String MOUNTAIN = PATH_MAP + "towerDefense_tile024.png";

	//BaseTower Path
	public static final String BASE_SNIPER_T_PATH = PATH_MAP + "towerDefense_tile268.png";
	public static final String BASE_FREEZE_T_PATH = PATH_MAP + "towerDefense_tile269.png";

	//Bullet Path
	public static final String NORMAL_BULLET_PATH = PATH_MAP + "towerDefense_tile295.png";
	public static final String MACHINE_BULLET_PATH = PATH_MAP + "towerDefense_tile298.png";
	public static final String SNIPER_BULLET_PATH = PATH_MAP + "towerDefense_tile297.png";
	public static final String ROCKET_BULLET_PATH = PATH_MAP + "towerDefense_tile252.png";
	public static final String FREEZE_BULLET_PATH = PATH_MAP + "towerDefense_tile275.png";

	//Button Game
	public static final String BUTTON_NEXT_WAVE = PATH_BUTTON + "Nextwave.png";
	public static final String BUTTON_START_GAME = PATH_BUTTON + "Start.png";
	public static final String BUTTON_HIGH_SCORE = PATH_BUTTON + "HighScore.png";
	public static final String BUTTON_QUIT_GAME = PATH_BUTTON + "QuitGame.png";
	public static final String BUTTON_CONTINUE = PATH_BUTTON + "Continue.png";
	public static final String BUTTON_PAUSE = PATH_BUTTON + "Pause.png";
	public static final String BUTTON_RESUME = PATH_BUTTON + "Resume.png";
	public static final String MUSIC_ON = PATH_BUTTON + "on.png";
	public static final String MUSIC_OFF = PATH_BUTTON + "off.png";

	//EndGame
	public static final String YOU_WIN = "file:src/main/resources/AssetsKit_2/youwin.png";
	public static final String YOU_LOSE = "file:src/main/resources/AssetsKit_2/youlose.png";

	//Upgrade Sell
	public static final String UPGRADE = PATH_MAP + "towerDefense_tile289.png";
	public static final String SELL = PATH_MAP + "towerDefense_tile287.png";

	//Sound
	public static final String GAME_MUSIC = "src/main/resources/Music/ingame.mp3";
	public static final String BOOM_SOUND = "src/main/resources/Music/boom.mp3";
	public static final String ROCKET_BULLET_SOUND = "src/main/resources/Music/rocketFire.mp3";
	public static final String SHOOT_SOUND = "src/main/resources/Music/shoot.mp3";
	public static final String LOSE_GAME = "src/main/resources/Music/lose.mp3";
	public static final String WIN_GAME = "src/main/resources/Music/win.mp3";
}