package net.keizerdev.tymp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragment fragment;
        switch (page) {
            case 1:
                fragment = new StoredSongFragment();
                break;
            case 2:
                fragment = new YoutubeFragment();
                break;
            default:
                fragment = new StoredSongFragment();
                break;
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}