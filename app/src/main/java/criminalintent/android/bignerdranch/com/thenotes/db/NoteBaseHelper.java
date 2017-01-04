package criminalintent.android.bignerdranch.com.thenotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Leonid on 20.08.2016.
 */
public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";

    public NoteBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DatabaseScheme.NotesTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
                DatabaseScheme.NotesTable.Cols.TEXT + ", " +
                DatabaseScheme.NotesTable.Cols.DATE  + " )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
