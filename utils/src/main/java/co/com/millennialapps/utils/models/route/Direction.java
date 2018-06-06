package co.com.millennialapps.utils.models.route;

import java.util.List;

public class Direction {

    private List<Route> routes;
    private String status;

    public Direction(List<Route> routes, String status) {
        this.routes = routes;
        this.status = status;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }
}