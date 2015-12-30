package net.keizerdev.tymp.container;

import android.provider.BaseColumns;

public final class StoredSongsDB {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public StoredSongsDB() {}

    /* Inner class that defines the table contents */
    public static abstract class StoredSongsEntry implements BaseColumns {
        public static final String TABLE_NAME = "StoredSongs";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_VIDEO_ID = "videoid";
        public static final String COLUMN_NAME_ALBUM_URL = "albumurl";
    }
}