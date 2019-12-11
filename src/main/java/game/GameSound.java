package game;

import game.Bullet.AbstractBullet;
import game.Bullet.RocketBullet;
import game.Enemy.AbstractEnemy;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class GameSound {
    private static MediaPlayer gameMusic = new MediaPlayer(new Media(new File(Config.GAME_MUSIC).toURI().toString()));

    public static void playSound(ArrayList<AbstractEntity> spawnList, ArrayList<AbstractEntity> destroyList){
        for (AbstractEntity entity:spawnList){
            if (entity instanceof AbstractBullet){
                if (entity instanceof RocketBullet) {
                    MediaPlayer rocketBullet = new MediaPlayer(new Media(new File(Config.ROCKET_BULLET_SOUND).toURI().toString()));
                    rocketBullet.setVolume(1);
                    rocketBullet.setAutoPlay(true);
                    rocketBullet.play();
                }
                else {
                    MediaPlayer shootSound = new MediaPlayer(new Media(new File(Config.SHOOT_SOUND).toURI().toString()));
                    shootSound.play();
                    shootSound.setVolume(1);
                }
            }
        }

        for (AbstractEntity entity:destroyList)
            if (entity instanceof AbstractEnemy) {
                if (!((AbstractEnemy) entity).isAlive()) {
                    MediaPlayer boomSound = new MediaPlayer(new Media(new File(Config.BOOM_SOUND).toURI().toString()));
                    boomSound.play();
                    boomSound.setVolume(1);
                }
            }
    }

    public static void gameMusic(){
        gameMusic.setVolume(0.7);
        gameMusic.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                gameMusic.seek(Duration.ZERO);
            }
        });
        gameMusic.setAutoPlay(true);

    }

    public static void muteGameMusic(){
        gameMusic.setMute(true);
    }

    public static void startGameMusic(){
        gameMusic.setMute(false);
    }
}
