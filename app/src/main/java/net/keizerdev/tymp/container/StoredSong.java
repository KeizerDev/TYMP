package net.keizerdev.tymp.container;

public class StoredSong {
    public String title = "";
    public String artist = "";
    public String albumUrl = "";
    public String videoId = "";

    public StoredSong() {
    }

    public StoredSong(String title, String artist, String albumUrl, String videoId) {
        this.title = title;
        this.artist = artist;
        this.albumUrl = albumUrl;
        this.videoId = videoId;
    }
}
