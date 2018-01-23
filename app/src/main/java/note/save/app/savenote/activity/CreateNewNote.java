package note.save.app.savenote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import note.save.app.savenote.R;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 22/01/18.
 */

public class CreateNewNote extends AppCompatActivity implements View.OnClickListener {
    final String TAG = CreateNewNote.class.getSimpleName();
    Activity activity;
    ImageView back_button;
    EditText note_title, note_description;
    TextView button_save, button_undo;
    DatabaseHandler db;
    Note note = null;
    String initial_note_title = "", initial_note_description = "";

    boolean isNoteSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_note_activity);
        initialize();
        initializeOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int id = intent.getIntExtra(GeneralUtil.KEY_ID, 0);
        if (id != 0) {
            Note note = db.getNote(id);
            if (note != null)
                setData(note);

        }else {
            button_undo.setVisibility(View.GONE);
        }
    }

    private void setData(Note note) {
        this.note = note;
        initial_note_title = note.getTitle();
        initial_note_description = note.getDescription();
        note_title.setText(note.getTitle());
        note_description.setText(note.getDescription());
    }

    @Override
    public void onBackPressed() {
        if (validateForBackPressed()) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle("Alert");
            dialog.setCancelable(true);
            /*dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    saveNote();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });*/
            dialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
            dialog.setNeutralButton("Continue editing", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        } else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void initialize() {
        activity = this;
        db = new DatabaseHandler(activity);
        note_title = findViewById(R.id.note_title);
        note_description = findViewById(R.id.note_description);
        button_save = findViewById(R.id.button_save);
        back_button = findViewById(R.id.back_button);
        button_undo = findViewById(R.id.button_undo);

    }

    private void initializeOnClickListener() {
        button_save.setOnClickListener(this);
        back_button.setOnClickListener(this);
        button_undo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                if (validatedata(true)) {
                    saveNote();
                }
                break;
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.button_undo:
                if (note != null)
                    setData(note);
                else
                    GeneralUtil.showMessage("Nothing to undo.");
                break;
        }
    }

    private boolean validatedata(boolean showMsg) {
        if (note_title.getText().toString().trim().equals("")) {
            if (showMsg)
                GeneralUtil.showMessage("Enter note title");
            return false;
        }
        if (note_description.getText().toString().trim().equals("")) {
            if (showMsg)
                GeneralUtil.showMessage("Enter note description");
            return false;
        }
        return true;
    }

    private boolean validateForBackPressed() {
        if (!note_title.getText().toString().trim().equals(initial_note_title)) {
            return true;
        }
        if (!note_description.getText().toString().trim().equals(initial_note_description)) {
            return true;
        }
        return false;
    }

    private void saveNote() {
        isNoteSaved = true;
        if (note != null) {
            if (db.updateNote(note.getId(),
                    note_title.getText().toString().trim(),
                    note_description.getText().toString().trim())) {
                GeneralUtil.showMessage("Note updated successfully.");
                finish();
            } else {
                GeneralUtil.showMessage("Unable to update note.");
            }
        } else {
            if (db.addNote(note_title.getText().toString().trim(),
                    note_description.getText().toString().trim())) {
                GeneralUtil.showMessage("Note saved successfully.");
                finish();
            } else {
                GeneralUtil.showMessage("Unable to save note.");
            }
        }
    }
}
