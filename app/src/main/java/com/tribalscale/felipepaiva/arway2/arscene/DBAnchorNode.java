package com.tribalscale.felipepaiva.arway2.arscene;

import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.NodeParent;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Light;
import com.google.ar.sceneform.rendering.Renderable;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Predicate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class DBAnchorNode extends AnchorNode implements Serializable {

    @NonNull
    private String title = getName();

    public DBAnchorNode() {
        super();
    }

    public DBAnchorNode(Anchor anchor) {
        super(anchor);
    }

    @Override
    public void setAnchor(Anchor anchor) {
        super.setAnchor(anchor);
    }

    @Override
    public Anchor getAnchor() {
        return super.getAnchor();
    }

    @Override
    public void setSmoothed(boolean b) {
        super.setSmoothed(b);
    }

    @Override
    public boolean isSmoothed() {
        return super.isSmoothed();
    }

    @Override
    public boolean isTracking() {
        return super.isTracking();
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
    }

    @Override
    public void setLocalPosition(Vector3 vector3) {
        super.setLocalPosition(vector3);
    }

    @Override
    public void setWorldPosition(Vector3 vector3) {
        super.setWorldPosition(vector3);
    }

    @Override
    public void setLocalRotation(Quaternion quaternion) {
        super.setLocalRotation(quaternion);
    }

    @Override
    public void setWorldRotation(Quaternion quaternion) {
        super.setWorldRotation(quaternion);
    }

    @Override
    public void setParent(NodeParent nodeParent) {
        super.setParent(nodeParent);
    }

    @Override
    public boolean isTopLevel() {
        return super.isTopLevel();
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        super.setOnTouchListener(onTouchListener);
    }

    @Override
    public void setOnTapListener(OnTapListener onTapListener) {
        super.setOnTapListener(onTapListener);
    }

    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        super.addLifecycleListener(lifecycleListener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        super.removeLifecycleListener(lifecycleListener);
    }

    @Override
    public void addTransformChangedListener(TransformChangedListener transformChangedListener) {
        super.addTransformChangedListener(transformChangedListener);
    }

    @Override
    public void removeTransformChangedListener(TransformChangedListener transformChangedListener) {
        super.removeTransformChangedListener(transformChangedListener);
    }

    @Override
    public void setLocalScale(Vector3 vector3) {
        super.setLocalScale(vector3);
    }

    @Override
    public void setWorldScale(Vector3 vector3) {
        super.setWorldScale(vector3);
    }

    @Override
    public void setRenderable(Renderable renderable) {
        super.setRenderable(renderable);
    }

    @Override
    public Renderable getRenderable() {
        return super.getRenderable();
    }

    @Override
    public void setCollisionShape(CollisionShape collisionShape) {
        super.setCollisionShape(collisionShape);
    }

    @Override
    public CollisionShape getCollisionShape() {
        return super.getCollisionShape();
    }

    @Override
    public void setLight(Light light) {
        super.setLight(light);
    }

    @Override
    public Light getLight() {
        return super.getLight();
    }

    @Override
    public void onActivate() {
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
    }

    @Override
    public boolean onTouchEvent(HitTestResult hitTestResult, MotionEvent motionEvent) {
        return super.onTouchEvent(hitTestResult, motionEvent);
    }

    @Override
    public void onTransformChange(Node node) {
        super.onTransformChange(node);
    }

    @Override
    public void callOnHierarchy(Consumer<Node> consumer) {
        super.callOnHierarchy(consumer);
    }

    @Override
    public Node findInHierarchy(Predicate<Node> predicate) {
        return super.findInHierarchy(predicate);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Node findByName(String s) {
        return super.findByName(s);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setTitle(String title){
        setName(title);
    }

    @NonNull
    public String getTitle() {
        return title = getName();
    }
}
