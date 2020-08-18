package mz.co.scn.hinario_lite.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

/**
 * Created by Sidónio Goenha on 14/08/2020.
 */
public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected DisplayMetrics dm;
    protected FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dm = getResources().getDisplayMetrics();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        loadLocale();
    }

    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    /**
     *
     */
    @SuppressLint("NewApi")
    protected void loadLocale() {
        String language = sharedPreferences.getString("language", "pt");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, dm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
