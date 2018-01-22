package note.save.app.savenote;

import android.app.Application;
import android.content.Context;

/**
 * Created by ashrafiqubal on 22/01/18.
 */

public class ApplicationClass extends Application {
    public static final String TAG = ApplicationClass.class.getSimpleName();
    private static Context context;

    private static ApplicationClass mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getParentContext(){
        return context;
    }

    public static synchronized ApplicationClass getInstance() {
        if (mInstance == null) {
            mInstance = new ApplicationClass();
        }
        return mInstance;
    }
}
