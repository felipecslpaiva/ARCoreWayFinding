package com.tribalscale.felipepaiva.arway2.arscene;

import com.google.ar.sceneform.rendering.ModelRenderable;

public interface ARWayFragmentContract {
    interface view{

        void render(ModelRenderable modelRenderable);
    }

    interface presenter{ }
}
