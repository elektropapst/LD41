package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PositionComponent implements Component, Pool.Poolable {
    public float x = 0;
    public float y = 0;

    @Override
    public void reset() {
        x = 0;
        y = 0;
    }
}
