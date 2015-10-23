package yang.acticlescraper.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import yang.acticlescraper.MainActivity;
import yang.acticlescraper.flipview.ViewApp;
import yang.acticlescraper.scrape.Utils;


public class Preload extends AsyncTask<String,Void,String> {
    Context context;
    MainActivity aa;

    public Preload(MainActivity aa){
        this.aa =aa;
        this.context = aa.getApplicationContext();
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        CharSequence text = "Loading data";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    @Override
    protected String doInBackground(String... params) {
        String category = params[0];
        if(category.equals("tech"))
            Utils.getContent("http://www.bbc.com/news/technology");
        else if(category.equals("Entertainment"))
            Utils.getContent("http://www.bbc.com/news/entertainment_and_arts");
        else if(category.equals("health"))
            Utils.getContent("http://www.bbc.com/news/health");
        else if(category.equals("business"))
            Utils.getContent("http://www.bbc.com/news/business");
        else if(category.equals("world"))
            Utils.getContent("http://www.bbc.com/news/world");
        else
            Utils.getContent("http://www.bbc.com/news/science_and_environment");
        return "Yes";
    }
    @Override
    protected void onPostExecute(String a) {
        CharSequence text = "Loading Finished";
        ViewApp.status = ViewApp.STATUS.FINISH;
        int duration = Toast.LENGTH_SHORT;
        new Handler().post(new Runnable() {
                               public void run() {
                                   Preload.this.aa.refresh();
                               }
                           });

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }
