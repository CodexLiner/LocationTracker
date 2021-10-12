package UiActivities;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.varbin.locationtracker.APIs.CreaterClass;

import java.io.DataOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import synceAdapter.AccountConstants;

public class FileUploadClass  extends AsyncTask<Void , Void , Void> {
    File file;
    String id;
    String uriS;
    public FileUploadClass(File file, String url , String id) {
        super();
        this.file = file;
        this.id = id;
        this.uriS = url;
    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("TAG", "doInBackground: ");
        File myFile = file;
        String mime = getMimeType(uriS);
        try {
            Log.d("TAG", "doInBackground: try");
            HttpURLConnection conn = null;
            DataOutputStream dataOutputStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            if (myFile.isFile()){
                Log.d("TAG", "youtubeSe :file hai ");
                String apiPath = AccountConstants.BASEURL+"fileupload/"+id;
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file" , myFile.getName() , RequestBody.create(MediaType.parse(mime) , myFile))
                        .addFormDataPart("submit" , "submit")
                        .build();
               Request request = new Request.Builder()
                        .url(apiPath)
                        .post(requestBody)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("TAG", "youtubeSe: fail  "+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("TAG", "youtubeSe : pass "+response.message());
                    }
                });
//
//
//







//                try{
//                    Log.d("TAG", "doInBackground: try2");
//                    String apiurl = "https://blogsthink.in/upload.php";
//                    FileInputStream fileInputStream = new FileInputStream(myFile);
//                    URL url = new URL(apiurl);
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//                    conn.setUseCaches(false);
//                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                    conn.setRequestProperty("file", uriS);
//                    dataOutputStream = new DataOutputStream(conn.getOutputStream());
//                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\" ; filename=\""
//                            + uriS + "\"" + lineEnd);
//                    dataOutputStream.writeBytes(lineEnd);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    buffer = new byte[bufferSize];
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                    while (bytesRead > 0){
//                        dataOutputStream.write(buffer, 0, bufferSize);
//                        bytesAvailable = fileInputStream.available();
//                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                    }
//                    dataOutputStream.writeBytes(lineEnd);
//                    dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                    int  serverResponseCode = conn.getResponseCode();
//                    String serverResponseMessage = conn .getURL().toString();
//                    if (serverResponseCode == 200) {
//                        Log.d("TAG", "doInBackground: "+200);
//                    }else {
//                        Log.d("TAG", "doInBackground: "+serverResponseMessage);
//                    }
//                    fileInputStream.close();
//                    dataOutputStream.flush();
//                    dataOutputStream.close();
//                }catch (Exception d){
//                    Log.d("TAG", "doInBackground: dd"+d);
//                }
            }

        }catch (Exception e){
            Log.d("TAG", "doInBackground: ee"+e);
        }
        return null;
    }
}
