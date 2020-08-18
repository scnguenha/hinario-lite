package mz.co.scn.hinario_lite.activity;

import android.os.Bundle;
import android.webkit.WebView;

import mz.co.scn.hinario_lite.R;

/**
 * Created by Sidónio Goenha on 5/17/2019.
 * This class is used to show privacy policy
 */
public class PrivacyPolicy extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("https://politicaprivacidadetv.wordpress.com/politica-de-privacidade/");
        //webView.loadUrl("file:///android_asset/politica_privacidade.html");
    }
}
