package com.varbin.locationtracker.APIs;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import UiActivities.FileUploadClass;
import retrofit2.http.Url;

public class fileUploadToserver {
    public static void filefinder(String filename, String fileid , Context context){
        String url = "\\/storage\\/emulated\\/0\\/VarbinRecords\\/mNumber-1631994369797.mp3";
        url  = url.replace("\\", "");
        String id = "92";
        if (id!=null && url !=null){
            fileChooser(id , url , context , filename);
        }
    }

    private static void fileChooser(String id, String url, Context context ,String FileName) {
        File file = new File(url);
        if (file.isFile()){
            Log.d("TAG", "fileChooser: hai");
            Uri uri = Uri.fromFile(file);

                try {
                    FileUploadClass fileUploadClass = new FileUploadClass(file , id ,  url);
                    fileUploadClass.execute();

                } catch (Exception e) {
                    Log.d("TAG", "fileChooser: eee"+e);
                }

        }else {
            Log.d("TAG", "fileChooser: no ");
        }
    }

}
