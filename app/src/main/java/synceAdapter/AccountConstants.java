package synceAdapter;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AccountConstants {
    public static final String AUTHORITY = "com.bisapp.mycontentprovider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.varbin.locationtracker";
    // The account name
    public static final String ACCOUNT = "Location Tracker";
    public static final String SYNC_STARTED="Sync Started";
    public static final String SYNC_FINISHED="Sync Finished";
    public static  boolean mainThread ;
    public static boolean wifiStatus = false;
    public static final String getStatus = Build.ID;
    public static final String inActive = "setCommandInactive/";
    public static boolean isInactive;
//    public static String BASEURL = "https://mobitracker.in/index.php/Api/";
    public static String BASEURL = "https://mobitrack.varbin.com/index.php/Api/";
    public static boolean isaudioRecording;
    public static String getStatus (Context context){
        String s = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        WifiManager m_wm = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String m = m_wm.getConnectionInfo().getMacAddress();
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String m_deviceId = TelephonyMgr.getSimCountryIso();
        return s+"-D_ID-"+ Build.TYPE +"-V-"+m_deviceId;
    }
    public static final String[] permisions = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_SYNC_SETTINGS,
            Manifest.permission.WRITE_SYNC_SETTINGS,
            Manifest.permission.READ_SMS,

    };

}
