package noman.googleplaces;

import android.location.Location;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser class that parse Google Places API JSON response
 * Created by Noman on 8/25/2016.
 */
public class PlacesParser extends Parser {
    private static final String GEOMETRY = "geometry";
    private static final String ICON = "icon";
    private static final String NAME = "name";
    private static final String PLACE_ID = "place_id";
    private static final String TYPES = "types";
    private static final String VICINITY = "vicinity";
    private static final String LOCATION = "location";
    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String STATUS = "status";
    private static final String STATUS_OK = "OK";
    private static final String RESULTS = "results";
    private static final String NEXT_PAGE_TOKEN = "next_page_token";

    public PlacesParser(String placesUrl) {
        super(placesUrl);
    }


    public Pair<String, List<Place>> parseNearbyPlaces() throws PlacesException {
        final String result = convertStreamToString(this.getInputStream());
        if (result == null) {
            throw new PlacesException("Result is null");
        }

        try {
            JSONObject json = new JSONObject(result);
            if (!json.getString(STATUS).equals(STATUS_OK)) {
                throw new PlacesException(json);
            }

            JSONArray results = json.getJSONArray(RESULTS);
            List<Place> places = new ArrayList<>();
            String nextPageToken = null;
            if (json.has(NEXT_PAGE_TOKEN)) {
                nextPageToken = json.getString(NEXT_PAGE_TOKEN);
            }

            for (int i = 0; i < results.length(); i++) {
                places.add(buildNearbyPlacefromJSON(results.getJSONObject(i)));
            }
            return new Pair<>(nextPageToken, places);
        } catch (JSONException e) {
            throw new PlacesException("JSONException. Msg: " + e.getMessage());
        }

    }

    private Place buildNearbyPlacefromJSON(JSONObject jsonPlace) throws JSONException {
        Place place = new Place();

        //get location
        JSONObject locationJson = jsonPlace.getJSONObject(GEOMETRY).getJSONObject(LOCATION);
        Location location = new Location("place");
        location.setLatitude(locationJson.getDouble(LAT));
        location.setLongitude(locationJson.getDouble(LNG));
        place.setLocation(location);

        //get icon
        place.setIcon(jsonPlace.getString(ICON));

        //getname
        place.setName(jsonPlace.getString(NAME));

        //get place id
        place.setPlaceId(jsonPlace.getString(PLACE_ID));

        //get types
        JSONArray typesArray = jsonPlace.getJSONArray(TYPES);
        String[] types = new String[typesArray.length()];
        for (int i = 0; i < typesArray.length(); i++) {
            types[i] = typesArray.getString(i);
        }
        place.setTypes(types);

        //get vicinity
        place.setVicinity(jsonPlace.getString(VICINITY));

        return place;
    }

    /**
     * Convert an inputstream to a string.
     *
     * @param input inputstream to convert.
     * @return a String of the inputstream.
     */
    private String convertStreamToString(final InputStream input) {
        if (input == null) return null;

        final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        final StringBuilder sBuf = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sBuf.append(line);
            }
        } catch (IOException e) {
            Log.e("Places Error", e.getMessage());
        } finally {
            try {
                input.close();
                reader.close();
            } catch (IOException e) {
                Log.e("Places Error", e.getMessage());
            }
        }
        return sBuf.toString();
    }

}
