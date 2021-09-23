package AutoCallRecorder;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService  extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //        Intent intent1 = new Intent(this , StateRecieverClas.class);
//        startService(intent1);
//        startService(new Intent(this.getApplicationContext() , RecorderService.class));
    }

    @Override
    public void onInterrupt() {
        //        Intent intent1 = new Intent(this , StateRecieverClas.class);
//        startService(intent1);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED;
        info.packageNames = new String[] {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }
}
