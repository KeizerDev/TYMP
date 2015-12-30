package net.keizerdev.tymp.youtube.container;

public class YtFile {

    private Meta meta;
    private String url = "";

    public YtFile(Meta meta, String url) {
        this.meta = meta;
        this.url = url;
    }

    /**
     * The url to download the file.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Meta data for the specific file.
     */
    public Meta getMeta() {
        return meta;
    }
}
