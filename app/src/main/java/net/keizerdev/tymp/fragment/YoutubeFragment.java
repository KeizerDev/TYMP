package net.keizerdev.tymp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.keizerdev.tymp.R;
import net.keizerdev.tymp.container.StoredSong;
import net.keizerdev.tymp.container.StoredSongsDBHelper;

public class YoutubeFragment extends Fragment {
    View view;
    EditText title, artist, albumurl, videoid;
    Button addsong;
    StoredSongsDBHelper db;

    public YoutubeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new StoredSongsDBHelper(getContext());

        view = inflater.inflate(R.layout.fragment_youtube, container, false);

        title = (EditText) view.findViewById(R.id.addsong_title);
        artist = (EditText) view.findViewById(R.id.addsong_artist);
        albumurl = (EditText) view.findViewById(R.id.addsong_albumurl);
        videoid = (EditText) view.findViewById(R.id.addsong_videoid);

        addsong = (Button) view.findViewById(R.id.addsong_to_db);

        addsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredSong storedSong = new StoredSong();
                storedSong.title = title.getText().toString();
                storedSong.artist = artist.getText().toString();
                storedSong.albumUrl = albumurl.getText().toString();
                storedSong.videoId = videoid.getText().toString();

                db.addStoredSong(storedSong);
            }
        });
        return view;
    }
}
