package uk.co.cafexpresso.projectsunshine.data;

import android.provider.BaseColumns;

/**
 * Created by moham_000 on 26/11/2014.
 */
public class WeatherContract {
    public static final class LocationEntry implements BaseColumns{

        public static final String TABLE_NAME="location";
        public static final String COLUMN_CITY_NAME="city_name";

        public static final String COLUMN_ID="location_id";
        public static final String COLUMN_LOC_SETTINGS="location_settings";
        public static final String COLUMN_COORD_LAT= "coord_lat";
        public static final String COLUMN_COORD_lONG= "coord_long";


    }
    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "location_id";

        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_DATETEXT = "date";

        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_WEATHER_ID = "weather_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_SHORT_DESC = "short_desc";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_HUMIDITY = "humidity";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_PRESSURE = "pressure";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_WIND_SPEED = "wind";
        // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String COLUMN_DEGREES = "degrees";

    }

}
