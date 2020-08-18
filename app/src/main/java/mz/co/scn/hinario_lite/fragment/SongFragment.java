package mz.co.scn.hinario_lite.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import mz.co.scn.hinario_lite.R;
import mz.co.scn.hinario_lite.model.Song;
import mz.co.scn.hinario_lite.util.AppConstants;

import static android.content.ContentValues.TAG;


/**
 * Created by Sidónio Goenha on 15/08/2020.
 */
public class SongFragment extends BaseFragment {
    private TextView tv_song_lyric;
    private TextView tv_nr;
    private ScrollView lyric_layout;
    private Song song;
    public int lyricSize;
    private boolean tools;
    private SwitchCompat switch_dark_mode;
    private DocumentReference reference;
    private String book;
    private boolean pageDetais;

    public SongFragment() {
        // Required empty public constructor
    }

    public SongFragment(Song song, String book) {
        this.song = song;
        this.book = book;
    }

    public static SongFragment newInstance(String songId) {
        SongFragment songFragment = new SongFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SONG_ID, songId);

        songFragment.setArguments(bundle);

        return songFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //position = getArguments().getInt("position");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        tv_song_lyric = view.findViewById(R.id.tv_song_lyric);
        lyric_layout = view.findViewById(R.id.lyric_layout);
        tv_nr = view.findViewById(R.id.tv_nr);
        switch_dark_mode = view.findViewById(R.id.switch_dark_mode);

        if (song != null) {
            tv_song_lyric.setText(song.getLyric());
            getActivity().setTitle(song.getTitleNumber());

            tv_nr.setText(String.valueOf(song.getNumber()));

            String lyricStyle = sharedPreferences.getString("lyric_style", "Sans Serif");
            setFont(lyricStyle, tv_song_lyric);

            lyricSize = Integer.valueOf(sharedPreferences.getString("lyric_size", "14"));
            tv_song_lyric.setTextSize(lyricSize);

            boolean darkMode = sharedPreferences.getBoolean("dark_mode", false);
            switch_dark_mode.setChecked(darkMode);
            if (darkMode) {
                tv_song_lyric.setTextColor(getResources().getColor(R.color.white));
                lyric_layout.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                tv_song_lyric.setTextColor(getResources().getColor(R.color.black));
                lyric_layout.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }

        switch_dark_mode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                tv_song_lyric.setTextColor(getResources().getColor(R.color.white));
                lyric_layout.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                tv_song_lyric.setTextColor(getResources().getColor(R.color.black));
                lyric_layout.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        SongViewAdapter listAdapter = new SongViewAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listAdapter.notifyDataSetChanged();

        view.findViewById(R.id.fab_font).setOnClickListener(v -> {
            if (!tools) {
                view.findViewById(R.id.layout_tools).setVisibility(View.VISIBLE);
                tools = true;
            } else {
                view.findViewById(R.id.layout_tools).setVisibility(View.GONE);
                tools = false;
            }
        });

        view.findViewById(R.id.lyric_layout).setOnClickListener(v -> {
            if (!pageDetais) {
                view.findViewById(R.id.ll_song_number).setVisibility(View.GONE);
                view.findViewById(R.id.ll_key).setVisibility(View.GONE);
                view.findViewById(R.id.fab_next).setVisibility(View.GONE);
                view.findViewById(R.id.fab_previous).setVisibility(View.GONE);
                view.findViewById(R.id.fab_font).setVisibility(View.GONE);
                pageDetais = true;
            } else {
                view.findViewById(R.id.ll_song_number).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ll_key).setVisibility(View.VISIBLE);
                view.findViewById(R.id.fab_next).setVisibility(View.VISIBLE);
                view.findViewById(R.id.fab_previous).setVisibility(View.VISIBLE);
                view.findViewById(R.id.fab_font).setVisibility(View.VISIBLE);
                pageDetais = false;
            }
        });

        view.findViewById(R.id.imv_size_minor).setOnClickListener(v -> {
            if (lyricSize >= 8) {
                lyricSize = lyricSize - 1;
                tv_song_lyric.setTextSize(lyricSize);
            }
        });

        view.findViewById(R.id.imv_size_plus).setOnClickListener(v -> {
            if (lyricSize < 40) {
                lyricSize = lyricSize + 1;
                tv_song_lyric.setTextSize(lyricSize);
            }
        });

        view.findViewById(R.id.imv_align_left).setOnClickListener(v -> {
            tv_song_lyric.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        });
        view.findViewById(R.id.imv_align_right).setOnClickListener(v -> {
            tv_song_lyric.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        });
        view.findViewById(R.id.imv_align_center).setOnClickListener(v -> {
            tv_song_lyric.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        });

        view.findViewById(R.id.fab_previous).setOnClickListener(v -> {

            if (song.getNumber() > 1) {
                getSong(String.valueOf(song.getNumber() - 1));
            }
        });

        view.findViewById(R.id.fab_next).setOnClickListener(v -> {
            if (book.equals(AppConstants.CANTEMOS_BOOK)) {
                if (song.getNumber() + 1 <= 160) {
                    getSong(String.valueOf(song.getNumber() + 1));
                }
            } else {
                if (song.getNumber() + 1 <= 330) {
                    getSong(String.valueOf(song.getNumber() + 1));
                }
            }
        });

        if (song.getKey() != null) {
            view.findViewById(R.id.ll_key).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.tv_key)).setText(song.getKey());
        }

        view.findViewById(R.id.ll_key).setOnClickListener(v -> {
            playAssetSound(getContext(), "key/" + song.getKey() + ".mid");
        });

        view.findViewById(R.id.ll_song_number).setOnClickListener(v -> {
            LayoutInflater i = getActivity().getLayoutInflater();
            View go_to_view = i.inflate(R.layout.go_to_song, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(go_to_view);
            AlertDialog dialog = builder.create();
            EditText edtNumber = go_to_view.findViewById(R.id.edt_number);

            ((TextView) go_to_view.findViewById(R.id.tv_number_interval)).setText(book.equals(AppConstants.CANTEMOS_BOOK) ? "(1 - 160)" : "(1 - 330)");

            go_to_view.findViewById(R.id.ll_cancel).setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            go_to_view.findViewById(R.id.ll_ok).setOnClickListener(v1 -> {
                if (!edtNumber.getText().toString().isEmpty() && !edtNumber.getText().toString().equals("0"))
                    getSong(edtNumber.getText().toString());
                dialog.dismiss();
            });

            dialog.show();
        });

        return view;
    }

    public static void playAssetSound(Context context, String soundFileName) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();

            AssetFileDescriptor descriptor = context.getAssets().openFd(soundFileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSongDetails(Song song) {
        tv_song_lyric.setText(song.getLyric());
        getActivity().setTitle(song.getTitleNumber());
        tv_nr.setText(String.valueOf(song.getNumber()));
    }

    public void getSong(String songId) {
        reference = firestore.collection(book).document(songId);

        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    song = document.toObject(Song.class);
                    setSongDetails(song);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    /**
     * This method is used to change songs font.
     *
     * @param font
     * @param tv
     */
    public void setFont(String font, TextView tv) {
        try {
            switch (font) {
                case "Monospace":
                    tv.setTypeface(Typeface.MONOSPACE);
                    break;
                case "Sans Serif":
                    tv.setTypeface(Typeface.SANS_SERIF);
                    break;
                case "Norwester":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/norwester.otf"));
                    break;
                case "Playfair":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/PlayfairDisplay_Regular.otf"));
                    break;
                case "Allura":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Allura_Regular.otf"));
                    break;
                case "Roboto":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto_Medium.ttf"));
                    break;
                case "Spectral":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Spectral_Italic.ttf"));
                    break;
                case "Shrikhand":
                    tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Shrikhand_Regular.otf"));
                    break;
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public class SongViewAdapter extends RecyclerView.Adapter<SongViewAdapter.ViewHolder> {

        public List<String> fonts;
        public String selectedFont;
        public Context context;

        /**
         * @param context
         */
        public SongViewAdapter(Context context) {
            this.context = context;
            fonts = new ArrayList<>();
            fonts.add("Allura");
            fonts.add("Monospace");
            fonts.add("Norwester");
            fonts.add("Playfair");
            fonts.add("Roboto");
            fonts.add("Sans Serif");
            fonts.add("Shrikhand");
            fonts.add("Spectral");
        }

        /**
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public SongViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_list_item, parent, false);

            return new SongViewAdapter.ViewHolder(v);
        }

        /**
         * @param holder
         * @param pos
         */
        @Override
        public void onBindViewHolder(@NonNull SongViewAdapter.ViewHolder holder, final int pos) {
            String font = fonts.get(pos);
            if (font != null)
                holder.tv_font.setText(font);

            setFont(font, holder.tv_font);

            holder.pos = pos;
        }

        /**
         * @return
         */
        @Override
        public int getItemCount() {
            return fonts.size();
        }

        public void setFilter(List<String> newFonts) {
            fonts = new ArrayList<>();
            fonts.addAll(newFonts);
            notifyDataSetChanged();
        }

        /**
         *
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public int pos;

            private TextView tv_font;

            public ViewHolder(@NonNull View v) {
                super(v);

                tv_font = v.findViewById(R.id.tv_font);

                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                selectedFont = fonts.get(pos);
                setFont(selectedFont, tv_song_lyric);
            }
        }
    }
}
