package com.example.postergo;

import android.os.Bundle;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class ARCore extends AppCompatActivity {

    private ArFragment arFragment;
    private PosterContentLoader posterContentLoader;
    private final Map<AugmentedImage, ARCoreNode> posterMap = new HashMap<>();

    /**
    * Setup posterContentLoader and arFragment onCreate
    */
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

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {

                case TRACKING:
                    // The Trackable is currently tracked and its pose is current.
                    Log.d("UpdateFrame", "tracking");
                    if (!posterMap.containsKey(augmentedImage)) {

                        posterContentLoader.getContent(augmentedImage.getIndex());
                        posterContentLoader.lWebView.reload();

                        ARCoreNode node = new ARCoreNode(
                                this,
                                posterContentLoader.leftPanel,
                                posterContentLoader.rightPanel
                        );

                        node.setImage(augmentedImage);
                        posterMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                    }
                    break;

                case STOPPED:
                    posterMap.remove(augmentedImage);
                    break;

                default:
                    break;
            }
        }

    }
}
