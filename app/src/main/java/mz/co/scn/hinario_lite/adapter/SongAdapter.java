package mz.co.scn.hinario_lite.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.model.Song;

/**
 * Created by Sidónio Goenha on 14/08/2020.
 * <p>
 * RecyclerView adapter for a list of songs
 */
public class SongAdapter extends FirestoreAdapter<SongAdapter.ViewHolder> {

    public interface OnSongSelectedListener {
        void onSongSelected(DocumentSnapshot song);
    }

    private OnSongSelectedListener listener;

    public SongAdapter(Query query, OnSongSelectedListener listener) {
        super(query);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_song, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), listener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_composer;
        private TextView tv_number;

        public ViewHolder(View v) {
            super(v);
            tv_title = v.findViewById(R.id.tv_title);
            tv_composer = v.findViewById(R.id.tv_composer);
            tv_number = v.findViewById(R.id.tv_number);
        }

        public void bind(final DocumentSnapshot snapshot, final OnSongSelectedListener listener) {
            Song song = snapshot.toObject(Song.class);

            if (song != null) {
                tv_title.setText(song.getTitle() != null ? song.getTitle() : "Título");
                tv_composer.setText(song.getComposer() != null ? song.getComposer() : "Desconhecido");
                tv_number.setText(String.valueOf(song.getNumber()));
            }

            // Click Listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSongSelected(snapshot);
                }
            });
        }
    }
}
