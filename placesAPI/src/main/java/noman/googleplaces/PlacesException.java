package noman.googleplaces;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Noman on 8/25/2016.
 */
public class PlacesException extends Exception {
    private static final String TAG = "PlacesException";
    private static final String KEY_STATUS = "status";

    private String statusCode;
    private String message;

    public PlacesException(JSONObject json){
        if(json == null){
            statusCode = "";
            message = "Parsing error";
            return;
        }
        try {
            statusCode = json.getString(KEY_STATUS);
            message = json.getString(KEY_STATUS);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException while parsing PlacesException argument. Msg: " + e.getMessage());
        }
    }

    public PlacesException(String msg){
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}