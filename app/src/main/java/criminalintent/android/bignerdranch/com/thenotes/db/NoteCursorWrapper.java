package criminalintent.android.bignerdranch.com.thenotes.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

import criminalintent.android.bignerdranch.com.thenotes.model.Note;

/**
 * Created by leonid on 01.09.2016.
 */
public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Note getNote(){
        String text = getString(getColumnIndex(DatabaseScheme.NotesTable.Cols.TEXT));
        Date date = new Date(Long.parseLong(getString(getColumnIndex(
                DatabaseScheme.NotesTable.Cols.DATE))));
        Note note = new Note();
        note.setTextNote(text);
        note.setDate(date);
        return note ;
    }
}
