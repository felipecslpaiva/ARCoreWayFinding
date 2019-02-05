package com.tribalscale.felipepaiva.arway2.arscene;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;

public class ARWayFragmentPresenter implements ARWayFragmentContract.presenter {
    private static final String GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";

    ModelRenderable duckRenderable;
    private Context context;
    private ARWayFragmentContract.view viewContract;
    private ARWayFragment arWayFragment;
    private boolean finishLoading = false;

    ARWayFragmentPresenter(Context context, ARWayFragmentContract.view viewContract, ARWayFragment arWayFragment) {
        this.context = context;
        this.viewContract =  viewContract;
        this.arWayFragment = arWayFragment;
    }

    void prepareModelRenderable(){
        ModelRenderable.builder()
                .setSource(context, RenderableSource.builder().setSource(
                        context,
                        Uri.parse(GLTF_ASSET),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.5f)  // Scale the original model to 50%.
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(GLTF_ASSET)
                .build()
                .thenAccept(this::completableHandler)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(context, "Unable to load renderable " +
                                            GLTF_ASSET, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    private void completableHandler(ModelRenderable modelRenderable) {
        duckRenderable = modelRenderable;
        finishLoading = true;
        arWayFragment.setOnTapArPlaneListener(this::tapListener);
    }

    CompletableFuture<ModelRenderable> get3DModel() {
        RenderableSource.Builder builder = RenderableSource.builder().setSource(
                context,
                Uri.parse(GLTF_ASSET),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.5f)  // Scale the original model to 50%.
                .setRecenterMode(RenderableSource.RecenterMode.ROOT);
        return ModelRenderable.builder()
                .setSource(context, builder.build()).build();
    }

    private void tapListener(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (duckRenderable == null){
            return;
        }
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arWayFragment.getArSceneView().getScene());
        TransformableNode lamp = new TransformableNode(arWayFragment.getTransformationSystem());
        lamp.setParent(anchorNode);
        lamp.setRenderable(duckRenderable);
        lamp.select();
    }
}
