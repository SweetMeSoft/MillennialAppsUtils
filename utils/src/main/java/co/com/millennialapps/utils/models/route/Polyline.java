package co.com.millennialapps.utils.models.route;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class Polyline {
    private String points;

    public Polyline(String points) {
        this.points = points;
    }

    public List<LatLng> getDecodedPoints() {
        return PolyUtil.decode(points);
    }

    public String getPoints() {
        return points;
    }
}