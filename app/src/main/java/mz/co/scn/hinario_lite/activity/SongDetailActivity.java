package mz.co.scn.hinario_lite.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.fragment.SongFragment;
import mz.co.scn.hinario_lite.model.Song;
import mz.co.scn.hinario_lite.util.AppConstants;

/**
 * Created by Sidónio Goenha on 15/08/2020.
 */
public class SongDetailActivity extends BaseActivity implements View.OnClickListener, EventListener<DocumentSnapshot> {
    private static final String TAG = "SongDetailActivity";
    private String book;
    private String songId;
    private DocumentReference reference;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_song_detail);
        if (getIntent().hasExtra(AppConstants.BOOK))
            book = getIntent().getStringExtra(AppConstants.BOOK);

        if (getIntent().hasExtra(AppConstants.SONG_ID))
            songId = getIntent().getStringExtra(AppConstants.SONG_ID);

        //SongFragment songFragment = new SongFragment();

        //Bundle bundle = new Bundle();
        //bundle.putInt("position", position);
        //bundle.putString(AppConstants.SONG_ID, songId);

        //songFragment.setArguments(bundle);

        reference = firestore.collection(book).document(songId);

        //getSupportFragmentManager().beginTransaction().replace(android.R.id.content, songFragment).commit();
    }

    private void onLoadSongFragment(Song song) {
        SongFragment songFragment = new SongFragment(song, book);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, songFragment).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        listenerRegistration = reference.addSnapshotListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    /**
     * Listener for the Restaurant document ({@link #reference}).
     */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "song:onEvent", e);
            return;
        }

        onLoadSongFragment(snapshot.toObject(Song.class));
    }
}