package millennialapps.com.co.utils.tools;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void saveData(Object toSave, String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        ref.setValue(toSave);
    }

    public String pushIndex(String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        return ref.push().getKey();
    }

    public void addListener(ValueEventListener valueListener, String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        ref.addValueEventListener(valueListener);
    }
    public void addListenerForSingleEvent(ValueEventListener valueListener, String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        ref.addListenerForSingleValueEvent(valueListener);
    }

    public void exist(String key, String value, ValueEventListener valueEventListener, String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        ref.orderByChild(key).equalTo(value).addListenerForSingleValueEvent(valueEventListener);
    }

    public void removeReference(String reference){
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        ref.child(reference).removeValue();
    }

    public void removeListener(ValueEventListener valueEventListener, String... ids) {
        mDatabase = initDatabaseReference();
        DatabaseReference ref = mDatabase;
        for (String id : ids) {
            ref = ref.child(id);
        }
        ref.removeEventListener(valueEventListener);
    }
}
