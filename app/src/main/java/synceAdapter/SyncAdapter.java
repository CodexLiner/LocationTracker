package synceAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import locationTracker.ForegroundServices;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver contentResolver;
    private final AccountManager mAccountManager;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        Log.d("TAG", "onPerformSync: unt ");
        contentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent serviceIntent = new Intent(context, ForegroundServices.class);
            serviceIntent.putExtra("inputExtra", "Background Task Is Running");
            context.startForegroundService(serviceIntent);
        }else {
            context.startService(new Intent(context , ForegroundServices.class));
        }
        if (AccountConstants.mainThread == false){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent serviceIntent = new Intent(context, ForegroundServices.class);
                serviceIntent.putExtra("inputExtra", "Background Task Is Running");
                context.startForegroundService(serviceIntent);
            }else {
                context.startService(new Intent(context , ForegroundServices.class));
            }
        }

    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {


    }
}
