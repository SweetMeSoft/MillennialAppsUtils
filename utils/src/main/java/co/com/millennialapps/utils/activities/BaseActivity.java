package co.com.millennialapps.utils.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Erick Velasco on 7/1/2018.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
