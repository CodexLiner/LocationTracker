package AutoCallRecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class StateRecieverClas extends BroadcastReceiver {
    static boolean isRocrdingStarted;
    public static String mNumber;
    public static String mName;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt("numOfCalls", 0).apply();
        boolean switchCheckOn = true;
        if (switchCheckOn) {
            try {
                Log.d(TAG, "onReceivenext: start");
                Bundle extras = intent.getExtras();
                String phoneState = extras.getString(TelephonyManager.EXTRA_STATE);
                Log.d(TAG, " onReceive: " + phoneState);
                String extrString = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                Toast.makeText(context, "Recording " + phoneState, Toast.LENGTH_SHORT).show();

                if (extras != null) {
                    dispacthExtra(context , intent , mNumber , extrString);

                    if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                    } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)/*&& pref.getInt("numOfCalls",1)==1*/) {

                        int j = sharedPreferences.getInt("numOfCalls", 0);
                        sharedPreferences.edit().putInt("numOfCalls", ++j).apply();
                        Log.d(TAG, "onReceivenext: j " + j);
                        mNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        Log.d(TAG, "onReceivenext: mobile" + mNumber);
                        if (sharedPreferences.getInt("numOfCalls", 1) == 1) {

                            Intent startIntent = new Intent(context, RecorderService.class);
                            startIntent.putExtra("number", mNumber);
                            context.startService(startIntent);
                            Log.d(TAG, "onReceivenext: inside sae");

                            //name=new CommonMethods().getContactName(phoneNumber,context);
//
//                            int serialNumber = pref.getInt("serialNumData", 1);
//                            new DatabaseManager(context).addCallDetails(new CallDetails(serialNumber, phoneNumber, new CommonMethods().getTIme(), new CommonMethods().getDate()));
//
//                            List<CallDetails> list = new DatabaseManager(context).getAllDetails();
//                            for (CallDetails cd : list) {
//                                String log = "Serial Number : " + cd.getSerial() + " | Phone num : " + cd.getNum() + " | Time : " + cd.getTime1() + " | Date : " + cd.getDate1();
//                                Log.d("Database ", log);
//                            }


                            //recordStarted=true;
//                            pref.edit().putInt("serialNumData", ++serialNumber).apply();
                            sharedPreferences.edit().putBoolean("recordStarted", true).apply();
                        }

                    } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                        int k = sharedPreferences.getInt("numOfCalls", 1);
                        sharedPreferences.edit().putInt("numOfCalls", --k).apply();
                        int l = sharedPreferences.getInt("numOfCalls", 0);

                        isRocrdingStarted = sharedPreferences.getBoolean("recordStarted", false);

                        if (isRocrdingStarted && l == 0) {
                            context.stopService(new Intent(context, RecorderService.class));
                            Log.d(TAG, "onReceivenext: stoped");
                            sharedPreferences.edit().putBoolean("recordStarted", false).apply();

                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void dispacthExtra(Context context, Intent intent, String phoneNumber, String extraState) {
        if (extraState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_CALL_START));
            } else {
                context.startService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_CALL_START));
            }
        } else if (extraState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_CALL_END));
            } else {
                context.startService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_CALL_END));
            }
        } else if (extraState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            if (phoneNumber == null)
                phoneNumber = intent.getStringExtra(
                        TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_INCOMING_NUMBER)
                        .putExtra("phoneNumber", phoneNumber));
            } else {
                context.startService(new Intent(context, RecorderService.class)
                        .putExtra("commandType", Constants.STATE_INCOMING_NUMBER)
                        .putExtra("phoneNumber", phoneNumber));
            }
        }
    }

}
