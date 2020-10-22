package mz.co.scn.hinario_lite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import mz.co.scn.hinario_lite.R;


/**
 * Created by Sidónio Goenha on 14/08/2020.
 * This class the first screen that the app open
 * It's used to create all songs in the first run of the app
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Glide.with(this).load(R.drawable.tv_lite_white).into((ImageView) findViewById(R.id.ivSplash));

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }, SPLASH_TIME_OUT);
    }
}
