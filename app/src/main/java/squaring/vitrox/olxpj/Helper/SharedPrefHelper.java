package squaring.vitrox.olxpj.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private Boolean firstTime = null;
    private Context mContext;

    public SharedPrefHelper(Context context) {
        this.mContext = context;
    }

    public boolean isFirstTime() {

        if (firstTime == null) {
            SharedPreferences mPreferences = mContext.getSharedPreferences(Config.SHARED_PREFERENCES_AREA, Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean(Config.FIRST_TIME, true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean(Config.FIRST_TIME, false);
                editor.apply();
            }
        }
        return firstTime;
    }
}
