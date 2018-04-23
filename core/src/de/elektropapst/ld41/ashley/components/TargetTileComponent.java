package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TargetTileComponent implements Component, Pool.Poolable{
    public float targetX = 0;
    public float targetY = 0;

    @Override
    public void reset() {
        targetX = 0;
        targetY = 0;
    }
}
