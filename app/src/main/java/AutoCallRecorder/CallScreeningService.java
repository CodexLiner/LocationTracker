package AutoCallRecorder;

import android.os.Build;
import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CallScreeningService  extends android.telecom.CallScreeningService {
    @Override
    public void onScreenCall(@NonNull Call.Details callDetails) {

    }
}
