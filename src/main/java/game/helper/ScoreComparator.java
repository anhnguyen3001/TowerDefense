package game.helper;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score a, Score b) {
        if (a.getLevel() < b.getLevel()) return 1;
        else if (a.getLevel() > b.getLevel()) return -1;
        return 0;
    }
}
