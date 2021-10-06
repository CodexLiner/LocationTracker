package callDetails;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

import com.varbin.locationtracker.APIs.CreaterClass;

import models.multiListClass;

public class lastCall {

    public static void ValidatLog(Context context){
        multiListClass mc = logModel(context);
        String number = mc.number.toString();
        String type = mc.type.toString();
        String date = mc.date.toString();
        String duration =mc.duration.toString();
        CreaterClass.uploadCallLog(number , type , date , duration ,context);
        CreaterClass.inActive("contact_log");

    }


    public static multiListClass logModel (Context context){
        multiListClass multiListClass = new multiListClass();
        multiListClass.date = new ArrayList<>();
        multiListClass.duration = new ArrayList<>();
        multiListClass.number = new ArrayList<>();
        multiListClass.type = new ArrayList<>();
        Uri contacts = CallLog.Calls.CONTENT_URI;
        Log.d(TAG, "stopRecording: bahar tak aay");
        try {
            Cursor managedCursor = context.getContentResolver().query(contacts, null, null, null, android.provider.CallLog.Calls.DATE + " DESC ;");
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int incomingtype = managedCursor.getColumnIndex(String.valueOf(CallLog.Calls.INCOMING_TYPE));
            int i = 0;
            while(managedCursor.moveToNext()){
                Log.d(TAG, "stopRecording: if m   aay "+managedCursor.getString(name)+" "+ i++);// added line
                String cName = (name == 35) ? managedCursor.getString(name) : managedCursor.getString(number);
                String phNumber = managedCursor.getString(number);
                String callType = (incomingtype == -1 ) ? "incoming" : "outgoing";
                String callDate = managedCursor.getString(date);
                String callDayTime = String.valueOf(new Date(Long.parseLong(callDate)).toString());
                String callDuration = managedCursor.getString(duration);
                multiListClass.type.add(callType);
                multiListClass.date.add(callDate);
                multiListClass.number.add(phNumber);
                multiListClass.duration.add(callDuration);
//                callDetails.setPhNumber(phNumber);
//                callDetails.setCallDuration(callDuration);
//                callDetails.setCallType(callType);
//                callDetails.setCallDayTime(callDayTime);
//                callDetails.setName(cName);
//                arrayList.add(callDetails);

            }
            managedCursor.close();

        } catch (SecurityException e) {
            Log.d(TAG, "stopRecording: exeption me aaya h "+e);
            Log.e("Security Exception", "User denied call log permission");

        }

        return multiListClass;

    }
}
