package note.save.app.savenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import note.save.app.savenote.adapters.NoteListAdapter;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.sql.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    AppCompatActivity activity;
    RecyclerView note_list;
    LinearLayoutManager linearLayoutManager;
    ImageView filter_button;
    DatabaseHandler db ;
    NoteListAdapter noteListAdapter;
    ImageView add_button;
    List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeOnClickListner();
    }
    private void initialize(){
        activity = this;
        db = new DatabaseHandler(activity);
        linearLayoutManager = new LinearLayoutManager(activity);
        noteListAdapter = new NoteListAdapter(activity);
        note_list = findViewById(R.id.note_list);
        note_list.setLayoutManager(linearLayoutManager);
        note_list.setAdapter(noteListAdapter);
        filter_button  = findViewById(R.id.filter_button);
        add_button = findViewById(R.id.add_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(notes.size() == 0){
            notes = db.getAllNotes();
            noteListAdapter.setNotes(notes);
            noteListAdapter.notifyDataSetChanged();
        }
    }

    private void initializeOnClickListner(){
        filter_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.filter_button:
                break;
            case R.id.add_button:
                db.addNote("First Note", "This is enough description", 1,1,0);
                break;
        }
    }
}
