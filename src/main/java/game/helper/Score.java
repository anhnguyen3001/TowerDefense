package game.helper;

public class Score{
    private String name;
    private int level;
    private int id;

    public Score(String name, int level){
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public String toString(){
        return name + " " + Integer.toString(level);
    }
}
