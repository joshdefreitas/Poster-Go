package com.example.postergo;

import android.content.Context;
import android.util.Log;

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

    private AugmentedImage image;

    private static CompletableFuture<ViewRenderable> rightPanel;
    private static FixedHeightViewSizer panelSizer;

    @SuppressWarnings({"AndroidApiChecker"})
    public ARCoreNode(Context context) {
        panelSizer = new FixedHeightViewSizer(0.3f);

        if (rightPanel == null) {
            rightPanel =
                    ViewRenderable.builder()
                        .setView(context, R.layout.right_panel)
                        .setSizer(panelSizer)
                        .build();


        }
    }
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void setImage(AugmentedImage image) {
        this.image = image;
        Log.d(TAG, String.valueOf(image.getIndex()));

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

        localPosition.set(0.5f * image.getExtentX() + 0.12f, 0.0f, 0.15f);
        panelNode = new Node();
        panelNode.setParent(this);
        panelNode.setLocalPosition(localPosition);
        panelNode.setLocalRotation(new Quaternion(new Vector3(1, 0, 0), -90));
        panelNode.setRenderable(rightPanel.getNow(null));
    }
}
