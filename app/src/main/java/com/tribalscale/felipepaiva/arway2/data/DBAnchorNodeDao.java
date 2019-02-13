package com.tribalscale.felipepaiva.arway2.data;

import com.tribalscale.felipepaiva.arway2.arscene.DBAnchorNode;

import java.util.List;

public abstract class DBAnchorNodeDao {
    abstract void insert(DBAnchorNode anchorNode);

    public abstract void deleteAll();

    public abstract List<DBAnchorNode> getAllNodes();

    public void inserAnchords(List<DBAnchorNode> anchorNodeList){
        DBAnchorNode tempNode;
        for (int i = 0; i < anchorNodeList.size(); i++) {
            tempNode = anchorNodeList.get(i);
            insert(tempNode);
        }
    }
}
