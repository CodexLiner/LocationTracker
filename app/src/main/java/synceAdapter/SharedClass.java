package synceAdapter;

import android.app.Application;
import android.content.SharedPreferences;

public class SharedClass extends Application {
   private final SharedPreferences sharedPreferences = getSharedPreferences("whole" , 0) ;
   private final SharedPreferences.Editor editor  = sharedPreferences.edit();
   public SharedPreferences.Editor getEditor(){
       return editor;
   }
   public boolean getShared(String s){
       return sharedPreferences.getBoolean(s , false);
   }
}
