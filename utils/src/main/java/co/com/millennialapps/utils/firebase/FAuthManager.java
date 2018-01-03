package co.com.millennialapps.utils.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;

import co.com.millennialapps.utils.R;
import co.com.millennialapps.utils.common.IReceiveDB;
import co.com.millennialapps.utils.models.User;
import co.com.millennialapps.utils.tools.DialogManager;
import co.com.millennialapps.utils.tools.Preferences;

/**
 * Created by erick on 1/7/2017.
 */

public class FAuthManager {

    private static FAuthManager authManager;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static FAuthManager getInstance() {
        return authManager == null ? authManager = new FAuthManager() : authManager;
    }

    public void authWithGoogle(final Activity activity, final GoogleSignInAccount acct) {
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkAndSaveUser(activity, acct.getEmail());
                            /*if(activity instanceof MainActivity){
                                MainActivity activity1 = (MainActivity) activity;
                                activity1.changeDrawerData();
                            }*/
                        } else {
                            Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                                    R.string.msg_cannot_login,
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void authWithPassword(final Activity activity, final String email, final String password) {
        DialogManager.showLoadingDialog(activity, R.string.loading, "Ingresando", true, false);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkAndSaveUser(activity, email);
                        } else {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                checkAndSaveUser(activity, email);
                                            } else {
                                                DialogManager.dismissLoadingDialog();
                                                Snackbar.make(activity.getCurrentFocus(),
                                                        R.string.msg_wrong_password,
                                                        Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    private void checkAndSaveUser(final Activity activity, final String email) {
        FDatabaseManager.getInstance().exist("email", email, dataSnapshot -> {
            User user = new User();
            if (dataSnapshot.getValue() == null) {
                String id = FDatabaseManager.getInstance().pushIndex(User.class.getName());
                user.setName(firebaseAuth.getCurrentUser().getDisplayName());
                user.setEmail(firebaseAuth.getCurrentUser().getEmail());
                user.setUrlPhoto(firebaseAuth.getCurrentUser().getPhotoUrl().toString());
                FDatabaseManager.getInstance().saveData(user, User.class.getName() + "/" + id);
            } else {
                if (dataSnapshot.child("password").getValue() == null
                        || dataSnapshot.child("password").getValue().toString().isEmpty()) {
                    FDatabaseManager.getInstance().saveData(user.getPassword(),
                            User.class.getName() + "/" + dataSnapshot.getKey() + "/" + "password");
                }
                user = dataSnapshot.getValue(User.class);
            }
            Preferences.saveObjectAsJson(activity, Preferences.PF_USER, Preferences.K_USER, user);
            DialogManager.dismissLoadingDialog();
        }, activity.getWindow().getDecorView(), User.class.getSimpleName());
    }


    public boolean isLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signOut() {
        firebaseAuth.signOut();
    }
}
