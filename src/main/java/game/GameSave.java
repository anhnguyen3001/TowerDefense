package game;

import game.Enemy.AbstractEnemy;
import game.Tile.Spawner.AbstractSpawner;
import game.Tile.Tower.AbstractTower;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameSave {
    public static void saveGame(GameField field) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src//main//java//game//SaveGame.txt"));

            String maptype = field.getMaptype();
            writer.write(maptype + "\n");

            //Size
            int row = field.getRow();
            int col = field.getCol();
            String size = Integer.toString(row) + "x" + Integer.toString(col) + "\n";
            writer.write(size);

            //Map Matrix
            int[][] bitmap = field.convert2DtoBitMap();
            for (int i = 0; i < row; i++) {
                String value = "";
                for (int j = 0; j < col; j++)
                    value += Integer.toString(bitmap[i][j]) + " ";
                writer.write(value + "\n");
            }

            String startEnd = "StartEnd ";
            ArrayList<ArrayList<Double>> wayPoint = field.getWayPoint();
            for (int i = 0; i < wayPoint.size(); i++){
                int length = wayPoint.get(i).size();
                startEnd += Double.toString(wayPoint.get(i).get(0)) + " " + Double.toString(wayPoint.get(i).get(1))
                        + " " + Double.toString(wayPoint.get(i).get(length - 2)) + " " + Double.toString(wayPoint.get(i).get(length - 1)) + " ";
            }
            startEnd += "\n";
            writer.write(startEnd);

            ArrayList<AbstractEntity> entities = field.getEntities();
            for (AbstractEntity entity:entities){
                if (entity instanceof AbstractTower || entity instanceof AbstractEnemy || entity instanceof AbstractSpawner)
                    writer.write(entity.toString());
            }

            //Player
            writer.write(field.getPlayer().toString());

            //Level
            writer.write("MaxLevel " + Integer.toString(field.getMaxLevel()) + "\n");
            writer.write("CurrentLevel " + Integer.toString(field.getCurrentLevel()));

            writer.close();
        } catch (IOException e){
            System.out.println("Can't save game");
        }
    }
}
