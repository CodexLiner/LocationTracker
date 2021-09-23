package synceAdapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import UiActivities.MainActivity;

public class BootService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Intent intents = new Intent(getBaseContext(), MainActivity.class);
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intents);
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
    }
}
