package uk.co.cafexpresso.projectsunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private final String LOG_TAG= MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"--onCreate--");
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"--onStop--");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"--onStart--");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"--onResume--");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"--onPause--");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"--onDestroy--");
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
            Intent openSettings=new Intent(this,SettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        if (id==R.id.action_view_location){
            SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplication());
            String location = prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_def_value));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //"geo:0,0?q=1600+Amphitheatre+Parkway%2C+CA"
            location.replaceAll(" ","+")
                    .replaceAll(",", "%2C");
            Uri location_query= Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q",location).build();
            intent.setData(location_query);
            Log.d("Location intent in the MainActivity: ","Query: "+location_query);
            if (intent.resolveActivity(getPackageManager()) != null) {

                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}



