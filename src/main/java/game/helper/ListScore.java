package game.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListScore {
    private ArrayList<Score> scores = new ArrayList<>();
    private String maptype;
    private int maxEntries;

    public ListScore(String maptype){
        maxEntries = 8;
        this.maptype = maptype;
        createHighScoreList();
    }

    public ArrayList<Score> getScores(){
        return scores;
    }

    private boolean isAdd(int level){
        int size = scores.size();
        if (size < maxEntries || scores.get(size - 1).getLevel() < level) return true;
        return false;
    }

    private void addHighScore(Score player){
        if (scores.size() < maxEntries) {
            scores.add(player);
        }
        else {
            int lowestLevel = scores.get(maxEntries-1).getLevel();
            if (player.getLevel() > lowestLevel){
                scores.remove(maxEntries - 1);
                scores.add(player);
            }
        }
        scores.sort(new ScoreComparator());
    }

    private void setID(){
        for (int i = 0; i < scores.size(); i++){
            if (i == 0) scores.get(i).setId(1);
            else if (scores.get(i).getLevel() == scores.get(i-1).getLevel()) scores.get(i).setId(scores.get(i-1).getId());
            else scores.get(i).setId(scores.get(i-1).getId() + 1);
        }
    }

    private void createHighScoreList(){
        try{
            Scanner sc = new Scanner(new File("src//main//java//game//HighScore//hs" + maptype + ".txt"));

            while (sc.hasNext()){
                String[] s = sc.nextLine().split(" ");
                scores.add(new Score(s[0], Integer.parseInt(s[1])));
            }
            setID();

            sc.close();
        }catch (IOException e){
            System.out.println("Can't load highscore list");
        }
    }

    public void saveScore(String name, int level){
        if (isAdd(level)){
            Score player = new Score(name, level);
            addHighScore(player);
            writeToFile();
        }
    }

    private void writeToFile(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src//main//java//game//HighScore//hs" + maptype + ".txt"));
            for (Score score:scores){
                writer.write(score.toString() + "\n");
            }

            writer.close();
        }catch (IOException e){
            System.out.println("can't save highscore");
        }
    }
}
