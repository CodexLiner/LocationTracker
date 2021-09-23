package synceAdapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * A bound Service that instantiates the authenticator
 * when started.
 */
public class BookAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private AuthSynceAdapter mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create a new authenticator object
        mAuthenticator = new AuthSynceAdapter(this);
        Log.d("TAG", "onPerformSync AA: ");
    }

    /**
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}