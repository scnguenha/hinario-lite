package mz.co.scn.hinario_lite.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mz.co.scn.hinario_lite.R;

/**
 * Created by Sidónio Goenha on 6/25/2019.
 */
public class DonateActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        TextView tv_donate = findViewById(R.id.tv_donate);

        tv_donate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Shrikhand_Regular.otf"));
    }
}
