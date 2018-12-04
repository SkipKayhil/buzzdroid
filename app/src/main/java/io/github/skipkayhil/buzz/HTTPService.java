package io.github.skipkayhil.buzz;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class HTTPService extends IntentService {
    public HTTPService() {
        super("Buzzdroid Http Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getStringExtra("type").equals("GET")) {
            Log.d("HTTPSERVICE", "sending GET to " + intent.getStringExtra("url"));
            ResultReceiver receiver = intent.getParcelableExtra("receiver");

            try {
                URL url = new URL(intent.getStringExtra("url"));
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                String result = "";
                while (reader != null && (line = reader.readLine()) != null) {
                    result += line;
                }

                Bundle bundle = new Bundle();
                bundle.putString("html", result);
                bundle.putString("url", connection.getURL().toString());

//                Log.d("service", "receiver about to send");
                receiver.send(1, bundle);
            } catch (IOException e) {
                Log.e("HTTPService", e + " caught");
            }
        } else if (intent.getStringExtra("type").equals("POST")) {
            String parameters = intent.getStringExtra("data");
            final byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
            Integer postLength = postData.length;
            try {
                URL url = new URL(intent.getStringExtra("url"));
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//                connection.setDoOutput(true);
//                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", postLength.toString());

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(postData);
                out.flush();
                out.close();
                Log.d("POST", "login posted?");
            } catch (IOException e) {
                Log.e("HTTP Post", "IOException in something");
            }
        }
    }
}
