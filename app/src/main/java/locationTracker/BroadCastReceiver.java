package locationTracker;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationResult;
public class BroadCastReceiver extends BroadcastReceiver {
    public  static  final String s = "edmt.dev.googlelocationbackground.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!= null){
            String  A = intent.getAction();
            if (s.equals(A)){
                LocationResult result = LocationResult.extractResult(intent);
                if (result!=null){
                    Location location = result.getLastLocation();
                    String StringLoc = "" + location.getLatitude() + "/" + location.getLongitude();
                    try {
                        Log.d("TAG", "onReceive: "+StringLoc);
                      //  here we have to call api
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}