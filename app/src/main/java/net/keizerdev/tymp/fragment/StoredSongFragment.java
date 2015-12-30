package net.keizerdev.tymp.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.keizerdev.tymp.R;
import net.keizerdev.tymp.adapter.StoredSongAdapter;
import net.keizerdev.tymp.container.StoredSong;
import net.keizerdev.tymp.container.StoredSongsDBHelper;
import net.keizerdev.tymp.youtube.YouTubeUriExtractor;
import net.keizerdev.tymp.youtube.container.YtFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoredSongFragment extends Fragment {
    View view;
    ListView listView;

    List<StoredSong> list = new ArrayList<>();
    private StoredSongAdapter adapter;

    public StoredSongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_storedsong, container, false);

        StoredSongsDBHelper db = new StoredSongsDBHelper(getContext());
//        db.addStoredSong(new StoredSong("Paint it black", "The Rolling stones", "http://placehold.it/300x300", "https://www.youtube.com/watch?v=O4irXQhgMqg"));
//        db.addStoredSong(new StoredSong("I can't get no", "The Rolling stones", "http://placehold.it/300x300", "https://www.youtube.com/watch?v=nrIPxlFzDi0"));
//        db.addStoredSong(new StoredSong("Land of Confusion", "Genesis", "http://placehold.it/300x300", "https://www.youtube.com/watch?v=1pkVLqSaahk"));
        list = db.getAllStoredSongs();

        listView = (ListView) view.findViewById(R.id.songsListContainer);
        adapter = new StoredSongAdapter(getActivity(), list);
        // get data from the table by the ListAdapter

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(list.get(position).videoId);
                getYoutubeDownloadUrl(list.get(position).videoId);
            }
        });
        return view;
    }

    private void getYoutubeDownloadUrl(String youtubeLink) {
        YouTubeUriExtractor ytEx = new YouTubeUriExtractor(getContext()) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
//                mainProgressBar.setVisibility(View.GONE);

                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    return;
                }

                // Iterate over itags
                for (int i = 0, itag = 0; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);

                    System.out.println(ytFiles.get(itag).getMeta().getItag()); // 140 is the one I want.
                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getMeta().getHeight() == -1 || ytFile.getMeta().getHeight() >= 360) {
                        if (ytFile.getMeta().getItag() == 140) {
                            addButtonToMainLayout(videoTitle, ytFile);
                        }
                    }
                }

            }
        };
        // Ignore the webm container format
        ytEx.setIncludeWebM(false);
        ytEx.setParseDashManifest(true);
        // Lets execute the request
        ytEx.execute(youtubeLink);
    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        // Display some buttons and let the user choose the format
        String filename;
//        filename = videoTitle.substring(0, 55) + "." + ytfile.getMeta().getExt();
        filename = videoTitle + "." + ytfile.getMeta().getExt();
        downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
        System.out.println(youtubeDlUrl + " - " + downloadTitle + " - " + fileName);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(youtubeDlUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    System.out.println(mp.isPlaying());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
