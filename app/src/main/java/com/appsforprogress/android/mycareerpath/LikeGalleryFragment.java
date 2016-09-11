package com.appsforprogress.android.mycareerpath;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LikeGalleryFragment extends Fragment
{
    private RecyclerView mLikeRecyclerView;
    private static final String TAG = "LikeGalleryFragment";


    public static LikeGalleryFragment newInstance()
    {
        return new LikeGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Run the code below to fetch images in th background:
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_like_gallery, container, false);

        mLikeRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_like_gallery_recycler_view);

        // Set up row of 3 elements
        mLikeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return v;

    }

    // Create a Background Thread to run the call to read in Image bytes from URL specified:
    // Need to specify the wikipedia URL:
    private class FetchItemsTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            // Likes reside in the UserProfileFragment
            for (int i = 0; i < numLikes; i++) {
                // Fetch a Wikipedia Page associated to one ouf our FB Likes:
                WikiPage wikipage = new WikiPageFetcher().fetchPage(likeItem[i]);
            }

            return null;
        }
    }

}
