package gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class galleryGetter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void SearchImages(Context context){
        Cursor cursor ;
        Uri uri;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String arr[] = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursor = context.getContentResolver().query(uri , arr , null , null);
        int  column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int  column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int i = 0;
        while (cursor.moveToNext() && cursor!=null ){
            Log.d("TAG", "SearchImages: "+cursor.getString(column_index_data)+" "+i++);
        }


    }
}
