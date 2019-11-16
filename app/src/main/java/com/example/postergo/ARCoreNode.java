package com.example.postergo;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.FixedHeightViewSizer;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

public class ARCoreNode extends AnchorNode {

    private static final String TAG = "ARCoreNode";
    private static float panelHeight = 0.279f;
    private static float panelWidth = 0.223f;

    private CompletableFuture<ViewRenderable> leftPanel;
    private CompletableFuture<ViewRenderable> rightPanel;

    /**
     * Create ARCoreNode, build viewRenderable for two panels
     * @param
     * context: current context
     * leftView: left panel view
     * rightView: right panel view
     *
    */
    @SuppressWarnings({"AndroidApiChecker"})
    public ARCoreNode(Context context, View leftView, View rightView) {
        FixedHeightViewSizer panelSizer = new FixedHeightViewSizer(panelHeight);

        if (leftPanel == null) {
            leftPanel =
                    ViewRenderable.builder()
                        .setView(context, leftView)
                        .setSizer(panelSizer)
                        .build();
        }

        if (rightPanel == null) {
            rightPanel =
                    ViewRenderable.builder()
                        .setView(context, rightView)
                        .setSizer(panelSizer)
                        .build();
        }


    }

    /**
    * Accept panel renderables and set them to the two sides of the image
    *
    * @param
    * image: the image that is currently tracked
    */
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void setImage(AugmentedImage image) {

        if (!leftPanel.isDone()) {
            CompletableFuture.allOf(leftPanel)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        if (!rightPanel.isDone()) {
            CompletableFuture.allOf(rightPanel)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }


        Vector3 leftPosition = new Vector3();
        Vector3 rightPosition = new Vector3();

        setAnchor(image.createAnchor(image.getCenterPose()));

        Node lPanelNode;
        Node rPanelNode;

        leftPosition.set(-0.5f * image.getExtentX() - 0.5f * panelWidth,
                0.0f,
                0.5f * panelHeight);
        rightPosition.set(0.5f * image.getExtentX() + 0.5f * panelWidth,
                0.0f,
                0.5f * panelHeight);

        lPanelNode = new Node();
        lPanelNode.setParent(this);
        lPanelNode.setLocalPosition(leftPosition);
        lPanelNode.setLocalRotation(new Quaternion(new Vector3(1, 0, 0), -90));
        lPanelNode.setRenderable(leftPanel.getNow(null));

        rPanelNode = new Node();
        rPanelNode.setParent(this);
        rPanelNode.setLocalPosition(rightPosition);
        rPanelNode.setLocalRotation(new Quaternion(new Vector3(1, 0, 0), -90));
        rPanelNode.setRenderable(rightPanel.getNow(null));
    }
}
