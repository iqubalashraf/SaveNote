package note.save.app.savenote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import note.save.app.savenote.activity.CreateNewNote;
import note.save.app.savenote.adapters.NoteListAdapter;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    int k = 0;

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
        noteListAdapter = new NoteListAdapter(activity, db);
        note_list = findViewById(R.id.note_list);
        note_list.setLayoutManager(linearLayoutManager);
        note_list.setAdapter(noteListAdapter);
        filter_button  = findViewById(R.id.filter_button);
        add_button = findViewById(R.id.add_button);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (noteListAdapter != null) {
            noteListAdapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (noteListAdapter != null) {
            noteListAdapter.restoreStates(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        k++;
        if (k == 1) {
            GeneralUtil.showMessage("Press again to exit.");
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        k = 0;
                    }
                }, getResources().getInteger(R.integer.double_back_press_time_out));
            } catch (Exception e) {
                finish();
            }
        }else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes.clear();
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
                startActivity(new Intent(activity, CreateNewNote.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
