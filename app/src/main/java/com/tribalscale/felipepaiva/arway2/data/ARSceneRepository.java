package com.tribalscale.felipepaiva.arway2.data;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.tribalscale.felipepaiva.arway2.arscene.DBAnchorNode;

import java.util.ArrayList;
import java.util.List;

public class ARSceneRepository {

    private static String TAG = ARSceneRepository.class.getSimpleName();
    private static final String KEY_ROOT_DIR = "shared_anchor_codelab_root";
    private Application application;
    private DatabaseReference rootRef;

    public ARSceneRepository(Application application) {
        this.application = application;
//        FirebaseApp firebaseApp = FirebaseApp.initializeApp(application.getBaseContext());
//        rootRef = FirebaseDatabase.getInstance(firebaseApp).getReference().child(KEY_ROOT_DIR);
//        DatabaseReference.goOnline();
    }

    public String saveMarkers(List<DBAnchorNode> hitResultList) {
        List<DBAnchorNode> markers = getMarkers();
        return "";
    }

    public List<DBAnchorNode> getMarkers() {
        return new ArrayList<DBAnchorNode>();
    }
}
