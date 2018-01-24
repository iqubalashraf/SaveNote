package note.save.app.savenote.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import note.save.app.savenote.ApplicationClass;
import note.save.app.savenote.R;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class GeneralUtil {
    public static final String timeFormat = "h:mm a";
    public static final String DATE_FORMAT_dd_slash_mm_slash_yyyy = "MMM dd";
    public static final String DATE_FORMAT_EEEE = "EEEE";
    public static final String KEY_ID = "note.save.app.savenote.utils.KEY_ID";
    public static final float animationValue = 0.45f;
    public static final int ANIMATION_TIME_OUT = 400;



    public static void updateStatusBarColor(Activity activity, String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    public static void showMessage(String message) {
        Toast.makeText(ApplicationClass.getParentContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedDate(long unixTime) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(unixTime);

        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return ApplicationClass.getParentContext().getString(R.string.text_today_at) + DateFormat.format(timeFormat, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return ApplicationClass.getParentContext().getString(R.string.text_yesterday_at) + DateFormat.format(timeFormat, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) < 7 ) {
            return DateFormat.format(DATE_FORMAT_EEEE, smsTime).toString() + " at " + DateFormat.format(timeFormat, smsTime).toString();
        } else {
            return DateFormat.format(DATE_FORMAT_dd_slash_mm_slash_yyyy, smsTime).toString() + " at " + DateFormat.format(timeFormat, smsTime).toString();
        }
    }

}
