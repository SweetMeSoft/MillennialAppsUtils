package co.com.millennialapps.utils.common;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Erick Velasco on 7/12/2017.
 */

public interface IReceiveDB {

    void onReceive(DataSnapshot dataSnapshot);

}
