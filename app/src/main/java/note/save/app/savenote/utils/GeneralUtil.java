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

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class GeneralUtil {
    public static final String timeFormat = "h:mm a";
    public static final String DATE_FORMAT_dd_slash_mm_slash_yyyy = "MMM dd";
    public static final String DATE_FORMAT_EEEE = "EEEE";
    public static final String KEY_ID = "note.save.app.savenote.utils.KEY_ID";



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
    public static String getFormattedTime(long unixTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_dd_slash_mm_slash_yyyy);
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat);
        SimpleDateFormat simpleDayFormat = new SimpleDateFormat(DATE_FORMAT_EEEE);
        Date date = new Date(unixTime);
        Date today = new Date();
        if (today.getTime() - unixTime < 24 * 60 * 60 * 1000)
            return "Today at " + simpleTimeFormat.format(date);
        else if (today.getTime() - unixTime < 2 * 24 * 60 * 60 * 1000)
            return "Yesterday at " + simpleTimeFormat.format(date);
        else if(today.getTime() - unixTime < 7 * 24 * 60 * 60 * 1000)
            return simpleDayFormat.format(date) + " at " + simpleTimeFormat.format(date);
        else
            return simpleDateFormat.format(date) + " at " + simpleTimeFormat.format(date);
    }

    public static String getFormattedDate(long unixTime) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(unixTime);

        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return "Today at " + DateFormat.format(timeFormat, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday at " + DateFormat.format(timeFormat, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) < 7 ) {
            return DateFormat.format(DATE_FORMAT_EEEE, smsTime).toString() + " at " + DateFormat.format(timeFormat, smsTime).toString();
        } else {
            return DateFormat.format(DATE_FORMAT_dd_slash_mm_slash_yyyy, smsTime).toString() + " at " + DateFormat.format(timeFormat, smsTime).toString();
        }
    }

}
