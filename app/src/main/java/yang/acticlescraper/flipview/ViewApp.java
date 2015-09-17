package yang.acticlescraper.flipview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yang.acticlescraper.R;

/*View page*/
public class ViewApp extends Fragment
{
    public enum STATUS{LOADING,FINISH}
    public static STATUS status = STATUS.LOADING;
    private FlipViewController flipView;
    Context ctx;
    public static ArticleAdapter adapter;
    /**
     * Called when the activity is first created.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.ctx = inflater.getContext();
        flipView = new FlipViewController(this.ctx);
        //Use RGB_565 can reduce peak memory usage on large screen device, but it's up to you to choose the best bitmap format
        flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
        adapter = new ArticleAdapter(this.ctx);
        flipView.setAdapter(adapter);
        View view = inflater.inflate(R.layout.loading, container, false);
        return flipView;

    }

    @Override
    public void onResume() {
        super.onResume();
        flipView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        flipView.onPause();
    }

}