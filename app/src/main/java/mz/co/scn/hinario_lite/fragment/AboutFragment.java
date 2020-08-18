package mz.co.scn.hinario_lite.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.activity.DonateActivity;
import mz.co.scn.hinario_lite.activity.PrivacyPolicy;

/**
 * Created by Sidónio Goenha on 7/10/2019.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_about, container, false);
        setHasOptionsMenu(true);

        try {
            ((TextView) v.findViewById(R.id.tv_app_version)).setText(getString(R.string.lblVersion) + " " + getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
        } catch (
                PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        v.findViewById(R.id.cv_report_error).setOnClickListener(view -> {
            Intent emailintent = new Intent(Intent.ACTION_SEND);
            emailintent.setData(Uri.parse("mailto:"));
            emailintent.setType("text/message");
            emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sid.goenha@gmail.com", "sid.goenha@live.com"});
            emailintent.putExtra(Intent.EXTRA_SUBJECT, "Reportar erro na aplicação TINSIMU TA VAKRISTE");

            try {
                startActivity(Intent.createChooser(emailintent, getString(R.string.msg_choose_email_provider)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), "Não existe nenhuma aplicação para efectuar a acção de envio de email", Toast.LENGTH_LONG).show();
            }

        });

        v.findViewById(R.id.tv_privacy_police).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), PrivacyPolicy.class));
        });
        v.findViewById(R.id.cv_donate).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), DonateActivity.class));
        });
        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //menu.findItem(R.id.search).setVisible(false);
        //menu.findItem(R.id.total_songs).setVisible(false);
    }
}
