package criminalintent.android.bignerdranch.com.thenotes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import criminalintent.android.bignerdranch.com.thenotes.R;
import criminalintent.android.bignerdranch.com.thenotes.model.AllNotes;
import criminalintent.android.bignerdranch.com.thenotes.model.Note;


public class NoteFragment extends Fragment {
    private Note mNote;
    private AllNotes mAllNotes;
    private EditText mText;

    public static NoteFragment getInstance(Note note){
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(NoteActivity.NOTE,note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_save_note){
            mNote.setTextNote(mText.getText().toString());
            mAllNotes.updateNote(mNote);
            backActivity();
            return true;
        }
        if(item.getItemId() == R.id.menu_item_delete_note){
            mAllNotes.deleteNote(mNote);
            backActivity();
            return true;
        }
        if(item.getItemId() == R.id.menu_item_send_note){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,mNote.getTextNote());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void backActivity(){
        Intent intent = new Intent(getActivity(),ListNotesActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note,container,false);
        mText = (EditText)view.findViewById(R.id.text_note);
        mNote = (Note) getArguments().getSerializable(NoteActivity.NOTE);
        mText.setText(mNote.getTextNote());
        mAllNotes = AllNotes.getInstance(getActivity());
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        mAllNotes.updateNote(mNote);
    }
}
