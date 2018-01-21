package note.save.app.savenote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Handler;

import note.save.app.savenote.MainActivity;
import note.save.app.savenote.R;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class SplashScreen extends AppCompatActivity {
    final String TAG = SplashScreen.class.getSimpleName();
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        activity = this;
        GeneralUtil.updateStatusBarColor(activity, getString(R.string.white_color_string));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                proceedUsaully();
            }
        }, getResources().getInteger(R.integer.SPLASH_SCREEN_TIMEOUT));
    }
    private void proceedUsaully() {
        startActivity(new Intent(activity, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
