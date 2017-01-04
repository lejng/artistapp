package criminalintent.android.bignerdranch.com.thenotes.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import criminalintent.android.bignerdranch.com.thenotes.R;
import criminalintent.android.bignerdranch.com.thenotes.model.Note;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE = "criminalintent.android." +
            "bignerdranch.com.thenotes.note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.note_fragment_container);
        if(fragment == null){
            fragment = NoteFragment.getInstance((Note) getIntent().getSerializableExtra(NOTE));
            fm.beginTransaction().add(R.id.note_fragment_container,fragment).commit();
        }
    }
}
