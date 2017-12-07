package co.com.millennialapps.utils.firebase;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.com.millennialapps.utils.R;
import co.com.millennialapps.utils.common.IReceiveDB;

/**
 * Created by erick on 6/7/2017.
 */

public class FDatabaseManager {
    
    private static FDatabaseManager fDatabaseManager;
    private DatabaseReference mDatabase;

    public static FDatabaseManager getInstance() {
        return fDatabaseManager == null ? fDatabaseManager = new FDatabaseManager() : fDatabaseManager;
    }

    private DatabaseReference initDatabaseReference() {
        return mDatabase == null ? mDatabase = FirebaseDatabase.getInstance().getReference() : mDatabase;
    }

    public void saveData(Object toSave, String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(path).setValue(toSave);
    }

    public String pushIndex(String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        return ref.child(path).push().getKey();
    }

    public void addListener(final IReceiveDB method, final View view, final String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                method.onReceive(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(view, R.string.database_cancelled, Snackbar.LENGTH_LONG).show();
                FirebaseCrash.report(databaseError.toException());
            }
        });
    }

    public void addListenerForSingleEvent(final IReceiveDB method, final View view, final String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                method.onReceive(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(view, R.string.database_cancelled, Snackbar.LENGTH_LONG).show();
                FirebaseCrash.report(databaseError.toException());
            }
        });
    }

    public void exist(String key, String value, final IReceiveDB method, final View view, String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(path).orderByChild(key).equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                method.onReceive(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(view, R.string.database_cancelled, Snackbar.LENGTH_LONG).show();
                FirebaseCrash.report(databaseError.toException());
            }
        });
    }

    public void removeReference(String reference) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(reference).removeValue();
    }

    public void removeListener(final IReceiveDB runnable, final View view, String path) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(path).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                runnable.onReceive(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(view, R.string.database_cancelled, Snackbar.LENGTH_LONG).show();
                FirebaseCrash.report(databaseError.toException());
            }
        });
    }
}
