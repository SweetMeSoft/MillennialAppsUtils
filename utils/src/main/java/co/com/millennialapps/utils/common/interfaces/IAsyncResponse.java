package co.com.millennialapps.utils.common.interfaces;

import co.com.millennialapps.utils.models.route.Direction;

public interface IAsyncResponse {
    void processFinish(Direction output);
}