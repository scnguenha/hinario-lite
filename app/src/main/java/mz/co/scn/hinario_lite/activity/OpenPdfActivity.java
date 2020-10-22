package mz.co.scn.hinario_lite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import mz.co.scn.hinario_lite.R;

public class OpenPdfActivity extends AppCompatActivity {
    private WebView webview;
    private ProgressBar progressbar;
    private String songUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pdf);

        webview = findViewById(R.id.web_view);
        progressbar = findViewById(R.id.progress);

        if (getIntent().hasExtra("url")){
            songUrl = getIntent().getStringExtra("url");
        }

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(songUrl);
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url){
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });

    }
}