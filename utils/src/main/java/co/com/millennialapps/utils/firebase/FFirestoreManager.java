package co.com.millennialapps.utils.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import co.com.millennialapps.utils.tools.DialogManager;

public class FFirestoreManager {

    private static FFirestoreManager fDatabaseManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FFirestoreManager getInstance() {
        return fDatabaseManager == null ? fDatabaseManager = new FFirestoreManager() : fDatabaseManager;
    }

    public void add(final Activity activity, String path, Object toSave, final IAddDBF method) {
        db.collection(path)
                .add(toSave)
                .addOnSuccessListener(method::onReceive)
                .addOnFailureListener(e -> DialogManager.showSnackbar(activity, "No se ha podido conectar a Firestore"));
    }

    public void get(Activity activity, String path, IGetDBF method){
        db.collection(path)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        method.onReceive(task);
                    } else {
                        DialogManager.showSnackbar(activity, task.getException().toString());
                    }
                });
    }

    public void set(final Activity activity, String collectionPath, String documentPath, Object toSave, final ISetDBF method) {
        db.collection(collectionPath)
                .document(documentPath).set(toSave)
                .addOnSuccessListener(method::onReceive)
                .addOnFailureListener(e -> DialogManager.showSnackbar(activity, "No se ha podido conectar a Firestore"));
    }

    public interface IAddDBF {
        void onReceive(DocumentReference documentReference);
    }

    public interface IGetDBF {
        void onReceive(@NonNull Task<QuerySnapshot> task);
    }

    public interface ISetDBF {
        void onReceive(Void aVoid);
    }
}
