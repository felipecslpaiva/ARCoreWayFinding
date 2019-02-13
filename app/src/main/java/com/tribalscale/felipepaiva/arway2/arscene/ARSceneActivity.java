package com.tribalscale.felipepaiva.arway2.arscene;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Config;
import com.tribalscale.felipepaiva.arway2.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ARSceneActivity extends AppCompatActivity {

    private ImageView imageViewStore;
    private ImageView imageViewStore2;
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

        imageViewStore2 = findViewById(R.id.activity_main_content_image2);
        imageViewStore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentViewContract.changeRenderableSource();
            }
        });
    }

    @Override
    public void onAttachFragment(@NonNull androidx.fragment.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof ARWayFragment){
            fragmentViewContract = (ARWayFragmentContract.view) fragment;
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
