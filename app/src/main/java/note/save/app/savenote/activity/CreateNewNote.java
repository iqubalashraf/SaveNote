package note.save.app.savenote.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import note.save.app.savenote.R;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by cliffex-14 on 22/1/18.
 */

public class CreateNewNote extends AppCompatActivity implements View.OnClickListener {
    final String TAG = CreateNewNote.class.getSimpleName();
    Activity activity;
    EditText note_title, note_description;
    TextView button_save;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_note_activity);
        initialize();
        initializeOnClickListener();
    }

    @Override
    public void onBackPressed() {
        if (validatedata(false)) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle("Alert");
            dialog.setMessage("Save this note");
            dialog.setCancelable(true);
            dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    if (db.addNote(note_title.getText().toString().trim(),
                            note_description.getText().toString().trim(), 0, 0, 0)) {
                        GeneralUtil.showMessage("Note saved successfully.");
                    }
                    activity.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
            dialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    activity.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
            dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }else {
            activity.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void initialize() {
        activity = this;
        db = new DatabaseHandler(activity);
        note_title = findViewById(R.id.note_title);
        note_description = findViewById(R.id.note_description);
        button_save = findViewById(R.id.button_save);
    }

    private void initializeOnClickListener() {
        button_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                if (validatedata(true)) {
                    if (db.addNote(note_title.getText().toString().trim(),
                            note_description.getText().toString().trim(), 0, 0, 0)) {
                        GeneralUtil.showMessage("Note saved successfully.");
                        finish();
                    } else {
                        GeneralUtil.showMessage("Unable to save note.");
                    }
                }
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
}
