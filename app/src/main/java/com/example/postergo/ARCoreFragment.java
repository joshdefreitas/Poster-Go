package com.example.postergo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

public class ARCoreFragment extends ArFragment {

    private static final String TAG = "ARCoreFragment";

    /**
    * Turn off plane discovery onCreateView,
    * since we are using augmentedImage
    */
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getPlaneDiscoveryController().hide();
        getPlaneDiscoveryController().setInstructionView(null);
        getArSceneView().getPlaneRenderer().setEnabled(false);
        return view;
    }

    /**
    * Get the session configuration, set auto focus mode for camera
    *
    * @param
    * session: current session
    * @return
    * the config of current session
    */
    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = super.getSessionConfiguration(session);
        setupAugmentedImageDatabase(config, session);
        config.setFocusMode(Config.FocusMode.AUTO);
        return config;
    }

    /**
    * Load the augmentedImageDatabase from local storage
    *
    * @param
    * config: session configuration
    * session: current session
    * @return
    * true for successfully loaded the augmentedImageDatabase,
    * otherwise false
    */
    private boolean setupAugmentedImageDatabase(Config config, Session session) {
        AugmentedImageDatabase augmentedImageDatabase;

        AssetManager assetManager = getContext() != null ? getContext().getAssets() : null;
        if (assetManager == null) {
            Log.e(TAG, "Context is null, cannot initialize image database.");
            return false;
        }

        try (InputStream is = new FileInputStream(
                getContext().getFileStreamPath(PosterContentLoader.imgdbFileName))) {
            augmentedImageDatabase = AugmentedImageDatabase.deserialize(session, is);
        } catch (IOException e) {
            Log.e(TAG, "IO exception loading augmented image database.", e);
            return false;
        }

        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }
}
