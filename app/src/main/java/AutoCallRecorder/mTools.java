package AutoCallRecorder;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public  class mTools {
    Calendar cal=Calendar.getInstance();
    public static String getDate()
    {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        return String.valueOf(day)+"_"+String.valueOf(month)+"_"+String.valueOf(year);
    }
    public static String getPath() throws IOException {
//        String internalFile=getDate();
//        File file=new File(Environment.getExternalStorageDirectory()+"/Varbin Records/");
        File file1=new File(Environment.getExternalStorageDirectory()+"/VarbinRecords/");
        if(!file1.exists()) {
            file1.mkdir();
        }
        if(file1.isDirectory()) {
          file1.getPath();
          File f = new File(file1.getAbsolutePath()+"/"+"nomedia");
          if (!f.exists()){
             try {
                 f.createNewFile();
             }catch (Exception e){
                 e.printStackTrace();
             }
          }
        }
        String path=file1.getAbsolutePath();

        return path;
    }


}
