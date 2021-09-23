package synceAdapter;

import android.os.Build;

public class AccountConstants {
    public static final String AUTHORITY = "com.bisapp.mycontentprovider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.varbin.locationtracker";
    // The account name
    public static final String ACCOUNT = "Location Tracker";
    public static final String SYNC_STARTED="Sync Started";
    public static final String SYNC_FINISHED="Sync Finished";
    public static  boolean mainThread = false;
    public static boolean wifiStatus = false;
    public static final String getStatus = "getCommand/"+ "QKQ1.200114.006";
    public static final String inActive = "setCommandInactive/"+ "QKQ1.200114.006/";
    public static boolean isInactive;
    public static boolean isaudioRecording;

}
