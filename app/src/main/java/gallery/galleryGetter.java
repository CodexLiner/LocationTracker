package gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class galleryGetter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static galleryMultiClass SearchImages(Context context){
        Cursor cursor ;
        Uri uri;
        galleryMultiClass gm = new galleryMultiClass();
        gm.url = new ArrayList<>();
        gm.date = new ArrayList<>();
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String arr[] = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursor = context.getContentResolver().query(uri , arr , null , null);
        int  column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        int  column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.OWNER_PACKAGE_NAME);
//        int i = 0;
        while (cursor.moveToNext() && cursor!=null ){
            gm.url.add(cursor.getString(column_index_data));
            gm.date.add(cursor.getString(column_index_data));
//            Log.d("TAG", "SearchImages: "+cursor.getString(column_index_data)+" "+i++);
        }
        return gm;


    }
}
