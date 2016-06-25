package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG_CANCEL = "FB login cancelled";
    private static final String TAG_ERROR = "FB login error";

    // For FaceBook Login:
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private Button mFBLoginButton;
    CallbackManager mCallbackManager;

    // User:
    private User mUser;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;
    private TextView mFBUserName;
    private TextView mFBGender;
    private TextView mFBLocation;

    // Facebook login button
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(profile);
        }
        @Override
        public void onCancel() {        }
        @Override
        public void onError(FacebookException e) {      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        mFBLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

        mFBLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callFBLogin();
            }
        });
        */

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {}
        };

        profileTracker = new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile)
            {
                nextActivity(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginButton loginButton = (LoginButton)findViewById(R.id.fb_login_button);

        callback = new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        };

        // loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause()
    {

        super.onPause();
    }

    protected void onStop()
    {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent)
    {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    private void nextActivity(Profile profile)
    {
        if(profile != null)
        {
            String firstName =  profile.getFirstName();
            String lastName =  profile.getLastName();
            String profileImage = profile.getProfilePictureUri(200,200).toString();

            Intent main = MainMenuActivity.launchProfile(LoginActivity.this, firstName, lastName, profileImage);

            /*main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            */
            startActivity(main);
        }
    }

    /*
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
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response)
                            {
                                if (response.getError() != null)
                                {
                                    // handle error
                                    System.out.println("ERROR");
                                }
                                else
                                {
                                    System.out.println("Success");

                                    try
                                    {
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

                                        // Toast.makeText(, "Welcome " + mUser.getName(), Toast.LENGTH_LONG).show();

                                        // Call Host Activity to present logged in fragment:
                                        mLoginCallback.onValidLogIn(true);

                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();

                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancel() {
                Log.d(TAG_CANCEL, "On cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_ERROR, error.toString());
            }
        });
    }
    */
}
