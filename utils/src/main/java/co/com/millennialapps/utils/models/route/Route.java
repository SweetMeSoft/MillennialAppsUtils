package co.com.millennialapps.utils.models.route;

import java.util.List;

public class Route {
    private List<Leg> legs;

    public Route(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return legs;
    }
}
