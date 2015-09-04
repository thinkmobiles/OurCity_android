package com.thinkmobiles.gcmsender;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    private EditText etMessage, etToken;
    private final static String token = "cF2ydNRwGqI:APA91bGRTRntKfJVkxLnjopGhdAy2a9hMIjoI5l32BpoXAOGCAQz0bW5cmfHfL5-k5VRnpBVzJnPp392lDFHIYBVOh_m2-ZSHxoxKLI-NbT3P0HEUHb2Lobi-3E7j4Cst2Hj-9EJ9tpn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessage = (EditText) findViewById(R.id.message_to_send_AM);
        etToken = (EditText) findViewById(R.id.token_to_send_AM);
        findViewById(R.id.send_btn_AM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] params = new String[2];
                params[0] = etMessage.getText().toString();
                params[1] = token;
                new AsyncSender(MainActivity.this).execute(params);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
