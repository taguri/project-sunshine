package uk.co.cafexpresso.projectsunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ShareActionProvider;
import android.widget.TextView;

import android.support.v7.widget.ShareActionProvider;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
            Intent openSettings=new Intent(this,SettingsActivity.class);
            startActivity(openSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }
        private static final String LOG_TAG=PlaceholderFragment.class.getSimpleName();
        private static final String FORECAST_HASH_TAG= " #SunshineApp";
        private String mExtraInfo;

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.detail_fragment, menu);

            MenuItem shareItem=menu.findItem(R.id.action_share);

            //get provider and hold onto it so u can change intent
            ShareActionProvider shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
            if(shareActionProvider!=null){
                shareActionProvider.setShareIntent(shareForecastIntent());
            }else{
                Log.e(LOG_TAG, "Share action provider is null?");
            }


        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            String id=getString(item.getItemId());
            if(id=="action_share"){
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private Intent shareForecastIntent(){
            Intent shareIntent =new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mExtraInfo+FORECAST_HASH_TAG);
            return shareIntent;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


            Intent invokeIntent =getActivity().getIntent();
            if (invokeIntent!=null && invokeIntent.hasExtra(Intent.EXTRA_TEXT)) {
                mExtraInfo = invokeIntent.getStringExtra(Intent.EXTRA_TEXT);
                TextView detailTextView = (TextView) rootView.findViewById(R.id.detail_text_view);
                detailTextView.setText(mExtraInfo);
            }
            return rootView;
        }
    }
}
