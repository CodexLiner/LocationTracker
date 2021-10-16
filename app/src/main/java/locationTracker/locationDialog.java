package locationTracker;




import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.varbin.locationtracker.R;


public class locationDialog {
    public static void statusCheck(Context context) {
        Log.d("TAG", "statusCheck: ");
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("TAG", "statusCheck: false");
            buildAlertMessageNoGps(context);
        }
    }

    private static void buildAlertMessageNoGps(Context context) {
        Log.d("TAG", "statusCheck: inside");
        try{
            final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.myDialog );
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            Log.d("TAG", "statusCheck: after show");
        }catch (Exception e){
            Log.d("TAG", "statusCheck: emessgae"+e.getLocalizedMessage());
        }
    }
}
