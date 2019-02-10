package com.tribalscale.felipepaiva.arway2.arscene;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.tribalscale.felipepaiva.arway2.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ARSceneActivity extends AppCompatActivity {

    private ImageView imageViewStore;
    private ARWayFragment arWayFragment;
    private String TAG = ARSceneActivity.class.getSimpleName();
    private ARWayFragmentContract.view fragmentViewContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewStore = findViewById(R.id.activity_main_content_image);
        imageViewStore.setOnClickListener(
                v -> {
                    if(fragmentViewContract != null){
                        fragmentViewContract.savePath();
                    }
                    Toast.makeText(
                            ARSceneActivity.this, "Feature under development", Toast.LENGTH_LONG)
                            .show();
                });
    }

    @Override
    public void onAttachFragment(@NonNull androidx.fragment.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof ARWayFragment){
            fragmentViewContract = arWayFragment;
            Log.d(TAG, fragment.getClass().getSimpleName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
