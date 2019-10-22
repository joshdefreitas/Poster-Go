package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ARCore extends AppCompatActivity {

    private ArFragment arFragment;
    private PosterContentLoader posterContentLoader;
    private final Map<AugmentedImage, ARCoreNode> posterMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_core);

        posterContentLoader = new PosterContentLoader(getApplicationContext());
        posterContentLoader.getImgdb();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame, just return.
        if (frame == null) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {
                case PAUSED:
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    //String text = "Detected Image " + augmentedImage.getIndex();
                    break;

                case TRACKING:
                    // Have to switch to UI Thread to update View.

                    // Create a new anchor for newly found images.
                    Log.d("UpdateFrame", "tracking");
                    if (!posterMap.containsKey(augmentedImage)) {
                        ARCoreNode node = new ARCoreNode(this);
                        node.setImage(augmentedImage);
                        posterMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                    }
                    break;

                case STOPPED:
                    posterMap.remove(augmentedImage);
                    break;
            }
        }
    }
}
