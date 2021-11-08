package AutoCallRecorder;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.content.ContentValues.TAG;

import synceAdapter.SharedClass;

public class RecorderService extends Service {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
    MediaRecorder mRecorder;
    public static AudioManager audioManager;
    String mNumber;
    String sPath;
    String sName;
    String fName;

    private String idCall;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent==null){
            return START_NOT_STICKY;
        }
        int commandType = intent.getIntExtra("commandType", 0);
        if (commandType== 0) return START_NOT_STICKY;
        switch (commandType){
            case Constants.RECORDING_ENABLED: {
                // enable recording here
                Log.d(TAG, "onStartCommand: start recording");
                startRecording();
                break;
            }
            case Constants.RECORDING_DISABLED:{
                // realease recorder here
                Log.d(TAG, "onStartCommand: stop recording");
                break;
            }
            case Constants.STATE_INCOMING_NUMBER:{
                break;
            }
            case Constants.STATE_CALL_START:{
                // start recording
                Log.d(TAG, "stopRecording: again start recording");
                startRecording();
                break;
            }
            case Constants.STATE_CALL_END:{
                //stop recording here
                Log.d(TAG, "onStartCommand: again stop recording");
                stopRecording();
                break;
            }
            //end of switch statement
        }

        return START_NOT_STICKY;
    }

    private void stopRecording() {

        if (mRecorder == null){
            return;
        }
       try {
           mRecorder.stop();
       }catch (Exception e){
           Log.d(TAG, "stopRecording Error: "+e);
       }
        startService(new Intent(this , StateRecieverClas.class));
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }
    private void startRecording() {

        try {
            sPath = mTools.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mNumber = intent.getStringExtra("number");
        String mPath = sPath+"/"+"mNumber"+"-"+String.valueOf(System.currentTimeMillis()+".mp3");
        if (mRecorder!=null){
            mRecorder =  null;
        }
        mRecorder = new MediaRecorder();
        mRecorder.reset();
        fName = sPath+"/"+"recording"+".mp3";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED);
        }
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setAudioSamplingRate(16000);
        mRecorder.setOutputFile(mPath);

        try {
            mRecorder.prepare();

        } catch (IOException e) {
            Log.d(TAG, "onReceivenext: record e "+e);
            e.printStackTrace();
        }
        Log.d(TAG, "onReceivenext: record started");
        try {
            mRecorder.start();
        }catch (Exception e){
            Log.d(TAG, "onReceivenext: record failed"+e);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onReceiveState: record stoped");
        super.onDestroy();
        Log.d(TAG, "onReceiveState: record stoped");
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
    }
}
