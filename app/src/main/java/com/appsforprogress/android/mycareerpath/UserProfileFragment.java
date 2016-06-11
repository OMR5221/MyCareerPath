package com.appsforprogress.android.mycareerpath;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Oswald on 3/5/2016.
 */
public class UserProfileFragment extends Fragment
{
    private static final String ARG_USER_ID = "user_id";

    private User mUser;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;


    public static UserProfileFragment newInstance() //UUID userId)
    {
        Bundle args = new Bundle();
        // args.putSerializable(ARG_USER_ID, userId);

        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mUser = new User();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialize view from the fragment_skill_list layout file
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Reference to the Views widgets:
        mFirstNameEdit = (EditText) view.findViewById(R.id.first_name_edit_text);

        mLastNameEdit = (EditText) view.findViewById(R.id.last_name_edit_text);

        return view;
    }
}
