package note.save.app.savenote;

import android.animation.Animator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import note.save.app.savenote.activity.CreateNewNote;
import note.save.app.savenote.adapters.NoteListAdapter;
import note.save.app.savenote.animation.CollapseAnimation;
import note.save.app.savenote.animation.ExpandAnimation;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.fragments.RightMenuFragment;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RightMenuFragment.OnClickActions {
    private static final String TAG = MainActivity.class.getSimpleName();
    int k = 0;

    AppCompatActivity activity;
    RecyclerView note_list;
    LinearLayoutManager linearLayoutManager;
    ImageView filter_button;
    DatabaseHandler db;
    NoteListAdapter noteListAdapter;
    ImageView add_button;
    List<Note> notes = new ArrayList<>();
    RelativeLayout sliding_screen;

    FrameLayout.LayoutParams note_list_params;
    RightMenuFragment.OnClickActions onClickActions;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private int panelWidth;
    FrameLayout menuPanel;

    @Override
    public void onCloseButtonClick() {
        toggleMenuBar();
        setMenuIconView();
    }

    @Override
    public void onApplyButtonClick() {
        toggleMenuBar();
        updateNotes();
        setMenuIconView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initialize();
        initializeOnClickListner();
    }

    private void initialize() {
        activity = this;
        onClickActions = this;
        db = new DatabaseHandler(activity);
        linearLayoutManager = new LinearLayoutManager(activity);
        note_list = findViewById(R.id.note_list);
        note_list.setLayoutManager(linearLayoutManager);
        filter_button = findViewById(R.id.filter_button);
        add_button = findViewById(R.id.add_button);
        sliding_screen = findViewById(R.id.sliding_screen);
        menuPanel = findViewById(R.id.menuPanel);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        note_list_params = (FrameLayout.LayoutParams) sliding_screen.getLayoutParams();
        note_list_params.width = metrics.widthPixels;
        sliding_screen.setLayoutParams(note_list_params);

        panelWidth = (int) ((metrics.widthPixels) * -GeneralUtil.animationValue);
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
        if(isExpanded){
            toggleMenuBar();
        }else {
            k++;
            if (k == 1) {
                GeneralUtil.showMessage(getString(R.string.text_press_again_to_exit));
                try {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            k = 0;
                        }
                    }, getResources().getInteger(R.integer.double_back_press_time_out));
                } catch (Exception e) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteListAdapter = new NoteListAdapter(activity, db);
        note_list.setAdapter(noteListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotes();
        setMenuIconView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateNotes(){
        notes.clear();
        if (notes.size() == 0) {
            notes = db.getAllNotes();
            noteListAdapter.setNotes(notes);
            noteListAdapter.notifyDataSetChanged();
        }
    }

    private void initializeOnClickListner() {
        filter_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_button:
                if(!toggleMenuBar()){
                    GeneralUtil.showMessage(activity.getString(R.string.text_unable_to_perform_this_opeartion_right_now));
                }
                break;
            case R.id.add_button:
                startActivity(new Intent(activity, CreateNewNote.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    private boolean toggleMenuBar() {
        if (!isExpanded) {
            isExpanded = true;
            add_button.setVisibility(View.GONE);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            RightMenuFragment rightMenuFragment = new RightMenuFragment();
            rightMenuFragment.setOnClickActions(onClickActions);
            fragmentTransaction.replace(R.id.menuPanel,
                    rightMenuFragment);
            fragmentTransaction.commit();
            new ExpandAnimation(sliding_screen, panelWidth,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -GeneralUtil.animationValue, 0, 0.0f, 0,
                    0.0f);

        } else {
            isExpanded = false;
            add_button.setVisibility(View.VISIBLE);
            new CollapseAnimation(sliding_screen, panelWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, -GeneralUtil.animationValue,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
                    0, 0.0f);

        }
        return true;
    }

    private void setMenuIconView(){
        if(GeneralUtil.isStar() || GeneralUtil.isHearted()){
            filter_button.setSelected(true);
        }else {
            filter_button.setSelected(false);
        }
    }
}
