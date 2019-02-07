package com.tribalscale.felipepaiva.arway2.arscene;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.TransformableNode;
import com.tribalscale.felipepaiva.arway2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class ARWayFragmentPresenter implements ARWayFragmentContract.presenter {
    private static final String GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";

    private Context context;
    private ARWayFragmentContract.view viewContract;
    private ARWayFragment arWayFragment;
    private List<ModelRenderable> modelRenderableList = new ArrayList<ModelRenderable>();
    private List<AnchorNode> anchorNodeList = new ArrayList<AnchorNode>();
    private boolean finishLoading = false;
    private ModelRenderable cube;
    private String TAG = ARWayFragmentPresenter.class.getSimpleName();
    private AnchorNode lastAnchorNode;

    ARWayFragmentPresenter(Context context, ARWayFragmentContract.view viewContract, ARWayFragment arWayFragment) {
        this.context = context;
        this.viewContract =  viewContract;
        this.arWayFragment = arWayFragment;
    }

    void prepareModelRenderable(){
        ModelRenderable.builder()
                .setSource(context,
                        RenderableSource.builder().setSource(
                                context,
                                Uri.parse(GLTF_ASSET),
                                RenderableSource.SourceType.GLTF2)
                                .setScale(0.2f)  // Scale the original model to 50%.
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
        modelRenderableList.add(modelRenderable);
        finishLoading = true;
        arWayFragment.setOnTapArPlaneListener(this::tapListener);
    }

    //This getter is for local resources will keep it here as reference
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
        if (this.modelRenderableList.get(0) == null){
            return;
        }

        //pick one renderable from the list
        //Fixed by now because whe only have one renderable
        ModelRenderable tempModel = modelRenderableList.get(0);

        //Create the anchors need for the node based on our
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);

        //if it's the first element to be render the parent is the root element if not we should
        //get the last one from the list
        if(anchorNodeList.size() < 1){
            anchorNode.setParent(arWayFragment.getArSceneView().getScene());
        }else{
            try {
                anchorNode.setParent(anchorNodeList.get(anchorNodeList.size()));
            }catch (IndexOutOfBoundsException ex){
                ex.printStackTrace();
                anchorNode.setParent(arWayFragment.getArSceneView().getScene());
            }
        }

        TransformableNode transformableNode = new TransformableNode(arWayFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(tempModel);
        transformableNode.select();

        //After add to the scene keep a referrence of it for further use.
        anchorNodeList.add(anchorNode);

        if(lastAnchorNode  == null){
            lastAnchorNode = anchorNode;
        }

        //Draw a line between the hit and the previous element
        addLineBetweenHits(hitResult, plane, motionEvent);
    }

    //First try to create lines keep here as reference
    private void lineBetweenPoints(AnchorNode anchorNode, Quaternion lookRotation, float distanceMeters) {
        MaterialFactory.makeOpaqueWithColor(context, new Color(android.graphics.Color.WHITE))
                .thenAccept(
                        material -> {
                            Log.i("lineBetweenPoints", "distance: " + distanceMeters);
                            Vector3 size = new Vector3(.01f, .01f, distanceMeters);
                            Vector3 center = new Vector3(.01f/2, .01f/2, distanceMeters/2);
                            cube = ShapeFactory.makeCube(size, center, material);
                            Node lineNode = new Node();
                            final Quaternion rotationFromAToB = lookRotation;

                            lineNode.setParent(anchorNode);
                            lineNode.setRenderable(cube);
                            lineNode.setWorldRotation(rotationFromAToB);
                            arWayFragment.getArSceneView().getScene().addChild(anchorNode);
                        });
    }

    private void addLineBetweenHits(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);

        if (lastAnchorNode != null) {
            anchorNode.setParent(arWayFragment.getArSceneView().getScene());
            Vector3 point1, point2;
            point1 = lastAnchorNode.getWorldPosition();
            point2 = anchorNode.getWorldPosition();

            /*
                First, find the vector extending between the two points and define a look rotation
                in terms of this Vector.
            */
            final Vector3 difference = Vector3.subtract(point1, point2);
            final Vector3 directionFromTopToBottom = difference.normalized();
            final Quaternion rotationFromAToB =
                    Quaternion.lookRotation(directionFromTopToBottom, Vector3.up());
            MaterialFactory.makeOpaqueWithColor(context, new Color(context.getColor(R.color.colorAccentGrey)))
                    .thenAccept(
                            material -> {
            /* Then, create a rectangular prism, using ShapeFactory.makeCube() and use the difference vector
                   to extend to the necessary length.  */
                                ModelRenderable model = ShapeFactory.makeCube(
                                        new Vector3(.08f, .01f, difference.length()),
                                        Vector3.zero(), material);
            /* Last, set the world rotation of the node to the rotation calculated earlier and set the world position to
                   the midpoint between the given points . */
                                Node node = new Node();
                                node.setParent(anchorNode);
                                node.setRenderable(model);
                                node.setWorldPosition(Vector3.add(point1, point2).scaled(.5f));
                                node.setWorldRotation(rotationFromAToB);
                            }
                    );
            lastAnchorNode = anchorNode;
        }
    }

    @Override
    public void savePath() {
        
    }
}