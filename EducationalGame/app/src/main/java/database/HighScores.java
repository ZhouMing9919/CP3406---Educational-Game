package database;

/**
 * Created by Michael on 16/05/2018.
 */

public class HighScores {
    public static final String TABLE_NAME = "highscores";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    private int id;
    private String name;
    private int score;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_SCORE + " INTEGER" + ")";

    public HighScores() {

    }

    public HighScores(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }
}

