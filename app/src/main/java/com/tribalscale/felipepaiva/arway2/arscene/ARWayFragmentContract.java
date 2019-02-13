package com.tribalscale.felipepaiva.arway2.arscene;

public interface ARWayFragmentContract {
    interface view {
        void savePath();
        void changeRenderableSource();
    }
    interface presenter {
        void savePath();
        void changeRenderebleSouce();
    }
}