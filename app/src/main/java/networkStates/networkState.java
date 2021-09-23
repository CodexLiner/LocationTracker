package networkStates;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import synceAdapter.AccountConstants;

public class networkState {
    public static  void EnableWifi(Context context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Log.d("TAG", "EnableWifi@: ");
        WifiManager wifiManager = (WifiManager)  context.getSystemService(context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        Log.d("TAG", "EnableWifi: "+wifiManager.getWifiState());
        AccountConstants.wifiStatus = true;
        ConnectivityManager dataManager;
        dataManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(true);
        dataMtd.invoke(dataManager, false);

    }
}
