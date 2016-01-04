package net.keizerdev.tymp.util;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by robert-jan on 1/4/16.
 */
public class YoutubeMediaPlayer {

    MediaPlayer youtubeMediaPlayer;

    public YoutubeMediaPlayer() {
        youtubeMediaPlayer = new MediaPlayer();
    }

    public void playYoutubeSource(String youtubeUrl) throws IOException {
        youtubeMediaPlayer.reset();
        youtubeMediaPlayer.setDataSource(youtubeUrl);
        youtubeMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        youtubeMediaPlayer.prepareAsync();
        youtubeMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                youtubeMediaPlayer.start();
                System.out.println(mp.isPlaying());
            }
        });
    }
}
