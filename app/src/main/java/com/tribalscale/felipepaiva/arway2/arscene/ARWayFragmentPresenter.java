package com.tribalscale.felipepaiva.arway2.arscene;

import android.content.Context;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.TransformableNode;
import com.tribalscale.felipepaiva.arway2.R;
import com.tribalscale.felipepaiva.arway2.data.ARSceneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import androidx.annotation.GuardedBy;

class ARWayFragmentPresenter implements ARWayFragmentContract.presenter, GLSurfaceView.Renderer {
    private final Session session;
    private String TAG = ARWayFragmentPresenter.class.getSimpleName();
    private static final String DUCK_GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf";

    private static final String BOX_GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/BoxAnimated/glTF/BoxAnimated.gltf";

    private static final String PIN_GLTF_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/BoxAnimated/glTF/BoxAnimated.gltf";

    private Context context;
    private ARWayFragment arWayFragment;
    private ARSceneRepository arSceneRepository;
    private ARWayFragmentContract.view viewContract;
    private List<ModelRenderable> modelRenderableList = new ArrayList<ModelRenderable>();
    private List<DBAnchorNode> anchorMarkNodeList = new ArrayList<DBAnchorNode>();
    private List<DBAnchorNode> anchorLineNodeList = new ArrayList<DBAnchorNode>();
    private ModelRenderable cube;
    private AnchorNode lastAnchorNode;
    private boolean finishLoading = false;
    private int renderableNodeCounter = 1;
    private int lineNodeCounter = 1;
    private List<HitResult> hitResultList = new ArrayList<HitResult>();
    private int currentRenderable = 0;

    @GuardedBy("singleTapAnchorLock")
    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    private ArrayList<String> glfRemoteAssets;

    ARWayFragmentPresenter(Context context, ARWayFragmentContract.view viewContract, ARWayFragment arWayFragment, ARSceneRepository arSceneRepository) {
        this.context = context;
        this.viewContract =  viewContract;
        this.arWayFragment = arWayFragment;
        this.arSceneRepository = arSceneRepository;
        lineNodeCounter = 0;
        renderableNodeCounter = 0;
        arWayFragment.setOnTapArPlaneListener(this::tapListener);
        session = arWayFragment.getArSceneView().getSession();
        this.glfRemoteAssets = new ArrayList<>();

        glfRemoteAssets.add(DUCK_GLTF_ASSET);
        glfRemoteAssets.add(PIN_GLTF_ASSET);
    }

    //Iterate over the list who can be provided by the back end (remote asset list)
    void prepareModelRenderable(){
        for (int i = 0; i < glfRemoteAssets.size(); i++) {
            String assetPath = glfRemoteAssets.get(i);
            ModelRenderable.builder()
                    .setSource(context,
                            getRenderableforAsset(assetPath))
                    .setRegistryId(assetPath)
                    .build()
                    .thenAccept(this::completableHandler)
                    .exceptionally(
                            throwable -> {
                                Toast toast =
                                        Toast.makeText(context, "Unable to load renderable " +
                                                assetPath, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });
        }
    }

    private RenderableSource getRenderableforAsset(String assetPath) {
        return RenderableSource.builder().setSource(
                context,
                Uri.parse(assetPath),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.1f)  // Scale the original model to 50%.
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();
    }

    private void completableHandler(ModelRenderable modelRenderable) {
        modelRenderableList.add(modelRenderable);
        finishLoading = true;
    }

    //This getter is for local resources will keep it here as reference
    CompletableFuture<ModelRenderable> get3DModel() {
        RenderableSource.Builder renderableSourceBuilder = RenderableSource.builder().setSource(
                context,
                Uri.parse(DUCK_GLTF_ASSET),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.5f)  // Scale the original model to 50%.
                .setRecenterMode(RenderableSource.RecenterMode.ROOT);
        return ModelRenderable.builder()
                .setSource(context, renderableSourceBuilder.build()).build();
    }

    private void tapListener(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        try {
            if (this.modelRenderableList.get(0) == null){
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }

        Config config = new Config(arWayFragment.getArSceneView().getSession());
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setPlaneFindingMode(Config.PlaneFindingMode.HORIZONTAL_AND_VERTICAL);
        config.setCloudAnchorMode(Config.CloudAnchorMode.ENABLED); // Add this line.
        arWayFragment.getArSceneView().getSession().configure(config);

        //pick one renderable from the list
        //Fixed by now because whe only have one renderable
        ModelRenderable tempModel = modelRenderableList.get(currentRenderable);

        //Create the anchors need for the node based on our
        Anchor anchor = session.hostCloudAnchor(hitResult.createAnchor());
        DBAnchorNode anchorNode = new DBAnchorNode(anchor);
        anchorNode.setName("RenderableNode" + renderableNodeCounter);
        renderableNodeCounter = renderableNodeCounter + 1;

        //if it's the first element to be render the parent is the root element if not we should
        //get the last one from the list
        if(anchorMarkNodeList.size() < 1){
            anchorNode.setParent(arWayFragment.getArSceneView().getScene());
        }else{
            try {
                anchorNode.setParent(anchorMarkNodeList.get(anchorMarkNodeList.size()));
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
        anchorMarkNodeList.add(anchorNode);

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
        Anchor anchor = session.hostCloudAnchor(hitResult.createAnchor());
        DBAnchorNode anchorNode = new DBAnchorNode(anchor);
        anchorNode.setName("LineNode"+lineNodeCounter);
        lineNodeCounter = lineNodeCounter + 1;

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
            anchorLineNodeList.add(anchorNode);
        }
    }

    private void checkUpdatedAnchor() {
        // We'll fill this in later...
    }

    @Override
    public void savePath() {
        arSceneRepository.saveMarkers(anchorLineNodeList);
        arSceneRepository.saveMarkers(anchorMarkNodeList);
    }

    @Override
    public void changeRenderebleSouce() {
        if(currentRenderable == 0){
            currentRenderable = 1;
        }else{
            currentRenderable = 0;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}