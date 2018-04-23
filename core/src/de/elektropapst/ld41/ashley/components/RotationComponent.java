package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class RotationComponent implements Component, Pool.Poolable {
    public float rotation = 0.0f;
    public float rotationInc = 5.0f;

    @Override
    public void reset() {
        rotation = 0.0f;
        rotationInc = 5.0f;
    }
}
