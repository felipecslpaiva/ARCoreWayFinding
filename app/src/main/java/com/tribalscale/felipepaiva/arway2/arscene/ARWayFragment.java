package com.tribalscale.felipepaiva.arway2.arscene;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.sceneform.ux.ArFragment;
import com.tribalscale.felipepaiva.arway2.utils.CameraPermissionHelper;

import androidx.annotation.Nullable;

public class ARWayFragment extends ArFragment {
    public ARWayFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (!CameraPermissionHelper.hasCameraPermission(getActivity())) {
            CameraPermissionHelper.requestCameraPermission(getActivity());
            return;
        }
        super.onResume();

    }

    //This method is required to be able to take a "print" of ar fragment.
    @Override
    public String[] getAdditionalPermissions() {
        String[] additionalPermissions = super.getAdditionalPermissions();
        int permissionLength = additionalPermissions != null ? additionalPermissions.length : 0;
        String[] permissions = new String[permissionLength + 1];
        permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (permissionLength > 0) {
            System.arraycopy(additionalPermissions, 0, permissions, 1, additionalPermissions.length);
        }
        return permissions;
    }
}
