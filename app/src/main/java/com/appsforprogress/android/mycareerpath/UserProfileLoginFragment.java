package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Oswald on 3/5/2016.
 */
public class UserProfileLoginFragment extends Fragment
{
    private static final String ARG_USER_ID = "user_id";
    private static final String TAG_CANCEL = "FB login cancelled";
    private static final String TAG_ERROR = "FB login error";

    private User mUser;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;

    // Facebook Login View:
    private TextView mFBUserName;
    private TextView mFBGender;
    private TextView mFBLocation;
    private Button mFBLoginButton;

    // For FaceBook Login:
    CallbackManager mCallbackManager;
    OnLogInListener mLoginCallback;

    public static UserProfileLoginFragment newInstance() //UUID userId)
    {
        Bundle args = new Bundle();
        // args.putSerializable(ARG_USER_ID, userId);

        UserProfileLoginFragment fragment = new UserProfileLoginFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set up the FaceBook Login
        FacebookSdk.sdkInitialize(this.getActivity().getApplicationContext());

        // mUser = new User();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialize view from the fragment_skill_list layout file
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mFBLoginButton = (LoginButton) view.findViewById(R.id.fb_login_button);

        mFBLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callFBLogin();
            }
        });

        return view;
    }


    private void callFBLogin()
    {
        mCallbackManager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                System.out.println("Success");
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");

                                    try {
                                        mUser = new User();
                                        String jsonresult = String.valueOf(json);
                                        System.out.println("JSON Result" + jsonresult);

                                        // String str_email = json.getString("email");
                                        mUser.setEmail(json.getString("email"));
                                        //String str_id = json.getString("id");
                                        mUser.setFaceBookID(json.getString("id"));
                                        // String str_firstname = json.getString("first_name");
                                        mUser.setFirstName(json.getString("first_name"));
                                        // String str_lastname = json.getString("last_name");
                                        mUser.setLastName(json.getString("last_name"));

                                        // PrefUtils.setCurrentUser(mUser, UserProfileFragment.this.getActivity());

                                        Toast.makeText(UserProfileLoginFragment.this.getActivity(), "Welcome " + mUser.getName(), Toast.LENGTH_LONG).show();

                                        // Call Host Activity to present logged in fragment:
                                        mLoginCallback.onValidLogIn(true);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();
                // Intent intent = new Intent(LoginActivity.this,LogoutActivity.class);
                // startActivity(intent);
                // finish();

            }

            @Override
            public void onCancel() {
                Log.d(TAG_CANCEL, "On cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_ERROR,error.toString());
            }
        });
    }

    // Container Activity must implement this interface
    // Allows this fragment to communicate with the calling Activity:
    public interface OnLogInListener
    {
        public void onValidLogIn(boolean loggedIn);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            mLoginCallback = (OnLogInListener) context;
        }
        catch (ClassCastException e) {
        throw new ClassCastException(context.toString()
                + " must implement OnLogInListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
