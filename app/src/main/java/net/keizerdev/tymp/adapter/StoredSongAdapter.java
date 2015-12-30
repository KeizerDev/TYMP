package net.keizerdev.tymp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.keizerdev.tymp.R;
import net.keizerdev.tymp.container.StoredSong;

import java.util.List;

public class StoredSongAdapter extends ArrayAdapter<StoredSong> {
    private List<StoredSong> items;
    private final Activity context;

    public StoredSongAdapter(Activity context, List<StoredSong> items) {
        super(context, R.layout.adapter_song, items);
        this.items = items;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        // reuse views
        if (itemView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            itemView = inflater.inflate(R.layout.adapter_song, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.songTitle = (TextView) itemView.findViewById(R.id.song_item_title);
            viewHolder.songArtist = (TextView) itemView.findViewById(R.id.song_item_artist);
            viewHolder.image = (ImageView) itemView.findViewById(R.id.song_item_img);
            itemView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) itemView.getTag();
        StoredSong item = items.get(position);
        holder.songTitle.setText(item.title);
        holder.songArtist.setText(item.artist);
//        if (!item.albumUrl.isEmpty()){
//            Picasso.with(context).load(item.albumUrl).into(holder.image);
//        }

        return itemView;
    }

    static class ViewHolder {
        TextView songArtist;
        TextView songTitle;
        ImageView image;
    }
}
