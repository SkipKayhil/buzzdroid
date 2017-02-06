package io.github.skipkayhil.buzz;

import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BuzzportView extends Fragment {

    private String username = "testusername";
    private String password = "testpassword";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.activity_buzzport_view, container, false);

        /*
         * Grab the saved username and password if they exist
         */
        SharedPreferences storage = getActivity().getSharedPreferences("LOGIN_INFO", 0);
        username = storage.getString("username", "");
        password = storage.getString("password", "");

        WebView webView = (WebView) inflatedView.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            /*
             * Override this method that blocks urls from redirecting
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            /*
             * Use javascript to fill in the username and password, then click the login button
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO: If username/password aren't in storage, then show the login dialog
                if (username == "" || password == "") {
                    DialogFragment loginDiag = new LoginDialog();
                    loginDiag.show(getActivity().getSupportFragmentManager(), "login");
                } else {
                    view.evaluateJavascript("document.getElementById(\"username\").value=\""
                            + username + "\";", null);
                    view.evaluateJavascript("document.getElementById(\"password\").value=\""
                            + password + "\";", null);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /*
         * Load the Buzzport website after the WebView settings
         */
        webView.loadUrl("https://buzzport.gatech.edu/misc/preauth.html");

        return inflatedView;
    }
}
