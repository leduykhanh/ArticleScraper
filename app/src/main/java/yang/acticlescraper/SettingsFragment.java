package yang.acticlescraper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

import yang.acticlescraper.sql.ArticleDBHelper;

/**
 * Created by le on 15/7/2015.
 */
public class SettingsFragment extends Fragment {
    Context ctx;
    private LoginButton loginButton;
    private TextView skipLoginButton;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePictureView;
    private ListView sharedLinklist;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private TextView name;
    private ArticleDBHelper mDBHelper;
    /**
     * Called when the activity is first created.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        this.ctx = inflater.getContext();
        mDBHelper = new ArticleDBHelper(this.ctx);
        callbackManager = CallbackManager.Factory.create();
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        name = (TextView) view.findViewById(R.id.name);
        arrayList = new ArrayList<String>();
        sharedLinklist = (ListView) view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, arrayList);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {"link","title","date"};
        String sortOrder ="date DESC";
//        Cursor cursor = db.query(
//                "Articles",  // The table to query
//                projection,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        );
        Cursor cursor = db.rawQuery("SELECT link,title,date FROM Articles WHERE 1=1",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            adapter.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        sharedLinklist.setAdapter(adapter);
        Profile profile = Profile.getCurrentProfile();
        if(profile!=null) {
            profilePictureView.setProfileId(profile.getId());
            name.setText(profile.getName());
        }
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                Profile profile = Profile.getCurrentProfile();
                profilePictureView.setProfileId(profile.getId());
                name.setText(profile.getName());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "Login canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "Login error", Toast.LENGTH_SHORT).show();
                Log.e("FB", "DCMFBEROOR");
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

