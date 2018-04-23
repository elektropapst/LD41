package de.elektropapst.ld41;

import com.badlogic.ashley.core.PooledEngine;
import de.elektropapst.ld41.assets.AssetLoader;

public class Statics {
    public static PooledEngine ashley;
    public static AssetLoader asset;

    public static void initializeStatics() {
        asset = new AssetLoader();
        ashley = new PooledEngine();
    }

    public static void disposeStatics() {
        asset.dispose();
    }
}
