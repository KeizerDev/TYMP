package net.keizerdev.tymp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.keizerdev.tymp.R;
import net.keizerdev.tymp.container.StoredSong;
import net.keizerdev.tymp.container.StoredSongsDBHelper;
import net.keizerdev.tymp.youtube.YouTubeUriExtractor;
import net.keizerdev.tymp.youtube.container.YtFile;

public class YoutubeFragment extends Fragment {
    View view;
    EditText videoid;
    Button addsong;
    StoredSongsDBHelper db;
    private ProgressDialog progressDialog;

    public YoutubeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new StoredSongsDBHelper(getContext());

        view = inflater.inflate(R.layout.fragment_youtube, container, false);

        videoid = (EditText) view.findViewById(R.id.addsong_videoid);
        progressDialog = new ProgressDialog(getContext());

        addsong = (Button) view.findViewById(R.id.addsong_to_db);

        addsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.app_name), "Check Youtube Url", true);
                if ((videoid.getText().toString().contains("://youtu.be/") || videoid.getText().toString().contains("youtube.com/watch?v="))) {
                    getYoutubeDownloadUrl(videoid.getText().toString());
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), R.string.video_not_found, Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void getYoutubeDownloadUrl(final String youtubeLink) {
        YouTubeUriExtractor ytEx = new YouTubeUriExtractor(getContext()) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    return;
                }
                progressDialog.dismiss();
                // Iterate over itags
                for (int i = 0, itag = 0; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);

                    System.out.println(ytFiles.get(itag).getMeta().getItag()); // 140 is the one I want.
                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getMeta().getHeight() == -1 || ytFile.getMeta().getHeight() >= 360) {
                        if (ytFile.getMeta().getItag() == 140) {
                            StoredSong storedSong = new StoredSong();

                            storedSong.title = videoTitle;
                            storedSong.artist = videoTitle;
                            storedSong.albumUrl = null; // thumbnail
                            storedSong.videoId = youtubeLink;

                            db.addStoredSong(storedSong);
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
}
