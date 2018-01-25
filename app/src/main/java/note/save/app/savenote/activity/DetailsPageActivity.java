package note.save.app.savenote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import note.save.app.savenote.R;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 23/01/18.
 */

public class DetailsPageActivity extends AppCompatActivity implements View.OnClickListener{
    final String TAG = SplashScreen.class.getSimpleName();
    Activity activity;
    ImageView back_button;
    TextView button_edit, note_title , last_updated_time, note_description;
    DatabaseHandler db ;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes_page);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initialize();
        initializeOnClickListner();
    }
    private void initialize(){
        activity = this;
        db = new DatabaseHandler(activity);
        back_button = findViewById(R.id.back_button);
        button_edit = findViewById(R.id.button_edit);
        note_title = findViewById(R.id.note_title);
        last_updated_time = findViewById(R.id.last_updated_time);
        note_description = findViewById(R.id.note_description);
    }
    private void initializeOnClickListner(){
        back_button.setOnClickListener(this);
        button_edit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int id = intent.getIntExtra(GeneralUtil.KEY_ID, 0);
        if(id !=0 ){
            Note note = db.getNote(id);
            if(note != null)
                setData(note);
            else{
                GeneralUtil.showMessage(getString(R.string.text_unable_to_perform_this_opeartion_right_now));
                finish();
            }
        }else{
            GeneralUtil.showMessage(getString(R.string.text_unable_to_perform_this_opeartion_right_now));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setData(Note note){
        this.note = note;
        note_title.setText(note.getTitle());
        note_description.setText(note.getDescription());
        last_updated_time.setText(getString(R.string.text_last_updated) + GeneralUtil.getFormattedDate(note.getLast_updated_time()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.button_edit:
                Intent intent = new Intent(activity, CreateNewNote.class);
                intent.putExtra(GeneralUtil.KEY_ID, note.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
