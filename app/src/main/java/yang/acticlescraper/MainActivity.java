package yang.acticlescraper;

import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import yang.acticlescraper.async.Preload;
import yang.acticlescraper.flipview.ViewApp;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int FRAGMENT_COUNT = 3;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        super.onCreate(savedInstanceState);
        if (mWifi.isConnected()) {
            new Preload(this).execute("business");

            FacebookSdk.sdkInitialize(this);

            setContentView(R.layout.activity_main);
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
//            getSupportActionBar().hide();
        }
        else{
            setContentView(R.layout.offline);
        }
        //showFragment(1);

          }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void refresh(){
        ViewApp.adapter.notifyDataSetChanged();
        FragmentTransaction fragTransaction =   this.getSupportFragmentManager().beginTransaction();
        fragTransaction.detach(this.mSectionsPagerAdapter.getItem(1));
        //fragTransaction.attach(this.mSectionsPagerAdapter.getItem(1));
        fragTransaction.attach(new ViewApp());
         mViewPager.setCurrentItem(1);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
            }
    public void showFragment(int fragmentIndex) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), "DCMM", duration);
        toast.show();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(this.mSectionsPagerAdapter.getItem(0));
        transaction.hide(this.mSectionsPagerAdapter.getItem(1));
        transaction.hide(this.mSectionsPagerAdapter.getItem(2));
        transaction.show(this.mSectionsPagerAdapter.getItem(fragmentIndex));
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public  void load(String cat){
        new Preload(this).execute(cat);
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        Drawable myDrawable;
        SpannableStringBuilder sb;
        ImageSpan span;
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           switch(position){
               case 0 :
                   return new HomeFragment();
               case 1:
                   return new ViewApp();
               case 2:
                   return new SettingsFragment();
           }
            return new HomeFragment();
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    //return getString(R.string.title_section1).toUpperCase(l);
                    myDrawable = getResources().getDrawable(R.drawable.home);;
                    sb = new SpannableStringBuilder("   HOME"); // space added before text for convenience

                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
                    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb;
                case 1:
                    //return getString(R.string.title_section1).toUpperCase(l);
                    myDrawable = getResources().getDrawable(R.drawable.view);;
                    sb = new SpannableStringBuilder("   VIEW"); // space added before text for convenience

                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
                    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb;
                   // return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    myDrawable = getResources().getDrawable(R.drawable.settings);;
                    sb = new SpannableStringBuilder("   SETTINGS"); // space added before text for convenience

                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
                    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    return sb;
            }
            return null;
        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


}
