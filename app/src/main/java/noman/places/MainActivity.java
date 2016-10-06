package noman.places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity implements PlacesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NRPlaces.Builder()
                .listener(this)
                .key("AIzaSyAF8QXex5dgm0UIjBTZLd-e6EgjYUHnuw0")
                .latlng(33.728505, 73.082579)
                .radius(2000)
                .build()
                .execute();

    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {
        Log.e("Noman", "onPlacesStart");

    }

    @Override
    public void onPlacesSuccess(List<Place> places) {
        for (Place place : places)
            Log.e("Noman", place.toString());
    }

    @Override
    public void onPlacesFinished() {
        Log.e("Noman", "onPlacesFinished");

    }


}
