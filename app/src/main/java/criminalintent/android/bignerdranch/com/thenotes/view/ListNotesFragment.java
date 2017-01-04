package criminalintent.android.bignerdranch.com.thenotes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import criminalintent.android.bignerdranch.com.thenotes.R;
import criminalintent.android.bignerdranch.com.thenotes.model.AllNotes;
import criminalintent.android.bignerdranch.com.thenotes.model.Note;

/**
 * Created by Леонид on 17.08.2016.
 */
public class ListNotesFragment extends Fragment  {
    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private AllNotes mAllNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mAllNotes = AllNotes.getInstance(getContext());
        if(mAllNotes.getNotes().size() == 0){
            View view = inflater.inflate(R.layout.empty_note,container,false);
            return view;
        }
        View view = inflater.inflate(R.layout.list_notes_fragment,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NoteAdapter(mAllNotes.getNotes());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_notes_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_item_new_note){
            Note note = new Note();
            mAllNotes.addNote(note);
            Intent intent = new Intent(getActivity(),NoteActivity.class);
            intent.putExtra(NoteActivity.NOTE,note);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(){
        List<Note> notes = mAllNotes.getNotes();
        if(mAdapter == null && mRecyclerView != null){
            mAdapter = new NoteAdapter(mAllNotes.getNotes());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if(mAdapter != null) {
                mAdapter.setNotes(notes);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    private class NoteHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView mNoteTitle;
        private TextView mDate;
        private Note mNote;
        public NoteHolder(View itemView) {
            super(itemView);
            mNoteTitle = (TextView)itemView.findViewById(R.id.note_title_text_view);
            mDate = (TextView)itemView.findViewById(R.id.date_text_view);
            itemView.setOnClickListener(this);
        }

        public void blindNote(Note note){
            mNote = note;
            String textNote = note.getTextNote();
            if(textNote != null) {
                if (textNote.length() > 60) {
                    textNote = textNote.substring(0, 50) + " ...";
                }
            }
            mNoteTitle.setText(textNote);
            mDate.setText(note.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),NoteActivity.class);
            intent.putExtra(NoteActivity.NOTE,mNote);
            startActivity(intent);
        }
    }
    private class NoteAdapter extends RecyclerView.Adapter{
        List<Note> mNotes ;

        public void setNotes(List<Note> mNotes) {
            this.mNotes = mNotes;
        }

        public NoteAdapter(List<Note> mNotes) {
            this.mNotes = mNotes;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_note,parent,false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((NoteHolder)holder).blindNote(mNotes.get(position));
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }
}
