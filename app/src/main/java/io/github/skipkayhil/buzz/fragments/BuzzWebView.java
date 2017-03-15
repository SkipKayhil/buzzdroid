package io.github.skipkayhil.buzz.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.github.skipkayhil.buzz.LoginDialog;
import io.github.skipkayhil.buzz.R;

public class BuzzWebView extends Fragment {

    private String username = "testusername";
    private String password = "testpassword";
    private String url;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_buzzport_view, container, false);

        username = getArguments().getString("username", "");
        password = getArguments().getString("password", "");
        url = getArguments().getString("url", "http://gatech.edu");

        WebView webView = (WebView) inflatedView.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            // Override this method that blocks urls from redirecting
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                final String LOGIN_URL = "login.gatech.edu";

                // Check if the view is on the login page
                if (url.contains (LOGIN_URL)) {
                    // Check if a username/password have been saved
                    if (username.equals("") || password.equals("")) {
                        // If not saved, then show the login dialog
                        new LoginDialog().show(getActivity().getSupportFragmentManager(), "login");
                    } else {
                        // If saved, check to see if the page contains the error message
                        view.evaluateJavascript("document.getElementById('msg')", (String s) -> {
                            String script = String.format(
                                    "document.getElementById('username').value='%s';"
                                            + "document.getElementById('password').value='%s';",
                                    username,
                                    password);

                            script = s.equals("null")
                                    ? script + "document.getElementById('fm1').submit.click()"
                                    : script;
                            view.evaluateJavascript(script, null);
                        });
                    }
                } else {
                    view.getSettings().setLoadWithOverviewMode(true);
                    view.getSettings().setUseWideViewPort(true);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the Buzzport website after the WebView settings
        webView.loadUrl(url);

        return inflatedView;
    }
}
