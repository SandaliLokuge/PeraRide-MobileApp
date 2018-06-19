package peraride.ce.pdn.edu.peraride;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import java.util.HashMap;
import java.util.List;


public class RideApplication extends MultiDexApplication {

    private static RideApplication application;
    private HashMap<String, List<String>> msgMap;

    private boolean showUpdateActivity;

    public static RideApplication getInstance() {
        return application;
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        msgMap = new HashMap<>();
        MultiDex.install(this);
    }

    public String getVersion() {
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Error", e.getMessage());
        }
        return versionName;
    }

    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }

    public void restartApplication() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean isShowUpdateActivity() {
        return showUpdateActivity;
    }

    public void setShowUpdateActivity(boolean showUpdateActivity) {
        this.showUpdateActivity = showUpdateActivity;
    }

    public HashMap<String, List<String>> getMsgMap() {
        return msgMap;
    }
}
