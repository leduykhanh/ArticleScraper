package yang.acticlescraper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import yang.acticlescraper.async.Preload;
import yang.acticlescraper.flipview.ViewApp;

/*View page*/
public class HomeFragment extends Fragment implements View.OnClickListener {

    Context ctx;

    /**
     * Called when the activity is first created.
     */
    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        this.ctx = inflater.getContext();
        LinearLayout techLO= (LinearLayout) view.findViewById(R.id.technologyViews);
        techLO.setOnClickListener(this);
        LinearLayout entLO= (LinearLayout) view.findViewById(R.id.entertainment);
        entLO.setOnClickListener(this);
        LinearLayout healthLO= (LinearLayout) view.findViewById(R.id.health);
        healthLO.setOnClickListener(this);
        LinearLayout businessLO= (LinearLayout) view.findViewById(R.id.business);
        businessLO.setOnClickListener(this);
        LinearLayout worldLO= (LinearLayout) view.findViewById(R.id.world);
        worldLO.setOnClickListener(this);
        return view;
    }
    private void showViewFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(this);
        transaction.show(new ViewApp());
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch(viewID){
            case R.id.technologyViews:
                new Preload((MainActivity)getActivity()).execute("tech");
                break;
            case R.id.entertainment:
                new Preload((MainActivity)getActivity()).execute("Entertainment");
                break;
            case R.id.health:
                new Preload((MainActivity)getActivity()).execute("health");
                break;
            case R.id.business:
                new Preload((MainActivity)getActivity()).execute("business");
                break;
            case R.id.world:
                new Preload((MainActivity)getActivity()).execute("world");
                break;
        }
    }
}