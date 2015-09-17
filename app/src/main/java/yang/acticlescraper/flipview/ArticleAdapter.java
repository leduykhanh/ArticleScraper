package yang.acticlescraper.flipview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yang.acticlescraper.R;
import yang.acticlescraper.facebook.FacebookManager;
import yang.acticlescraper.sql.ArticleDBHelper;

public class ArticleAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private int repeatCount = 1;
    FacebookManager facebookManager;
    private List<Articles.Data> articleData;
    private ArticleDBHelper mDBHelper;
    Context context;
    public ArticleAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        articleData = new ArrayList<Articles.Data>(Articles.IMG_DESCRIPTIONS);
        facebookManager = new FacebookManager(context);
        mDBHelper = new ArticleDBHelper(context);
        this.context = context;

    }

    @Override
    public int getCount() {
    return articleData.size() * repeatCount;
    }

    public int getRepeatCount() {
    return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
    }

    @Override
    public Object getItem(int position) {
    return position;
    }

    @Override
    public long getItemId(int position) {
    return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    View layout = convertView;
      //Log.e("statusaeee", ViewApp.status + "");
      if(ViewApp.status==ViewApp.STATUS.LOADING){
           layout = inflater.inflate(R.layout.loading, null);
             }
      else {
          if (convertView == null) {
              layout = inflater.inflate(R.layout.complex1, null);
              Logger.d("created new view from adapter: %d", position);
          }

          final Articles.Data data = articleData.get(position % articleData.size());

          UI
                  .<TextView>findViewById(layout, R.id.title)
                  .setText(Logger.format("%d. %s", position, data.title));

       /* UI
            .<ImageView>findViewById(layout, R.id.photo)
            .setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename));
    */

          UI
                  .<TextView>findViewById(layout, R.id.content)
                  .setText(data.content);
          UI
                  .<TextView>findViewById(layout, R.id.time)
                  .setText(data.time);
          UI
                  .<TextView>findViewById(layout, R.id.author)
                  .setText(data.author);

          UI
                  .<ImageButton>findViewById(layout, R.id.facebook)
                  .setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          facebookManager.share(data.link, data.title);
                          SQLiteDatabase db = mDBHelper.getWritableDatabase();
                          ContentValues values = new ContentValues();
                          values.put("link",data.link);
                          values.put("title",data.title);
                          long newRowId;
                          newRowId = db.insert("Articles",null,values);
                          if(newRowId > -1) {
                              Toast toast = Toast.makeText(context, "Shared link saved", Toast.LENGTH_SHORT);
                              toast.show();
                          }
                          db.close();
                      }
                  });
      }
    return layout;
    }

    public void removeData(int index) {
    if (articleData.size() > 1) {
      articleData.remove(index);
    }
    }
    public void clearData(){
        if (articleData.size() > 1)
            //articleData.clear();
        for(int i=1;i<articleData.size();i++) removeData(i);
    }
    public void addData(Articles.Data data){
        articleData.add(data);
    }
    public void set(int index,Articles.Data data){
        articleData.set(index,data);
    }
    public int size(){return articleData.size();}
    }
