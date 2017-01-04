package criminalintent.android.bignerdranch.com.thenotes.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import criminalintent.android.bignerdranch.com.thenotes.db.DatabaseScheme.NotesTable;
import criminalintent.android.bignerdranch.com.thenotes.db.NoteBaseHelper;
import criminalintent.android.bignerdranch.com.thenotes.db.NoteCursorWrapper;

/**
 * Created by Leonid on 20.08.2016.
 */
public class AllNotes {
    private static AllNotes instance;
    private SQLiteDatabase mDatabase;
    private AllNotes(Context context){
        mDatabase = new NoteBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }
    public static AllNotes getInstance(Context context){
        if (instance == null) {
            synchronized(AllNotes.class) {
                if(instance == null) {
                    instance = new AllNotes(context);
                }
            }
        }
        return instance;
    }

    public int getCount(){
        return 0;
    }

    public Note getNote(int position){
        return null;
    }

    public void addNote(Note note){
        ContentValues value = getContentValue(note);
        mDatabase.insert(NotesTable.NAME,null,value);
    }

    public void updateNote(Note note){
        ContentValues value = getContentValue(note);
        String date = String.valueOf(note.getDate().getTime());
        mDatabase.update(NotesTable.NAME, value ,NotesTable.Cols.DATE + " = ?",
                new String[]{date});
    }

    public void deleteNote(Note note){
        String date = String.valueOf(note.getDate().getTime());
        mDatabase.delete(NotesTable.NAME,NotesTable.Cols.DATE + " = ?", new String[]{date});
    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursorWrapper = queryNotes(null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                notes.add(cursorWrapper.getNote());
                cursorWrapper.moveToNext();
            }

        }finally {
            cursorWrapper.close();
        }
        return notes;
    }

    private NoteCursorWrapper queryNotes(String whereClause,String[] whereArgs){
        Cursor cursor = mDatabase.query(
                NotesTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new NoteCursorWrapper(cursor);
    }

    private static ContentValues getContentValue(Note note){
        ContentValues value = new ContentValues();
        value.put(NotesTable.Cols.DATE,String.valueOf(note.getDate().getTime()));
        value.put(NotesTable.Cols.TEXT,note.getTextNote());
        return value;
    }
}
