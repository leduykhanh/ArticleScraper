package yang.acticlescraper.facebook;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.*;
import com.facebook.share.widget.ShareDialog;

public class FacebookManager{
    static Context context;
    CallbackManager callbackManager;
    static ShareDialog shareDialog;
    public FacebookManager(Context c){
        context = c;
        shareDialog = new ShareDialog((Activity)c);

    }
    public void share(String url,String title) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(Uri.parse(url))
                .setContentDescription("Please read this")
                .build();
        shareDialog.show((Activity)context, content);
    }
}