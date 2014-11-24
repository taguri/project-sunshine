package uk.co.cafexpresso.projectsunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by moham_000 on 24/11/2014.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecast_fragment, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if (id==R.id.action_refresh){
            FetchWeatherTask fetchWeather =new FetchWeatherTask();
            fetchWeather.execute("Manchester,uk");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] fakeDataArray = {
                "Today- Sunny as Fuck",
                "Tomorrow- Rainy as Fuck",
                "Monday- Windy as Fuck",
                "Tuesday- Dry as Fuck",
                "Wednesday- Humid as Fuck",
                "Thursday- Boring",
                "Jumua Mubarak. Also cloudy",
                "Next Saturday- God knows",
                "Stop scrolling"
        };
        //List<String> fakeData= new ArrayList<String>(Arrays.asList(fakeDataArray));
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, fakeDataArray);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listViewForecast = (ListView) rootView.findViewById(R.id.listview_forecast);
        listViewForecast.setAdapter(listAdapter);
        return rootView;
    }
    public class FetchWeatherTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast


                String baseUrl="http://api.openweathermap.org/data/2.5/forecast/daily?";
                String q=params[0];
                String mode="json";
                String units="metric";
                String cnt="7";
                Uri urlBuilder= Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter("q", q)
                        .appendQueryParameter("mode", mode)
                        .appendQueryParameter("units",units)
                        .appendQueryParameter("cnt",cnt)
                        .build();
                String builtUrl=urlBuilder.toString();
                //URL url = new URL(builtUrl);
                URL url= new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=manchester,uk&mode=json&units=metric&cnt=7");
                Log.v(LOG_TAG,"URL from builder= "+ builtUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

    }



}