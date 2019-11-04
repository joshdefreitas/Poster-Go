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
    private static float panelHeight = 0.3f;
    private static float panelWidth = 0.24f;

    private  CompletableFuture<ViewRenderable> rightPanel;

    /*
    * Create ARCoreNode, build viewRenderable for right panel
    */
    @SuppressWarnings({"AndroidApiChecker"})
    public ARCoreNode(Context context, View view) {
        FixedHeightViewSizer panelSizer = new FixedHeightViewSizer(panelHeight);

        if (rightPanel == null) {
            rightPanel =
                    ViewRenderable.builder()
                        .setView(context, view)
                        .setSizer(panelSizer)
                        .build();


        }
    }

    /*
    * Accept right panel renderable and set it to the right side if the image
    *
    * Param:
    * image: the image that is currently tracked
    */
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void setImage(AugmentedImage image) {

        if (!rightPanel.isDone()) {
            CompletableFuture.allOf(rightPanel)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        Vector3 localPosition = new Vector3();

        setAnchor(image.createAnchor(image.getCenterPose()));
        Node panelNode;

        localPosition.set(0.5f * image.getExtentX() + 0.5f * panelWidth,
                0.0f,
                0.5f * panelHeight);

        panelNode = new Node();
        panelNode.setParent(this);
        panelNode.setLocalPosition(localPosition);
        panelNode.setLocalRotation(new Quaternion(new Vector3(1, 0, 0), -90));
        panelNode.setRenderable(rightPanel.getNow(null));
    }
}
