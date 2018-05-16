package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michael on 16/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "highscores.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HighScores.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HighScores.COLUMN_NAME);

        onCreate(sqLiteDatabase);
    }

    public long saveScore(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HighScores.COLUMN_NAME, name);
        values.put(HighScores.COLUMN_SCORE, score);

        long id = db.insert(HighScores.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public HighScores getScore(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(HighScores.TABLE_NAME, new String[]{HighScores.COLUMN_ID, HighScores.COLUMN_NAME, HighScores.COLUMN_SCORE}, HighScores.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        HighScores score = new HighScores(cursor.getInt(cursor.getColumnIndex(HighScores.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(HighScores.COLUMN_NAME)),cursor.getInt(cursor.getColumnIndex(HighScores.COLUMN_SCORE)));

        cursor.close();

        return score;
    }


}
