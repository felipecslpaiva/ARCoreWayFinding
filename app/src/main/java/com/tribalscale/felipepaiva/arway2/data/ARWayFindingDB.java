package com.tribalscale.felipepaiva.arway2.data;

import android.content.Context;

//@Database(entities = {DBAnchorNode.class}, version = 1, exportSchema = false)
public abstract  class ARWayFindingDB  {
    private static ARWayFindingDB INSTANCE;
    public abstract DBAnchorNodeDao anchorNodeDao();

    static ARWayFindingDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ARWayFindingDB.class) {
                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ARWayFindingDB.class, "word_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
