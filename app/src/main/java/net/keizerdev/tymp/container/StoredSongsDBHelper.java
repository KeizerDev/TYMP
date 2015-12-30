package net.keizerdev.tymp.container;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class StoredSongsDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StoredSongs.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StoredSongsDB.StoredSongsEntry.TABLE_NAME + " (" +
                    StoredSongsDB.StoredSongsEntry._ID + " INTEGER PRIMARY KEY," +
                    StoredSongsDB.StoredSongsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    StoredSongsDB.StoredSongsEntry.COLUMN_NAME_ARTIST + TEXT_TYPE + COMMA_SEP +
                    StoredSongsDB.StoredSongsEntry.COLUMN_NAME_VIDEO_ID + TEXT_TYPE + COMMA_SEP +
                    StoredSongsDB.StoredSongsEntry.COLUMN_NAME_ALBUM_URL + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StoredSongsDB.StoredSongsEntry.TABLE_NAME;

    public StoredSongsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addStoredSong(StoredSong storedSong) {
        Log.d("addStoredSong", storedSong.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoredSongsDB.StoredSongsEntry.COLUMN_NAME_TITLE, storedSong.title);
        values.put(StoredSongsDB.StoredSongsEntry.COLUMN_NAME_ARTIST, storedSong.artist);
        values.put(StoredSongsDB.StoredSongsEntry.COLUMN_NAME_VIDEO_ID, storedSong.videoId);
        values.put(StoredSongsDB.StoredSongsEntry.COLUMN_NAME_ALBUM_URL, storedSong.albumUrl);

        db.insert(StoredSongsDB.StoredSongsEntry.TABLE_NAME, null, values);

        db.close();
    }

    public List<StoredSong> getAllStoredSongs() {
        List<StoredSong> storedSongs = new LinkedList<StoredSong>();

        String query = "SELECT  * FROM " + StoredSongsDB.StoredSongsEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        StoredSong storedSong = null;
        if (cursor.moveToFirst()) {
            do {
                storedSong = new StoredSong();

//                id = cursor.getString(0);
                storedSong.title = cursor.getString(1);
                storedSong.artist = cursor.getString(2);
                storedSong.videoId = cursor.getString(3);
                storedSong.albumUrl = cursor.getString(4);

                storedSongs.add(storedSong);
            } while (cursor.moveToNext());
        }

        Log.d("getAllStoredSongs()", storedSong.toString());

        return storedSongs;
    }
}