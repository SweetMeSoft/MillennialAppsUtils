package co.com.millennialapps.utils.tools.tasks;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import co.com.millennialapps.utils.common.interfaces.IAsyncResponse;
import co.com.millennialapps.utils.models.route.Direction;

public class PathFinder extends AsyncTask<Void, Void, String> {

    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private final String url;
    public IAsyncResponse delegate = null;

    public PathFinder(LatLng origin, LatLng destination, String googleApiKey, IAsyncResponse response) {
        this.delegate = response;
        this.url = createUrl(origin, destination, googleApiKey);
    }

    private String createUrl(LatLng origin, LatLng destination, String googleApiKey) {
        String urlOrigin = origin.latitude + "," + origin.longitude;
        String urlDestination = destination.latitude + "," + destination.longitude;
        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&mode=walking" + "&key=" + googleApiKey;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(this.url);
            InputStream is = url.openConnection().getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        if (res != null) {
            delegate.processFinish(new Gson().fromJson(res, Direction.class));
        }
    }
}
