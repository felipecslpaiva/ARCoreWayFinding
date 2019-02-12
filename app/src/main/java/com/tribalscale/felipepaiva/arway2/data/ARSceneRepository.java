package com.tribalscale.felipepaiva.arway2.data;

import android.app.Application;

import com.google.ar.core.HitResult;

import java.util.ArrayList;
import java.util.List;

public class ARSceneRepository {

    private static String TAG = ARSceneRepository.class.getSimpleName();
    private Application application;

    public ARSceneRepository(Application application) {
        this.application = application;
    }

    public String saveMarkers(List<HitResult> hitResultList) {
        List<HitResult> markers = getMarkers();
        return "";
    }

    public List<HitResult> getMarkers() {
        return new ArrayList<HitResult>();
    }
}
