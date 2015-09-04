package com.thinkmobiles.gcmsender;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by klim on 04.09.15.
 */
public class AsyncSender extends AsyncTask<String, Void, Void> {
    private final String API_KEY = "AIzaSyAD6Pyvc3UA_7Th1GVx0ucUVcKno35Mf9c";
    private Context mContext;
    private String mMessage, mToken;

    public AsyncSender(Context _context) {
        mContext = _context;
    }


    @Override
    protected Void doInBackground(String[] params) {
        JSONObject jGcmData = new JSONObject();
        JSONObject jData = new JSONObject();
        try {

            mMessage = params[0];
            mToken = params[1];
            jData.put("message", mMessage.trim());

            // Where to send GCM message.
            if (!mToken.isEmpty()) {
                jGcmData.put("to", mToken.trim());
            } else {
                jGcmData.put("to", "/topics/global");
            }
            // What to send in GCM message.
            jGcmData.put("data", jData);


            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

//                // Read GCM response.
//                InputStream inputStream = conn.getInputStream();
//                String resp = IOUtils.toString(inputStream);
//                System.out.println(resp);
//                System.out.println("Check your device/emulator for notification or logcat for " +
//                        "confirmation of the receipt of the GCM message.");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mContext, "message has sent", Toast.LENGTH_SHORT).show();

    }

}
