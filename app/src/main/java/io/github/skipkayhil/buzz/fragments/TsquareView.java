package io.github.skipkayhil.buzz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.skipkayhil.buzz.HTTPService;
import io.github.skipkayhil.buzz.R;
import io.github.skipkayhil.buzz.model.TSquare;
import io.github.skipkayhil.buzz.model.User;
import io.github.skipkayhil.buzz.model.GTClass;

public class TsquareView extends Fragment {

    final static String LOG_ID = "TSQUAREVIEW";
    final static String TSQUARE_BASE_URL = "https://t-square.gatech.edu/portal/pda";
    final static String LOGIN_BASE_URL = "https://login.gatech.edu";
    final static String LOGIN_REDIRECT_URL = LOGIN_BASE_URL + "/cas/login?service="
            + "https%3A%2F%2Ft-square.gatech.edu%2Fsakai-login-tool%2Fcontainer";
    final static String LOGIN_POST_EXT = "&_eventId=submit&execution=e1s1"
            + "&lt=%s&password=%s&submit=LOGIN&username=%s";

    private TSquare tSquare = new TSquare();

    private class TsquareReceiver extends ResultReceiver {

        public TsquareReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle receivedResult) {
            if (receivedResult != null) {
                String url = receivedResult.getString("url", "");
                String html = receivedResult.getString("html", "");
                handleHTML(url, html);
            }
        }
    }

    private void handleHTML(String url, String html) {
        if (url.contains(LOGIN_BASE_URL)) {
            // first check for error banner
            if (html.contains("Incorrect login") || html.contains("ticket")) {
                // login details are wrong
                Log.d(LOG_ID, "Login page displayed error");

            } else if (html.contains("Login successful")) {
                Log.d(LOG_ID, "Logged in, redirecting to t-square.gatech.edu");
                makeHTTPRequest("GET", TSQUARE_BASE_URL);

            } else {
                // no error, meaning fresh login screen: try to login
                // TODO:  make new intent with custom URL

                Log.d(LOG_ID, "Login page ready for login");
                Log.d(LOG_ID, html);

                String username = User.getInstance().getUsername();
                String password = User.getInstance().getPassword();
                String chopped = html.substring(html.indexOf("LT-"));
                String lt = chopped.substring(0, chopped.indexOf("\""));

//                Log.d("LT", chopped);
                Log.d("LT", lt);

                String post = String.format(LOGIN_POST_EXT, lt, password, username);
                Log.d(LOG_ID, "Sending login info with GET");
                makeHTTPRequest("GET",
                        LOGIN_REDIRECT_URL + String.format(LOGIN_POST_EXT, lt, password, username));
//                makeHTTPRequest("GET", "https://t-square.gatech.edu/portal/pda/?force.login=yes");

            }

            // if no error banner, then grab LT and post the login info
        } else if (url.contains("t-square.gatech.edu/portal/pda")) {
            // check if need to login
            if (html.contains("class=\"loginLink\"")) {
                // tsquare is not logged in, go to login.gatech.edu and try to log in
                Log.d("TSQUAREVIEW", "TSquare is logged out, redirecting to login.gatech.edu");
                makeHTTPRequest("GET", "https://login.gatech.edu/cas/login?service="
                        + "https%3A%2F%2Ft-square.gatech.edu%2Fsakai-login-tool%2Fcontainer");
            } else {
                // everything is good and logged in
                Log.d("Tsquare", "Logged into TSquare, parsing HTML...");
                String[] htmlCopy = html.split("<li>");

                for (String s : htmlCopy) {
//                    s = s.trim();
                    Log.d("wtf", s);

                    if (s.trim().startsWith("<span>") && !s.contains("My Workspace")) {
                        tSquare.addClass(new GTClass(s));
                    }
                }

                for (GTClass c: tSquare.getClassList()) {
                    Log.d("GTClass", c.toString());
                }

            }
            // if we are logged in, then we need to render the view
        }
    }

    private void makeHTTPRequest(String type, String url) {
        makeHTTPRequest(type, url, "");
    }

    private void makeHTTPRequest(String type, String url, String data) {
        Intent intent = new Intent(this.getContext(), HTTPService.class);
        TsquareReceiver receiver = new TsquareReceiver(null);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        intent.putExtra("data", data);
        intent.putExtra("receiver", receiver);
        getContext().startService(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("new tsv", "intent creation");
        makeHTTPRequest("GET", "https://t-square.gatech.edu/portal/pda/?force.login=true");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // load the tsquare page html
        // if logged in, pass it on the View to render
        // if NOT logged in, then make request to login.gatech.edu, and post credentials?, and redirect
        return inflater.inflate(R.layout.fragment_buses_view, container, false);
    }
}
